package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.game.Game;
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
    private Text linkText;

    @FXML
    GridPane mainGrid;

    public MainMenuController(Game game){
        menuTitle = new Text("SMAC");
        menuNewGame = new Text("NEW GAME");
        menuHelp = new Text("HELP");
        menuBox = new VBox();
        linkText = new Text("https://github.zhaw.ch/PM2-IT19bWIN-benf-runm/gruppe05-alalau-projekt2-autochess/blob/develop/README.md");
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
        menuHelp.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                menuBox.getChildren().remove(linkText);
                menuBox.getChildren().add(linkText);
            }
        });
    }
}


