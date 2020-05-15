package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Game.Game;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class of the HeroSelectWindow.
 * It initializes all the components.
 */
public class HeroSelectController implements Initializable {
    //data fields
    protected Game game;
    protected Button nextButton;

    //Player 1 data fields (p1 = player1 h1 = hero 1)
    private Text p1Text;

    private VBox p1H1VBox;
    private VBox p1H2VBox;
    private VBox p1H3VBox;

    private ImageView p1H1ImgView;
    private ImageView p1H2ImgView;
    private ImageView p1H3ImgView;

    private Image p1H1Img;
    private Image p1H2Img;
    private Image p1H3Img;

    private ToggleGroup p1ToggleGroup;

    private RadioButton p1H1RadioButton;
    private RadioButton p1H2RadioButton;
    private RadioButton p1H3RadioButton;

    private Config.HeroType heroTypeP1;


    //Player 2 data fields (p1 = player1 h1 = hero 1)
    private Text p2Text;

    private VBox p2H1VBox;
    private VBox p2H2VBox;
    private VBox p2H3VBox;

    private ImageView p2H1ImgView;
    private ImageView p2H2ImgView;
    private ImageView p2H3ImgView;

    private Image p2H1Img;
    private Image p2H2Img;
    private Image p2H3Img;

    private ToggleGroup p2ToggleGroup;

    private RadioButton p2H1RadioButton;
    private RadioButton p2H2RadioButton;
    private RadioButton p2H3RadioButton;

    private Config.HeroType heroTypeP2;

    //fxml data fields
    @FXML
    GridPane mainGrid;


    public HeroSelectController(Game currentGame) {
        game = currentGame;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlayerText();
        initializeHeroImages();
        initializeHeroButtons();
        initializeVBox();
        addComponentsToMainGrid();
    }

    private void addComponentsToMainGrid() {
        //Add playerText to mainGrid
        mainGrid.add(p1Text, 1, 0);
        mainGrid.add(p2Text, 1, 2);


        //Add player VBox to mainGrid
        mainGrid.add(p1H1VBox, 0, 1);
        mainGrid.add(p1H2VBox, 1, 1);
        mainGrid.add(p1H3VBox, 2, 1);

        mainGrid.add(p2H1VBox, 0, 3);
        mainGrid.add(p2H2VBox, 1, 3);
        mainGrid.add(p2H3VBox, 2, 3);

        //Add next Button to mainGrid
        nextButton = new Button("Next");
        mainGrid.add(nextButton, 2, 4);
    }

    private void initializePlayerText() {
        p1Text = new Text("Player 1");
        p2Text = new Text("Player 2");
    }

    private void initializeHeroImages() {
        //Add Hero1 image to player1 Image view, set Size of ImageView
        p1H1Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        p1H1ImgView = new ImageView(p1H1Img);
        p1H1ImgView.setFitWidth(100);
        p1H1ImgView.setFitHeight(100);

        //Add Hero2 image to player1  Image view, set Size of ImageView
        p1H2Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        p1H2ImgView = new ImageView(p1H2Img);
        p1H2ImgView.setFitWidth(100);
        p1H2ImgView.setFitHeight(100);

        //Add Hero2 image to player1  Image view, set Size of ImageView
        p1H3Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        p1H3ImgView = new ImageView(p1H3Img);
        p1H3ImgView.setFitWidth(100);
        p1H3ImgView.setFitHeight(100);

        //Add Hero1 image to player2 Image view, set Size of ImageView
        p2H1Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        p2H1ImgView = new ImageView(p2H1Img);
        p2H1ImgView.setFitWidth(100);
        p2H1ImgView.setFitHeight(100);

        //Add Hero2 image to player2  Image view, set Size of ImageView
        p2H2Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        p2H2ImgView = new ImageView(p2H2Img);
        p2H2ImgView.setFitWidth(100);
        p2H2ImgView.setFitHeight(100);

        //Add Hero2 image to player2  Image view, set Size of ImageView
        p2H3Img = new Image(String.valueOf(getClass().getResource("images/minion.jpg")));
        p2H3ImgView = new ImageView(p2H3Img);
        p2H3ImgView.setFitWidth(100);
        p2H3ImgView.setFitHeight(100);
    }

