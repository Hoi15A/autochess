package ch.zhaw.pm2.autochess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainApp extends Application {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getCanonicalName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startNewGame(primaryStage);
    }

    private void startNewGame(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGameWindow.fxml"));
            Pane rootPane = loader.load();
            // fill in scene and stage setup
            Scene scene = new Scene(rootPane);

            // configure and show stage
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(420);
            primaryStage.setMinHeight(250);
            primaryStage.setTitle("SMAC");
            primaryStage.show();
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error starting up UI" + e.getMessage());
        }
    }
}

