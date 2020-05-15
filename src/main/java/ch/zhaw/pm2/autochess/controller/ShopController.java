package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Game.Game;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalFundsReductionException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    private Text player1Text;
    private Text player2Text;
    private int p1Funds;
    private int p2Funds;



    private GridPane player1BuyPane;
    private GridPane player2BuyPane;
    private static final int P1_FIELD_COLS = 3;
    private static final int P2_FIELD_COLS = 3;
    private static final int P1_FIELD_ROWS = 3;
    ;
    private static final int P2_FIELD_ROWS = 3;

    private GridPane player1SellPane;
    private GridPane player2SellPane;

    private ListView<String> p1MinionList;
    private ObservableList<String> p1Items;

    private ListView<String> p2MinionList;
    private ObservableList<String> p2Items;

    protected Button nextButton;

    private Game game;

    @FXML
    private GridPane shopMainGrid;

    public ShopController(Game game) {
        this.game = game;
        nextButton = new Button("next");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlayerText();
        initializePlayerBuyView();
        initializePlayerSellView();
        initializeMinionList();

        /*todo
        -getCoin to display coins  -> getHeroFunds(int heroId)
         */

        shopMainGrid.add(player1BuyPane, 0, 1);
        shopMainGrid.add(player2BuyPane, 1, 1);
        shopMainGrid.add(player1SellPane, 0, 2);
        shopMainGrid.add(player2SellPane, 1, 2);

        shopMainGrid.add(nextButton, 1, 4);
    }

    private void initializePlayerText() {
        try {
            p1Funds = game.getHeroFunds(1);
            p2Funds = game.getHeroFunds(2);
        } catch (IllegalGameStateException e) {
            e.printStackTrace();
        }
        player1Text = new Text("Player 1 Shop | cash: "+p1Funds);
        player2Text = new Text("Player 2 Shop | cash: "+p2Funds);

        shopMainGrid.getChildren().remove(player1Text);
        shopMainGrid.getChildren().remove(player2Text);

        shopMainGrid.add(player1Text, 0, 0);
        shopMainGrid.add(player2Text, 1, 0);
    }

    private void initializePlayerBuyView() {

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

        player1BuyPane.add(new Label("Tank"), 0, 0);
        player1BuyPane.add(new Label(Config.TANK_PRICE+" $"), 1, 0);
        player1BuyPane.add(p1BuyTank, 2, 0);

        player1BuyPane.add(new Label("Warrior"), 0, 1);
        player1BuyPane.add(new Label(Config.WARRIOR_PRICE+" $"), 1, 1);
        player1BuyPane.add(p1BuyWarrior, 2, 1);

        player1BuyPane.add(new Label("Ranger"), 0, 2);
        player1BuyPane.add(new Label(Config.RANGER_PRICE+" $"), 1, 2);
        player1BuyPane.add(p1BuyRanger, 2, 2);

        p1BuyTank.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(1, Config.MinionType.TANK);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadMinionList();
            }
        });

        p1BuyWarrior.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(1, Config.MinionType.WARRIOR);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (IllegalFundsReductionException illegalFundsReductionException) {
                    System.out.println(illegalFundsReductionException.getStackTrace());
                }
                initializePlayerText();
                reloadMinionList();
            }
        });

        p1BuyRanger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(1, Config.MinionType.RANGER);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (IllegalFundsReductionException illegalFundsReductionException) {
                    System.out.println(illegalFundsReductionException.getStackTrace());
                }
                initializePlayerText();
                reloadMinionList();
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

        player2BuyPane.add(new Label("Tank"), 0, 0);
        player2BuyPane.add(new Label("10$"), 1, 0);
        player2BuyPane.add(p2BuyTank, 2, 0);

        player2BuyPane.add(new Label("Warrior"), 0, 1);
        player2BuyPane.add(new Label("10$"), 1, 1);
        player2BuyPane.add(p2BuyWarrior, 2, 1);

        player2BuyPane.add(new Label("Ranger"), 0, 2);
        player2BuyPane.add(new Label("10$"), 1, 2);
        player2BuyPane.add(p2BuyRanger, 2, 2);

        p2BuyTank.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(2, Config.MinionType.TANK);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (IllegalFundsReductionException illegalFundsReductionException) {
                    System.out.println(illegalFundsReductionException.getStackTrace());
                }
                reloadMinionList();
            }
        });

        p2BuyRanger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(2, Config.MinionType.RANGER);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (IllegalFundsReductionException illegalFundsReductionException) {
                    System.out.println(illegalFundsReductionException.getStackTrace());
                }
                reloadMinionList();
            }
        });

        p2BuyWarrior.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(2, Config.MinionType.WARRIOR);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (IllegalFundsReductionException illegalFundsReductionException) {
                    System.out.println(illegalFundsReductionException.getStackTrace());
                }
                reloadMinionList();
            }
        });
    }

    private void initializePlayerSellView() {
        //player 1
        player1SellPane = new GridPane();
        player1SellPane.setGridLinesVisible(true);

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            player1SellPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(50);
            player1SellPane.getRowConstraints().add(row);
        }

        Button p1SellButton = new Button("sell");
        TextField p1minionIDFiel = new TextField();

        player1SellPane.add(p1minionIDFiel, 0, 0);
        player1SellPane.add(p1SellButton, 1, 0);

        p1minionIDFiel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p1minionIDFiel.getText().matches("\\d*")) {
                p1minionIDFiel.setText(p1minionIDFiel.getText().replaceAll("[^\\d]", ""));
            }
        });

        p1SellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int minionID = Integer.parseInt(p1minionIDFiel.getText());
                try {
                    game.sellMinion(1, minionID);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (InvalidMinionIDException invalidMinionIDException) {
                    System.out.println(invalidMinionIDException.getStackTrace());
                }
                reloadMinionList();
                p1minionIDFiel.setText("");
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
        TextField p2minionIDFiel = new TextField();

        player2SellPane.add(p2minionIDFiel, 0, 0);
        player2SellPane.add(p2SellButton, 1, 0);

        p2minionIDFiel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p2minionIDFiel.getText().matches("\\d*")) {
                p2minionIDFiel.setText(p2minionIDFiel.getText().replaceAll("[^\\d]", ""));
            }
        });

        p2SellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int minionID = Integer.parseInt(p2minionIDFiel.getText());
                try {
                    game.sellMinion(2, minionID);
                } catch (IllegalGameStateException illegalGameStateException) {
                    System.out.println(illegalGameStateException.getStackTrace());
                } catch (InvalidMinionIDException invalidMinionIDException) {
                    System.out.println(invalidMinionIDException.getStackTrace());
                }
                reloadMinionList();
                p2minionIDFiel.setText("");
            }
        });
    }

    private void initializeMinionList() {
        p1MinionList = new ListView<String>();
        p1Items = FXCollections.observableArrayList();

        p2MinionList = new ListView<String>();
        p2Items = FXCollections.observableArrayList();

        /*todo
        getAllMinionIds(int heroId) -> getMinionInfoAsString(int heroId, int minionId)
        iterate over minion list and add every minion to this list
         */

        p1MinionList.setItems(p1Items);
        shopMainGrid.add(p1MinionList, 0, 3);

        /*todo
        getAllMinionIds(int heroId) -> getMinionInfoAsString(int heroId, int minionId)
        iterate over minion list and add every minion to this list
         */

        p2MinionList.setItems(p2Items);
        shopMainGrid.add(p2MinionList, 1, 3);
    }

    private void reloadMinionList() {
        p1MinionList.getItems().clear();
        p2MinionList.getItems().clear();
        try {
            for (int id : game.getAllMinionIds(1)) {
                String c = game.getMinionInfoAsString(1, id);
                p1MinionList.getItems().add(c);
            }
        } catch (IllegalGameStateException e) {
            e.printStackTrace();
        } catch (InvalidMinionIDException e) {
            e.printStackTrace();
        }
        try {
            for (int id : game.getAllMinionIds(2)) {
                String c = game.getMinionInfoAsString(2, id);
                p2MinionList.getItems().add(c);
            }
        } catch (IllegalGameStateException e) {
            e.printStackTrace();
        } catch (InvalidMinionIDException e) {
            e.printStackTrace();
        }
    }
}