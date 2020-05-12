package ch.zhaw.pm2.autochess;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    private File stylesheet = new File(String.valueOf(getClass().getResource("css/MainStylesheet.css")));
    private Text title;
    private Text menuNewGame;
    private Text menuHelp;
    private VBox menuBox;


    @FXML
    GridPane mainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMenu();
        initializeMenuTitle();

        menuBox = new VBox();
        menuBox.getChildren().add(menuNewGame);
        menuBox.getChildren().add(menuHelp);


        mainGrid.add(title,0,0);
        mainGrid.add(menuBox,0,1);

        //new Game Listener
        menuNewGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("new game");
                loadHeroSelectWindow();

            }
        });

        //Help listener
        menuHelp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("help");
            }
        });

    }

    private void initializeMenuTitle(){
        title = new Text("SMAC");
        title.setId("title");
    }

    private void initializeMenu(){
        menuNewGame = new Text("new Game");
        menuNewGame.setId("menuNewGame");
        menuHelp = new Text("help");
        menuHelp.setId("menuHelp");
    }

    private void loadHeroSelectWindow(){
        Stage heroSelectStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HeroSelectWindow.fxml"));
            Pane shopPane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("SMAC");
            stage.setScene(new Scene(shopPane, 800, 600));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}