    private void initializeHeroButtons() {
        //player 1
        p1ToggleGroup = new ToggleGroup();

        p1H1RadioButton = new RadioButton();
        p1H2RadioButton = new RadioButton();
        p1H3RadioButton = new RadioButton();

        p1H1RadioButton.setSelected(true);

        p1H1RadioButton.setToggleGroup(p1ToggleGroup);
        p1H2RadioButton.setToggleGroup(p1ToggleGroup);
        p1H3RadioButton.setToggleGroup(p1ToggleGroup);

        //player 2
        p2ToggleGroup = new ToggleGroup();

        p2H1RadioButton = new RadioButton();
        p2H2RadioButton = new RadioButton();
        p2H3RadioButton = new RadioButton();

        p2H1RadioButton.setSelected(true);

        p2H1RadioButton.setToggleGroup(p2ToggleGroup);
        p2H2RadioButton.setToggleGroup(p2ToggleGroup);
        p2H3RadioButton.setToggleGroup(p2ToggleGroup);
    }

    private void initializeVBox() {
        //Inizialize VBoxes
        p1H1VBox = new VBox();
        p1H2VBox = new VBox();
        p1H3VBox = new VBox();

        p2H1VBox = new VBox();
        p2H2VBox = new VBox();
        p2H3VBox = new VBox();


        //Add elements to Player1 VBoxes
        p1H1VBox.getChildren().add(p1H1ImgView);
        p1H1VBox.getChildren().add(p1H1RadioButton);

        p1H2VBox.getChildren().add(p1H2ImgView);
        p1H2VBox.getChildren().add(p1H2RadioButton);

        p1H3VBox.getChildren().add(p1H3ImgView);
        p1H3VBox.getChildren().add(p1H3RadioButton);

        //Add elements to Player2 VBoxes
        p2H1VBox.getChildren().add(p2H1ImgView);
        p2H1VBox.getChildren().add(p2H1RadioButton);

        p2H2VBox.getChildren().add(p2H2ImgView);
        p2H2VBox.getChildren().add(p2H2RadioButton);

        p2H3VBox.getChildren().add(p2H3ImgView);
        p2H3VBox.getChildren().add(p2H3RadioButton);
    }

    /**
     * This Method handles the Action, if the next button is clicked
     * It creates a new Game
     * @param currentStage
     */
    public void nextButtonClicked(Stage currentStage) {
        if (p1H1RadioButton.equals(p1ToggleGroup.getSelectedToggle())) {
            heroTypeP1 = Config.HeroType.ALIEN;
        } else if (p1H2RadioButton.equals(p1ToggleGroup.getSelectedToggle())) {
            heroTypeP1 = Config.HeroType.SPACE_MARINE;
        } else if (p1H3RadioButton.equals(p1ToggleGroup.getSelectedToggle())) {
            heroTypeP1 = Config.HeroType.ENGINEER;
        }

        if (p2H1RadioButton.equals(p2ToggleGroup.getSelectedToggle())) {
            heroTypeP2 = Config.HeroType.ALIEN;
        } else if (p2H2RadioButton.equals(p2ToggleGroup.getSelectedToggle())) {
            heroTypeP2 = Config.HeroType.SPACE_MARINE;
        } else if (p2H3RadioButton.equals(p2ToggleGroup.getSelectedToggle())) {
            heroTypeP2 = Config.HeroType.ENGINEER;
        }

        try {
            game = new Game(heroTypeP1, heroTypeP2);
        } catch (IllegalGameStateException illegalGameStateException) {
        }
    }
}
