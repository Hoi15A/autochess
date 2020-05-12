package ch.zhaw.pm2.autochess;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private File stylesheet = new File(String.valueOf(getClass().getResource("css/MainStylesheet.css")));
    private Text title;
    private Text menu[];


    @FXML
    GridPane mainGrid;
    @FXML
    RowConstraints titleRow;
    @FXML
    RowConstraints menuRow;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeMenu();
        initializeMenuTitle();

        mainGrid.add(title,0,0);
        mainGrid.add(menu[0],0,1);

        //new Game Listener
        menu[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("new game");

            }
        });

        //Help listener
        menu[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        menu[0].setText("new Game");
        menu[1].setText("Help");
    }
}

