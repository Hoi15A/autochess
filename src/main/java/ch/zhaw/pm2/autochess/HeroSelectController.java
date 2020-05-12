package ch.zhaw.pm2.autochess;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HeroSelectController implements Initializable {
    private VBox hero1Box;
    private VBox hero2Box;
    private VBox hero3Box;

    private ImageView hero1ImgV;
    private ImageView hero2ImgV;
    private ImageView hero3ImgV;

    private Image hero1Img;
    private Image hero2Img;
    private Image hero3Img;

    private RadioButton hero1Button;
    private RadioButton hero2Button;
    private RadioButton hero3Button;

    private ToggleGroup buttonGroup;

    private Button nextButton;

    @FXML
    GridPane mainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeHeroImages();
        initializeHeroButtons();
        initializeHeroBoxes();

        nextButton = new Button("next");

        mainGrid.add(hero1Box,0,0);
        mainGrid.add(hero2Box,1,0);
        mainGrid.add(hero3Box,2,0);
        mainGrid.add(nextButton,2,1);


        //nextbutton listener
        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //check player and decide if player 2 has to choose or shop will display
            }
        });


    }

    private void initializeHeroImages(){
        hero1Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        hero1ImgV = new ImageView(hero1Img);
        hero1ImgV.setFitWidth(100);
        hero1ImgV.setFitHeight(100);

        hero2Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        hero2ImgV = new ImageView(hero2Img);
        hero2ImgV.setFitWidth(100);
        hero2ImgV.setFitHeight(100);

        hero3Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        hero3ImgV = new ImageView(hero3Img);
        hero3ImgV.setFitWidth(100);
        hero3ImgV.setFitHeight(100);
    }

    private void initializeHeroButtons() {
        buttonGroup = new ToggleGroup();

        hero1Button = new RadioButton();
        hero2Button = new RadioButton();
        hero3Button = new RadioButton();

        hero1Button.setToggleGroup(buttonGroup);
        hero2Button.setToggleGroup(buttonGroup);
        hero3Button.setToggleGroup(buttonGroup);
    }

    private void initializeHeroBoxes() {
        hero1Box = new VBox();
        hero2Box = new VBox();
        hero3Box = new VBox();

        hero1Box.getChildren().add(hero1ImgV);
        hero1Box.getChildren().add(hero1Button);

        hero2Box.getChildren().add(hero2ImgV);
        hero2Box.getChildren().add(hero2Button);

        hero3Box.getChildren().add(hero3ImgV);
        hero3Box.getChildren().add(hero3Button);
    }
}
