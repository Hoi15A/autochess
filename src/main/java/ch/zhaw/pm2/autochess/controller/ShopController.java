package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.game.Game;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.hero.exceptions.IllegalFundsReductionException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidMinionIDException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class of the ShopWindow.
 * It initializes all the components.
 */
public class ShopController implements Initializable {
    //data fields
    protected Game game;
    protected Button nextButton;

    private static final int BUY_FIELD_ROWS = 3;
    private static final int BUY_FIELD_COLS = 3;

    private static final Config.MinionType TANK = Config.MinionType.TANK;
    private static final Config.MinionType WARRIOR = Config.MinionType.WARRIOR;
    private static final Config.MinionType RANGER = Config.MinionType.RANGER;

    //Player 1 data fields (p1 = player1 h1 = hero 1)
    private Text p1Text;

    private int p1Funds;

    private GridPane p1BuySectionGridPane;
    private GridPane p1SellSectionGridPane;

    private ListView<String> p1MinionList;
    private ObservableList<String> p1Items;

    private Button p1BuyTankButton;
    private Button p1BuyWarriorButton;
    private Button p1BuyRangerButton;

    private Button p1SellButton;
    private TextField p1minionIDFiel;


    //Player 1 data fields (p1 = player1 h1 = hero 1)
    private Text p2Text;

    private int p2Funds;

    private GridPane p2BuySectionGridPane;
    private GridPane p2SellSectionGridPane;

    private ListView<String> p2MinionList;
    private ObservableList<String> p2Items;

    private Button p2BuyTankButton;
    private Button p2BuyWarriorButton;
    private Button p2BuyRangerButton;

    private Button p2SellButton;
    private TextField p2minionIDFiel;


    //fxml data fields
    @FXML
    private GridPane shopMainGrid;

    public ShopController(Game currentGame) {
        game = currentGame;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializePlayerText();
        initializePlayer1BuyView();
        initializePlayer2BuyView();
        initializePlayer1SellView();
        initializePlayer2SellView();
        initializePlayer1MinionList();
        initializePlayer2MinionList();
        reloadPlayer1MinionList();
        reloadPlayer2MinionList();
        addComponentsToMainGrid();
    }

    private void addComponentsToMainGrid() {
        shopMainGrid.add(p1BuySectionGridPane, 0, 1);
        shopMainGrid.add(p1SellSectionGridPane, 0, 2);

        shopMainGrid.add(p2SellSectionGridPane, 1, 2);
        shopMainGrid.add(p2BuySectionGridPane, 1, 1);

        nextButton = new Button("Next");
        shopMainGrid.add(nextButton, 1, 4);
    }

    private void initializePlayerText() {
        try {
            p1Funds = game.getHeroFunds(1);
            p2Funds = game.getHeroFunds(2);
        } catch (IllegalGameStateException e) {
            e.printStackTrace();
        }

        shopMainGrid.getChildren().remove(p1Text);
        shopMainGrid.getChildren().remove(p2Text);

        p1Text = new Text("Player 1 Shop | cash: " + p1Funds);
        p2Text = new Text("Player 2 Shop | cash: " + p2Funds);

        shopMainGrid.add(p1Text, 0, 0);
        shopMainGrid.add(p2Text, 1, 0);
    }

