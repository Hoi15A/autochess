package ch.zhaw.pm2.autochess.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private static final Logger logger = Logger.getLogger(MainApp.class.getCanonicalName());
    private Stage primaryStage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startNewGame(primaryStage);
    }

    private void startNewGame(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MainWindow.fxml"));
            Pane rootPane = loader.load();
            rootPane.getStylesheets().add(String.valueOf(getClass().getResource("css/MainStylesheet.css")));
            rootPane.setId("pane");
            Scene scene = new Scene(rootPane);

            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.setTitle("SMAC");
            primaryStage.show();
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Error starting up UI" + e.getMessage());
        }
    }
}