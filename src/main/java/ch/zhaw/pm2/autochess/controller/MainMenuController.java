package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Game.Game;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class of the MainMenuWindow.
 * It initializes all the components.
 */
public class MainMenuController implements Initializable {
    private Text menuTitle;
    protected Text menuNewGame;
    private Text menuHelp;
    private VBox menuBox;

    @FXML
    GridPane mainGrid;

    public MainMenuController(Game game){
        menuTitle = new Text("SMAC");
        menuNewGame = new Text("new Game");
        menuHelp = new Text("help");
        menuBox = new VBox();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuTitle.setId("title");
        menuHelp.setId("menuHelp");
        menuNewGame.setId("menuNewGame");

        menuBox.getChildren().add(menuNewGame);
        menuBox.getChildren().add(menuHelp);

        mainGrid.add(menuTitle,0,0);
        mainGrid.add(menuBox,0,1);

        //Help listener
        menuHelp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //todo
                System.out.println("link to redme");
            }
        });
    }
}


