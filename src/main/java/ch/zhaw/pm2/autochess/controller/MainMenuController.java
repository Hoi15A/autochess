package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Game.Game;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController implements Initializable {
    private File stylesheet = new File(String.valueOf(getClass().getResource("css/MainMenuStylesheet.css")));
    private Text title;
    protected Text menuNewGame;
    private Text menuHelp;
    private VBox menuBox;


    @FXML
    GridPane mainGrid;

    public MainMenuController(Game game){
        System.out.println("yep");
        menuNewGame = new Text("new Game");
        menuNewGame.setId("menuNewGame");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMenu();
        initializeMenuTitle();

        menuBox = new VBox();
        menuBox.getChildren().add(menuNewGame);
        menuBox.getChildren().add(menuHelp);


        mainGrid.add(title,0,0);
        mainGrid.add(menuBox,0,1);

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
        menuHelp = new Text("help");
        menuHelp.setId("menuHelp");
    }
}