    private void initializePlayer1BuyView() {
        p1BuySectionGridPane = new GridPane();
        p1BuyTankButton = new Button("Buy");
        p1BuyWarriorButton = new Button("Buy");
        p1BuyRangerButton = new Button("Buy");

        for (int i = 0; i < BUY_FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            p1BuySectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < BUY_FIELD_ROWS; i++) {
            RowConstraints row = new RowConstraints(50);
            p1BuySectionGridPane.getRowConstraints().add(row);
        }

        p1BuySectionGridPane.add(new Label(TANK.toString()), 0, 0);
        p1BuySectionGridPane.add(new Label(Config.TANK_PRICE + " $"), 1, 0);
        p1BuySectionGridPane.add(p1BuyTankButton, 2, 0);

        p1BuySectionGridPane.add(new Label(WARRIOR.toString()), 0, 1);
        p1BuySectionGridPane.add(new Label(Config.WARRIOR_PRICE + " $"), 1, 1);
        p1BuySectionGridPane.add(p1BuyWarriorButton, 2, 1);

        p1BuySectionGridPane.add(new Label(RANGER.toString()), 0, 2);
        p1BuySectionGridPane.add(new Label(Config.RANGER_PRICE + " $"), 1, 2);
        p1BuySectionGridPane.add(p1BuyRangerButton, 2, 2);

        p1BuyTankButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(1, TANK);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer1MinionList();
            }
        });

        p1BuyWarriorButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(1, WARRIOR);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer1MinionList();
            }
        });

        p1BuyRangerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(1, RANGER);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer1MinionList();
            }
        });
    }

    private void initializePlayer2BuyView() {
        p2BuySectionGridPane = new GridPane();
        p2BuyTankButton = new Button("Buy");
        p2BuyWarriorButton = new Button("Buy");
        p2BuyRangerButton = new Button("Buy");

        for (int i = 0; i < BUY_FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            p2BuySectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < BUY_FIELD_ROWS; i++) {
            RowConstraints row = new RowConstraints(50);
            p2BuySectionGridPane.getRowConstraints().add(row);
        }

        p2BuySectionGridPane.add(new Label(TANK.toString()), 0, 0);
        p2BuySectionGridPane.add(new Label(Config.TANK_PRICE + " $"), 1, 0);
        p2BuySectionGridPane.add(p2BuyTankButton, 2, 0);

        p2BuySectionGridPane.add(new Label(WARRIOR.toString()), 0, 1);
        p2BuySectionGridPane.add(new Label(Config.WARRIOR_PRICE + " $"), 1, 1);
        p2BuySectionGridPane.add(p2BuyWarriorButton, 2, 1);

        p2BuySectionGridPane.add(new Label(RANGER.toString()), 0, 2);
        p2BuySectionGridPane.add(new Label(Config.RANGER_PRICE + " $"), 1, 2);
        p2BuySectionGridPane.add(p2BuyRangerButton, 2, 2);

        p2BuyTankButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(2, TANK);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer2MinionList();
            }
        });

        p2BuyWarriorButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(2, WARRIOR);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer2MinionList();
            }
        });

        p2BuyRangerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.buyMinion(2, RANGER);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (IllegalFundsReductionException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer2MinionList();
            }
        });
    }


    private void initializePlayer1SellView() {
        p1SellSectionGridPane = new GridPane();
        p1SellButton = new Button("Sell");
        p1minionIDFiel = new TextField();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            p1SellSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(50);
            p1SellSectionGridPane.getRowConstraints().add(row);
        }


        p1SellSectionGridPane.add(p1minionIDFiel, 0, 0);
        p1SellSectionGridPane.add(p1SellButton, 1, 0);

        //Validate userinput. Check if numeric
        p1minionIDFiel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (p1minionIDFiel.getText().matches("\\d*")) {
            } else {
                p1minionIDFiel.setText(p1minionIDFiel.getText().replaceAll("[^\\d]", ""));
            }
        });

        p1SellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int minionID = Integer.parseInt(p1minionIDFiel.getText());
                try {
                    game.sellMinion(1, minionID);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (InvalidMinionIDException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer1MinionList();
                p1minionIDFiel.setText("");
            }
        });
    }

    private void initializePlayer2SellView() {
        p2SellSectionGridPane = new GridPane();
        p2SellButton = new Button("Sell");
        p2minionIDFiel = new TextField();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(115);
            p2SellSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(50);
            p2SellSectionGridPane.getRowConstraints().add(row);
        }


        p2SellSectionGridPane.add(p2minionIDFiel, 0, 0);
        p2SellSectionGridPane.add(p2SellButton, 1, 0);

        //Validate userinput. Check if numeric
        p2minionIDFiel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (p2minionIDFiel.getText().matches("\\d*")) {
            } else {
                p2minionIDFiel.setText(p2minionIDFiel.getText().replaceAll("[^\\d]", ""));
            }
        });

        p2SellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int minionID = Integer.parseInt(p2minionIDFiel.getText());
                try {
                    game.sellMinion(2, minionID);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                } catch (InvalidMinionIDException e) {
                    e.printStackTrace();
                }
                initializePlayerText();
                reloadPlayer2MinionList();
                p2minionIDFiel.setText("");
            }
        });
    }

    private void initializePlayer1MinionList() {
        p1MinionList = new ListView<String>();
        p1Items = FXCollections.observableArrayList();

        p1MinionList.setItems(p1Items);
        shopMainGrid.add(p1MinionList, 0, 3);
    }

    private void initializePlayer2MinionList() {
        p2MinionList = new ListView<String>();
        p2Items = FXCollections.observableArrayList();

        p2MinionList.setItems(p2Items);
        shopMainGrid.add(p2MinionList, 1, 3);
    }

    private void reloadPlayer1MinionList() {
        p1MinionList.getItems().clear();
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
    }

    private void reloadPlayer2MinionList() {
        p2MinionList.getItems().clear();
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