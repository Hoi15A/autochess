package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Game.Game;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HeroSelectController implements Initializable {
    private Text player1;
    private Text player2;

    private VBox player1Hero1Box;
    private VBox player1Hero2Box;
    private VBox player1Hero3Box;

    private VBox player2Hero1Box;
    private VBox player2Hero2Box;
    private VBox player2Hero3Box;

    private ImageView player1Hero1ImgV;
    private ImageView player1Hero2ImgV;
    private ImageView player1Hero3ImgV;

    private ImageView player2Hero1ImgV;
    private ImageView player2Hero2ImgV;
    private ImageView player2Hero3ImgV;

    private Image player1Hero1Img;
    private Image player1Hero2Img;
    private Image player1Hero3Img;

    private Image player2Hero1Img;
    private Image player2Hero2Img;
    private Image player2Hero3Img;

    private RadioButton player1Hero1Button;
    private RadioButton player1Hero2Button;
    private RadioButton player1Hero3Button;

    private RadioButton player2Hero1Button;
    private RadioButton player2Hero2Button;
    private RadioButton player2Hero3Button;

    private ToggleGroup player1ButtonGroup;
    private ToggleGroup player2ButtonGroup;

    protected Button nextButton;

    private Config.HeroType heroTypePlayer1;
    private Config.HeroType heroTypePlayer2;

    protected Game game;

    @FXML
    GridPane mainGrid;

    public HeroSelectController(Game game) {
        this.game = game;
        nextButton = new Button("next");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeHeroImages();
        initializeHeroButtons();
        initializePlayers();
        initializePlayer();

        //player1 title
        mainGrid.add(player1, 1, 0);

        //player1 images and buttons
        mainGrid.add(player1Hero1Box, 0, 1);
        mainGrid.add(player1Hero2Box, 1, 1);
        mainGrid.add(player1Hero3Box, 2, 1);

        //player2 title
        mainGrid.add(player2, 1, 2);

        //player2 images and buttons
        mainGrid.add(player2Hero1Box, 0, 3);
        mainGrid.add(player2Hero2Box, 1, 3);
        mainGrid.add(player2Hero3Box, 2, 3);

        //next button
        nextButton = new Button("next");
        mainGrid.add(nextButton, 2, 4);
    }

    public void nextButtonClicked(Stage currentStage) {
        if (player1Hero1Button.equals(player1ButtonGroup.getSelectedToggle())) {
            heroTypePlayer1 = Config.HeroType.ALIEN;
        } else if (player1Hero2Button.equals(player1ButtonGroup.getSelectedToggle())) {
            heroTypePlayer1 = Config.HeroType.SPACE_MARINE;
        } else if (player1Hero3Button.equals(player1ButtonGroup.getSelectedToggle())) {
            heroTypePlayer1 = Config.HeroType.ENGINEER;
        } else {
            System.out.println("yeet");
        }

        if (player2Hero1Button.equals(player2ButtonGroup.getSelectedToggle())) {
            heroTypePlayer2 = Config.HeroType.ALIEN;
        } else if (player2Hero2Button.equals(player2ButtonGroup.getSelectedToggle())) {
            heroTypePlayer2 = Config.HeroType.SPACE_MARINE;
        } else if (player2Hero3Button.equals(player2ButtonGroup.getSelectedToggle())) {
            heroTypePlayer2 = Config.HeroType.ENGINEER;
        } else {
            System.out.println("yeet");
        }

        try {
            game = new Game(heroTypePlayer1, heroTypePlayer2);
        } catch (IllegalGameStateException illegalGameStateException) {
            /* todo popup
            final Popup popup = new Popup();
            popup.setX(300);
            popup.setY(200);
            popup.getContent().addAll(new Text("something went wrong"));
            popup.show(currentStage);
            */
            System.out.println(illegalGameStateException.getStackTrace());
        }
    }

    private void initializePlayers() {
        player1 = new Text("Player 1");
        player2 = new Text("Player 2");

    }

    private void initializeHeroImages() {
        //player 1
        player1Hero1Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        player1Hero1ImgV = new ImageView(player1Hero1Img);
        player1Hero1ImgV.setFitWidth(100);
        player1Hero1ImgV.setFitHeight(100);

        player1Hero2Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        player1Hero2ImgV = new ImageView(player1Hero2Img);
        player1Hero2ImgV.setFitWidth(100);
        player1Hero2ImgV.setFitHeight(100);

        player1Hero3Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        player1Hero3ImgV = new ImageView(player1Hero3Img);
        player1Hero3ImgV.setFitWidth(100);
        player1Hero3ImgV.setFitHeight(100);

        //player 2
        player2Hero1Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        player2Hero1ImgV = new ImageView(player2Hero1Img);
        player2Hero1ImgV.setFitWidth(100);
        player2Hero1ImgV.setFitHeight(100);

        player2Hero2Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        player2Hero2ImgV = new ImageView(player2Hero2Img);
        player2Hero2ImgV.setFitWidth(100);
        player2Hero2ImgV.setFitHeight(100);

        player2Hero3Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        player2Hero3ImgV = new ImageView(player2Hero3Img);
        player2Hero3ImgV.setFitWidth(100);
        player2Hero3ImgV.setFitHeight(100);
    }

    private void initializeHeroButtons() {
        //player 1
        player1ButtonGroup = new ToggleGroup();

        player1Hero1Button = new RadioButton();
        player1Hero2Button = new RadioButton();
        player1Hero3Button = new RadioButton();

        player1Hero1Button.setSelected(true);

        player1Hero1Button.setToggleGroup(player1ButtonGroup);
        player1Hero2Button.setToggleGroup(player1ButtonGroup);
        player1Hero3Button.setToggleGroup(player1ButtonGroup);

        //player 2
        player2ButtonGroup = new ToggleGroup();

        player2Hero1Button = new RadioButton();
        player2Hero2Button = new RadioButton();
        player2Hero3Button = new RadioButton();

        player2Hero1Button.setSelected(true);

        player1Hero1Button.setId("player1Hero1Button");
        player2Hero2Button.setId("player2Hero2Button");
        player2Hero3Button.setId("player2Hero3Button");

        player2Hero1Button.setToggleGroup(player2ButtonGroup);
        player2Hero2Button.setToggleGroup(player2ButtonGroup);
        player2Hero3Button.setToggleGroup(player2ButtonGroup);
    }

    private void initializePlayer() {
        //player 1
        player1Hero1Box = new VBox();
        player1Hero2Box = new VBox();
        player1Hero3Box = new VBox();

        player1Hero1Box.getChildren().add(player1Hero1ImgV);
        player1Hero1Box.getChildren().add(player1Hero1Button);

        player1Hero2Box.getChildren().add(player1Hero2ImgV);
        player1Hero2Box.getChildren().add(player1Hero2Button);

        player1Hero3Box.getChildren().add(player1Hero3ImgV);
        player1Hero3Box.getChildren().add(player1Hero3Button);

        //player 2
        player2Hero1Box = new VBox();
        player2Hero2Box = new VBox();
        player2Hero3Box = new VBox();

        player2Hero1Box.getChildren().add(player2Hero1ImgV);
        player2Hero1Box.getChildren().add(player2Hero1Button);

        player2Hero2Box.getChildren().add(player2Hero2ImgV);
        player2Hero2Box.getChildren().add(player2Hero2Button);

        player2Hero3Box.getChildren().add(player2Hero3ImgV);
        player2Hero3Box.getChildren().add(player2Hero3Button);
    }
}
