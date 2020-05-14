package ch.zhaw.pm2.autochess.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopController implements Initializable {
    private Text player1Text;
    private Text player2Text;

    private GridPane player1BuyPane;
    private GridPane player2BuyPane;
    private static final int P1_FIELD_COLS = 3;
    private static final int P2_FIELD_COLS = 3;
    private static final int P1_FIELD_ROWS = 3;;
    private static final int P2_FIELD_ROWS = 3;

    private GridPane player1SellPane;
    private GridPane player2SellPane;


    @FXML
    private GridPane shopMainGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlayerText();
        initializePlayerBuyView();
        initializePlayerSellView();
        initializeMinionList();

        /*todo
        -getCoin to display coins  -> getHeroFunds(int heroId)
         */


        shopMainGrid.add(player1Text,0,0);
        shopMainGrid.add(player2Text,1,0);
        shopMainGrid.add(player1BuyPane,0,1);
        shopMainGrid.add(player2BuyPane,1,1);
        shopMainGrid.add(player1SellPane,0,2);
        shopMainGrid.add(player2SellPane,1,2);


        Button nextButton = new Button("next");
        shopMainGrid.add(nextButton,1,4);

        nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                loadGameWindow();
            }
        });
    }

    private void loadGameWindow(){
        Stage heroSelectStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/GameWindow.fxml"));
            Pane gamePane = loader.load();

            Stage gamestage = new Stage();
            gamestage.setTitle("SMAC");
            gamestage.setScene(new Scene(gamePane, 800, 600));
            gamestage.setResizable(false);
            gamestage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    private void initializePlayerText(){
        player1Text = new Text("Player 1 Shop");
        player2Text = new Text("Player 2 Shop");
    }

    private void initializePlayerBuyView(){

        //player 1
        player1BuyPane = new GridPane();
        player1BuyPane.setId("player1BuyPane");
        player1BuyPane.setGridLinesVisible(true);

        for (int i = 0; i < P1_FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            player1BuyPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < P1_FIELD_ROWS; i++) {
            RowConstraints row = new RowConstraints(50);
            player1BuyPane.getRowConstraints().add(row);
        }

        //get herotype for label
        //get cost for label
        Button p1BuyTank = new Button("buy");
        Button p1BuyWarrior = new Button("buy");
        Button p1BuyRanger = new Button("buy");

        player1BuyPane.add(new Label("Tank"),0,0);
        player1BuyPane.add(new Label("10$"),1,0);
        player1BuyPane.add(p1BuyTank,2,0);

        player1BuyPane.add(new Label("Warrior"),0,1);
        player1BuyPane.add(new Label("10$"),1,1);
        player1BuyPane.add(p1BuyWarrior,2,1);

        player1BuyPane.add(new Label("Ranger"),0,2);
        player1BuyPane.add(new Label("10$"),1,2);
        player1BuyPane.add(p1BuyRanger,2,2);

        p1BuyTank.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buyMinion(int heroId, Config.MinionType minionType)
                //todo refresh list
            }
        });

        p1BuyWarrior.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buyMinion(int heroId, Config.MinionType minionType)
                //todo refresh list
            }
        });

        p1BuyRanger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buyMinion(int heroId, Config.MinionType minionType)
                //todo refresh list
            }
        });




        //player 2
        player2BuyPane = new GridPane();
        player2BuyPane.setId("player2BuyPane");
        player2BuyPane.setGridLinesVisible(true);

        for (int i = 0; i < P2_FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            player2BuyPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < P2_FIELD_ROWS; i++) {
            RowConstraints row = new RowConstraints(50);
            player2BuyPane.getRowConstraints().add(row);
        }

        //get herotype for label
        //get cost for label
        Button p2BuyTank = new Button("buy");
        Button p2BuyWarrior = new Button("buy");
        Button p2BuyRanger = new Button("buy");

        player2BuyPane.add(new Label("Tank"),0,0);
        player2BuyPane.add(new Label("10$"),1,0);
        player2BuyPane.add(p2BuyTank,2,0);

        player2BuyPane.add(new Label("Warrior"),0,1);
        player2BuyPane.add(new Label("10$"),1,1);
        player2BuyPane.add(p2BuyWarrior,2,1);

        player2BuyPane.add(new Label("Ranger"),0,2);
        player2BuyPane.add(new Label("10$"),1,2);
        player2BuyPane.add(p2BuyRanger,2,2);

        p2BuyTank.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buyMinion(int heroId, Config.MinionType minionType)
                //todo refresh list
            }
        });

        p2BuyRanger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buyMinion(int heroId, Config.MinionType minionType)
                //todo refresh list
            }
        });

        p2BuyRanger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //buyMinion(int heroId, Config.MinionType minionType)
                //todo refresh list
            }
        });
    }

    private void initializePlayerSellView(){
        //player 1
        player1SellPane = new GridPane();
        player1SellPane.setGridLinesVisible(true);

        for (int i = 0; i < P1_FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            player1SellPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(50);
            player1SellPane.getRowConstraints().add(row);
        }

        Button p1SellButton = new Button("sell");
        TextField p1xFiel = new TextField();
        TextField p1yFiel = new TextField();

        player1SellPane.add(p1xFiel,0,0);
        player1SellPane.add(p1yFiel,1,0);
        player1SellPane.add(p1SellButton,2,0);

        p1SellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //sellMinion(int heroId, int minionId);
            }
        });

        //player 2
        player2SellPane = new GridPane();
        player2SellPane.setGridLinesVisible(true);

        for (int i = 0; i < P1_FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            player2SellPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(50);
            player2SellPane.getRowConstraints().add(row);
        }

        Button p2SellButton = new Button("sell");
        TextField p2xFiel = new TextField();
        TextField p2yFiel = new TextField();

        player2SellPane.add(p2xFiel,0,0);
        player2SellPane.add(p2yFiel,1,0);
        player2SellPane.add(p2SellButton,2,0);

        p2SellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //sellMinion(int heroId, int minionId);
            }
        });
    }

    private void initializeMinionList(){
        ListView<String> p1MinionList = new ListView<String>();
        ObservableList<String> p1Items =FXCollections.observableArrayList ();
        /*todo
        getAllMinionIds(int heroId) -> getMinionInfoAsString(int heroId, int minionId)
        iterate over minion list and add every minion to this list
         */

        p1MinionList.setItems(p1Items);
        shopMainGrid.add(p1MinionList,0,3);



        ListView<String> p2MinionList = new ListView<String>();
        ObservableList<String> p2Items =FXCollections.observableArrayList ();
        /*todo
        getAllMinionIds(int heroId) -> getMinionInfoAsString(int heroId, int minionId)
        iterate over minion list and add every minion to this list
         */

        p2MinionList.setItems(p2Items);
        shopMainGrid.add(p2MinionList,1,3);
    }
}