package models.settings;

public class GameSettings {
    private static GameSettings instance;
    private Audio audioSettings;
    private Graphics graphicsSettings;
    private General generalSettings;

    private GameSettings() {
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
            instance.initializeDefaultSettings();
        }
        return instance;
    }

    private void initializeDefaultSettings() {
        this.audioSettings = new Audio(50, false);
        this.graphicsSettings = new Graphics();
        this.generalSettings = new General();
    }

    public Audio getAudioSettings() {
        return this.audioSettings;
    }

    public General getGeneralSettings() {
        return this.generalSettings;
    }

    public Graphics getGraphicsSettings() {
        return this.graphicsSettings;
    }
}
