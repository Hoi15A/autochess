package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.board.BattleLog;
import ch.zhaw.pm2.autochess.game.Game;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mainclass of the Application
 * Holds a Game and handles windows to displays
 */
public class MainApp extends Application {
    private static final Logger logger = Logger.getLogger(MainApp.class.getCanonicalName());
    protected Game game;

    //MainMenu data fields
    private MainMenuController mainMenuController;
    private Stage mainMenuStage;
    private FXMLLoader mainMenuLoader;
    private Pane mainMenuPane;
    private Scene mainMenuScene;

    //HeroSelect data fields
    private HeroSelectController heroSelectController;
    private Stage heroSelectStage;
    private FXMLLoader heroSelectLoader;

    //Shop data fields
    private ShopController shopController;
    private Stage shopStage;
    private FXMLLoader shopLoader;

    //Game data fields
    private GameController gameController;
    private Stage gameStage;
    private FXMLLoader gameLoader;


    /**
     * Main Method of the application.
     * Launches the App.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Thiss Method loads the the MainMenu.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
        loadMainMenu();
    }

    /**
     * This Method loads the MainMenuWindow
     */
    private void loadMainMenu() {
        mainMenuStage = new Stage();
        mainMenuController = new MainMenuController(game);
        try {
            mainMenuLoader = new FXMLLoader(getClass().getResource("fxml/MainMenuWindow.fxml"));
            mainMenuLoader.setController(mainMenuController);

            mainMenuPane = mainMenuLoader.load();
            mainMenuPane.getStylesheets().add(String.valueOf(getClass().getResource("css/MainMenuStylesheet.css")));
            mainMenuPane.setId("mainMenuPane");

            mainMenuScene = new Scene(mainMenuPane);

            mainMenuStage.setResizable(false);
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setMinWidth(800);
            mainMenuStage.setMinHeight(600);
            mainMenuStage.setTitle("SMAC");
            mainMenuStage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error starting up MainMenu" + e.getMessage());
        }
        mainMenuController.menuNewGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainMenuStage.close();
                loadHeroSelectWindow();
            }
        });
    }

    /**
     * This Method loads the HeroSelectWindow
     */
    protected void loadHeroSelectWindow() {
        heroSelectStage = new Stage();
        heroSelectController = new HeroSelectController(game);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HeroSelectWindow.fxml"));
            loader.setController(heroSelectController);
            Pane heroSelectPane = loader.load();

            heroSelectPane.getStylesheets().add(String.valueOf(getClass().getResource("css/HeroSelectStylesheet.css")));
            heroSelectPane.setId("mainMenuPane");
            Scene heroSelectScene = new Scene(heroSelectPane);

            heroSelectStage.setResizable(false);
            heroSelectStage.setScene(heroSelectScene);
            heroSelectStage.setMinWidth(800);
            heroSelectStage.setMinHeight(600);
            heroSelectStage.setTitle("SMAC");
            heroSelectStage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        heroSelectController.nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                heroSelectController.nextButtonClicked(heroSelectStage);
                game = heroSelectController.game;
                heroSelectStage.close();
                loadShopWindow();
            }
        });
    }

    /**
     * This Method loads the ShopWindow
     */
    protected void loadShopWindow() {
        shopStage = new Stage();
        shopController = new ShopController(game);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ShopWindow.fxml"));
            loader.setController(shopController);
            Pane shopPane = loader.load();

            shopPane.getStylesheets().add(String.valueOf(getClass().getResource("css/ShopStylesheet.css")));
            shopPane.setId("shopPane");
            Scene shopScene = new Scene(shopPane);

            shopStage.setResizable(false);
            shopStage.setScene(shopScene);
            shopStage.setMinWidth(800);
            shopStage.setMinHeight(600);
            shopStage.setTitle("SMAC");
            shopStage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        shopController.nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shopStage.close();
                loadGameWindow();
            }
        });
    }

    /**
     * This Method loads the GameWindow
     */
    private void loadGameWindow() {
        gameStage = new Stage();
        gameController = new GameController(game);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/GameWindow.fxml"));
            loader.setController(gameController);
            Pane gamePane = loader.load();

            gamePane.getStylesheets().add(String.valueOf(getClass().getResource("css/GameStylesheet.css")));
            gamePane.setId("gamePane");
            Scene gameScene = new Scene(gamePane);

            gameStage.setResizable(false);
            gameStage.setScene(gameScene);
            gameStage.setMinWidth(800);
            gameStage.setMinHeight(600);
            gameStage.setTitle("SMAC");
            gameStage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
        gameController.battleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gameController.doBattle();

                ListView<String> battleLogList = new ListView<>();

                for(BattleLog battleLog : game.getBattleLog()){
                    String battleLogString = battleLog.toString();
                    battleLogList.getItems().add(battleLogString);

                }

                if (game.getWinner() == -1) {
                    loadShopWindow();
                    gameStage.close();

                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(shopStage);
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.getChildren().add(new Text("Round over, close this Window to shop Minions for the next round!"));
                    try {
                        dialogVbox.getChildren().add(new Text("Player1: "+game.getHeroHealth(1)+" health"));
                        dialogVbox.getChildren().add(new Text("Player2: "+game.getHeroHealth(2)+" health"));
                        dialogVbox.getChildren().add(new Text("BattleLog:"));
                        dialogVbox.getChildren().add(battleLogList);

                    } catch (IllegalGameStateException e) {
                        e.printStackTrace();
                    }
                    Scene dialogScene = new Scene(dialogVbox, 400, 300);
                    dialog.setScene(dialogScene);
                    dialog.show();
                } else {
                    gameStage.close();
                    loadMainMenu();
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(mainMenuStage);
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.getChildren().add(new Text("Game Over!  Player " + game.getWinner() + " won"));
                    dialogVbox.getChildren().add(new Text("BattleLog:"));
                    dialogVbox.getChildren().add(battleLogList);
                    Scene dialogScene = new Scene(dialogVbox, 400, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
            }
        });
    }
}