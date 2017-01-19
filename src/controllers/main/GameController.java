package controllers.main;

import controllers.PlayerController;
import controllers.input.ActionType;
import controllers.input.Input;
import controllers.input.InputAction;
import controllers.input.InputType;
import controllers.input.joystick.Joystick;
import controllers.input.joystick.JoystickCode;
import controllers.input.joystick.JoystickEvent;
import controllers.menus.MenuController;
import controllers.menus.Start;
import javafx.application.Platform;
import javafx.fxml.FXML;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import models.players.PlayerFactory;

import javax.swing.*;

public class GameController implements Initializable {
    private MenuController currentMenu;
    private PlayerController playerController;
    // TODO: 1/19/17 plate Controller

    private Timer gameTimer;
    private final int CLOWNSPEED = 20;
    private final int PLATESPEED = 1;
    private static GameController instance;
    private Input joystickInput;

    @FXML
    private AnchorPane rootPane;

    @FXML
    AnchorPane menuPane;

    @FXML
    private AnchorPane mainGame;

    @FXML
    private AnchorPane rect;

    @FXML
    private Rectangle plate1;

    @FXML
    private Rectangle plate2;

    @FXML
    private Rectangle rightRod;

    @FXML
    private Rectangle leftRod;


    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    Node player;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controllers
        currentMenu = Start.getInstance();
        playerController = new PlayerController();
        try {
            URL url = new File("src/views/clown.fxml").toURI().toURL();
            Node node = playerController.createPlayer("player1", url);
            mainGame.getChildren().add(node);
            player = node;
        } catch (IOException e) {
            e.printStackTrace();
        }

        instance  = this;

        joystickInput = Joystick.getInstance();
        joystickInput.registerClassForInputAction(getClass(), instance);
    }

    public void setCurrentMenu(MenuController currentMenu) {
        this.currentMenu = currentMenu;
    }

    public MenuController getCurrentMenu() {
        return currentMenu;
    }

    public AnchorPane getMainGame() {
        return mainGame;
    }

    private Double currentX;
    @FXML
    public void mouseHandler(MouseEvent event) {
        if (currentX == null) {
            currentX = event.getX();
        } else {
            if (currentX > event.getX()) {
                rect.setLayoutX(Math.max(rect.getLayoutX() - CLOWNSPEED, -350 + rect.getWidth() / 2.0));
            } else {
                rect.setLayoutX(Math.min(rect.getLayoutX() + CLOWNSPEED, 350 - rect.getWidth() / 2.0));
            }
        }
    }

    @FXML
    public void keyHandler(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                if (mainGame.isVisible()) {
                    rect.setLayoutX(Math.max(rect.getLayoutX() - CLOWNSPEED, -350 + rect.getWidth() / 2.0));
                }
                break;
            case RIGHT:
                if (mainGame.isVisible()) {
                    rect.setLayoutX(Math.min(rect.getLayoutX() + CLOWNSPEED, 350 - rect.getWidth() / 2.0));
                }
                break;
            case ESCAPE:
                Platform.runLater(() -> {
                    if (currentMenu.isVisible()) {
                        currentMenu.setMenuVisible(false);
                        mainGame.requestFocus();
                        mainGame.setVisible(true);
                    } else {
                        currentMenu = Start.getInstance();
                        currentMenu.setMenuVisible(true);
                        currentMenu.requestFocus(0);
                        mainGame.setVisible(false);
                    }
                });
                break;
            // keyboard_two
            case A:
                if (mainGame.isVisible()) {
                    String playerName = PlayerFactory.getFactory()
                            .getPlayerNameWithController(InputType.KEYBOARD_TWO);
                    if (playerName != null) {
                        playerController.moveLeft(playerName);
                    }
                }
                break;
            case D:
                if (mainGame.isVisible()) {
                    String playerName = PlayerFactory.getFactory()
                            .getPlayerNameWithController(InputType.KEYBOARD_TWO);
                    if (playerName != null) {
                        playerController.moveRight(playerName);
                    }
                }
                break;
            default:
                break;
        }
    }


    @InputAction(ACTION_TYPE = ActionType.BEGIN, INPUT_TYPE = InputType.JOYSTICK)
    public void performJoystickAction(JoystickEvent event) {
        String playerName = PlayerFactory.getFactory().getPlayerNameWithController(InputType.JOYSTICK);

        Platform.runLater(() -> {
            if (event.getJoystickCode() == JoystickCode.LEFT) {
                if (playerName != null) {
                    playerController.moveLeft(playerName);
                }
            } else if (event.getJoystickCode() == JoystickCode.RIGHT) {
                if (playerName != null) {
                    playerController.moveRight(playerName);
                }
            }
        });
    }

    public double getStageWidth() {
        return mainGame.getWidth();
    }

}
