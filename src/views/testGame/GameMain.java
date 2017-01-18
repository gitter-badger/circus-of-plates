package views.testGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMain extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // load needed classes
        Pane root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Circus Of Plates");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(root, 700, 500));
        //primaryStage.setFullScreen(true);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}