package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Game.Game;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.PositionVector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private GridPane fieldGrid;
    private GridPane player1Grid;
    private GridPane player2Grid;

    private ListView<String> p1MinionList;
    private ObservableList<String> p1Items;

    private ListView<String> p2MinionList;
    private ObservableList<String> p2Items;

    private static final int FIELD_COLS = 8;
    private static final int FIELD_ROWS = 8;

    private TextField p1minionIdTF;
    private TextField p1fieldposXTF;
    private TextField p1fieldposYTF;
    private Button p1PlaceButton;
    private GridPane p1minionplacegrid;

    private TextField p2minionIdTF;
    private TextField p2fieldposXTF;
    private TextField p2fieldposYTF;
    private Button p2PlaceButton;
    private GridPane p2minionplacegrid;

    private TextField p1minionidField;
    private Button p1removeButton;
    private GridPane p1minionremovegrid;

    private TextField p2minionidField;
    private Button p2removeButton;
    private GridPane p2minionremovegrid;

    private Button battleButton;

    private Game game;

    @FXML
    private GridPane gameMainGrid;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*todo
         -remove minion: TextFiel (minionID) button: set boarder red (falls empty)
         button.onClick  -> get Id -> removeMinionFromBoard(int minionId)
         -> updateBoard -> getAllMinionIDS(heroID) iterate ->  getMinionPos(int minionId) if(no exc) -> draw minionType on grid   else -> do nothing

         -Health: healthbar -> getHeroHealth(int heroID) -> show as bar idk

         -Battle button: button.onClick -> doBattle()
           after battle -> animate battleLog -> getBattleLog() -> draw
           after battle -> doHeroDamage() -> display somehow hero health
           after battle -> distributeFunds() -> display same as health
           after battle -> clearBoard()
           after battle -> getWinner() if winner(1 or 2) -> game over(back to main menu)  else(-1) -> open shop
         */

        battleButton = new Button("Battle");

        initializeFieldGrid();
        initializePlayerGrid();
        initializeMinionList();
        initializePlaceMinionSection();
        initializeRemoveMinionSection();

        player1Grid.add(p1minionplacegrid,0,1);
        player1Grid.add(p1minionremovegrid,0,2);

        player2Grid.add(p2minionplacegrid,0,1);
        player2Grid.add(p2minionremovegrid,0,2);

        gameMainGrid.add(fieldGrid, 1, 0);
        gameMainGrid.add(player1Grid, 0, 0);
        gameMainGrid.add(player2Grid, 2, 0);
        gameMainGrid.add(battleButton,1,1);
    }

    private void initializeRemoveMinionSection() {
        p1minionidField = new TextField();
        p1removeButton = new Button("remove");

        p1minionremovegrid = new GridPane();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p1minionremovegrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(100);
            p1minionremovegrid.getRowConstraints().add(row);
        }

        p1minionremovegrid.add(p1minionidField,0,0);
        p1minionremovegrid.add(p1removeButton,1,0);

        p1minionidField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p1minionidField.getText().matches("\\d*")) {
                p1minionidField.setText(p1minionidField.getText().replaceAll("[^\\d]", ""));
            }
        });

        p1removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p1minionidField.getText());
                refreshGameGrid();

                try {
                    PositionVector posVec = game.getMinionPos(id);
                    int row = posVec.getY();
                    int col = posVec.getX();
                    ObservableList<Node> childrens = fieldGrid.getChildren();
                    for (Node node : childrens) {
                        try {
                            if (fieldGrid.getRowIndex(node) == row && fieldGrid.getColumnIndex(node) == col) {
                                System.out.println(node);
                                fieldGrid.getChildren().remove(node);
                            }
                        }catch (NullPointerException e){
                            System.out.println("yees");
                        }
                    }
                    game.removeMinionFromBoard(id);
                } catch (MinionNotOnBoardException e) {
                    e.printStackTrace();
                }
            }
            });

        p2minionidField = new TextField();
        p2removeButton = new Button("remove");

        p2minionremovegrid = new GridPane();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p2minionremovegrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(100);
            p2minionremovegrid.getRowConstraints().add(row);
        }

        p2minionremovegrid.add(p2minionidField,0,0);
        p2minionremovegrid.add(p2removeButton,1,0);

        p2minionidField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p2minionidField.getText().matches("\\d*")) {
                p2minionidField.setText(p2minionidField.getText().replaceAll("[^\\d]", ""));
            }
        });

        p2removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //   int id = Integer.parseInt(p1minionidField.getText());
                refreshGameGrid();

             /*   try {
                    game.removeMinionFromBoard(id);
                    //todo   fieldGrid.getChildren().remove();
                } catch (MinionNotOnBoardException e) {
                    e.printStackTrace();
                }*/
            }
        });
        }

    private void initializePlaceMinionSection() {
        p1minionIdTF = new TextField();
        p1fieldposXTF = new TextField();
        p1fieldposYTF = new TextField();
        p1PlaceButton = new Button("place");

        p1minionplacegrid = new GridPane();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p1minionplacegrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints(50);
            p1minionplacegrid.getRowConstraints().add(row);
        }

        p1minionplacegrid.add(p1fieldposXTF, 0, 0);
        p1minionplacegrid.add(p1fieldposYTF, 1, 0);
        p1minionplacegrid.add(p1minionIdTF, 0, 1);
        p1minionplacegrid.add(p1PlaceButton, 1, 1);

        p1minionIdTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p1minionIdTF.getText().matches("\\d*")) {
                p1minionIdTF.setText(p1minionIdTF.getText().replaceAll("[^\\d]", ""));
            }
        });

        p1fieldposXTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p1fieldposXTF.getText().matches("\\d*")) {
                p1fieldposXTF.setText(p1fieldposXTF.getText().replaceAll("[^\\d]", ""));
            }
        });

        p1fieldposYTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p1fieldposYTF.getText().matches("\\d*")) {
                p1fieldposYTF.setText(p1fieldposYTF.getText().replaceAll("[^\\d]", ""));
            }
        });

        p1PlaceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p1minionIdTF.getText());
                int x = Integer.parseInt(p1fieldposXTF.getText());
                int y = Integer.parseInt(p1fieldposYTF.getText());
                System.out.println("id: " + id + " x: " + x + " y: " + y);
                PositionVector posVector = new PositionVector();
                posVector.setX(x);
                posVector.setY(y);

                try {
                    game.placeMinionOnBoard(1, id, posVector);
                    fieldGrid.add(new Text("test"), posVector.getX(), posVector.getY());
                } catch (InvalidPositionException e) {
                    e.printStackTrace();
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                }
            }
        });

        p2minionIdTF = new TextField();
        p2fieldposXTF = new TextField();
        p2fieldposYTF = new TextField();
        p2PlaceButton = new Button("place");

        p2minionplacegrid = new GridPane();

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p2minionplacegrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints(50);
            p2minionplacegrid.getRowConstraints().add(row);
        }

        p2minionplacegrid.add(p2fieldposXTF, 0, 0);
        p2minionplacegrid.add(p2fieldposYTF, 1, 0);
        p2minionplacegrid.add(p2minionIdTF, 0, 1);
        p2minionplacegrid.add(p2PlaceButton, 1, 1);

        p2minionIdTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p2minionIdTF.getText().matches("\\d*")) {
                p2minionIdTF.setText(p2minionIdTF.getText().replaceAll("[^\\d]", ""));
            }
        });

        p2fieldposXTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p2fieldposXTF.getText().matches("\\d*")) {
                p2fieldposXTF.setText(p2fieldposXTF.getText().replaceAll("[^\\d]", ""));
            }
        });

        p2fieldposYTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p2fieldposYTF.getText().matches("\\d*")) {
                p2fieldposYTF.setText(p2fieldposYTF.getText().replaceAll("[^\\d]", ""));
            }
        });

        p2PlaceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p2minionIdTF.getText());
                int x = Integer.parseInt(p2fieldposXTF.getText());
                int y = Integer.parseInt(p2fieldposYTF.getText());
                System.out.println("id: " + id + " x: " + x + " y: " + y);
                PositionVector posVector = new PositionVector();
                posVector.setX(x);
                posVector.setY(y);

                try {
                    game.placeMinionOnBoard(2, id, posVector);
                    fieldGrid.add(new Text("test"), posVector.getX(), posVector.getY());
                } catch (InvalidPositionException e) {
                    e.printStackTrace();
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initializeFieldGrid() {
        fieldGrid = new GridPane();
        fieldGrid.setId("fieldGrid");
        fieldGrid.setGridLinesVisible(true);

        for (int i = 0; i < FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            fieldGrid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < FIELD_ROWS; i++) {
            RowConstraints row = new RowConstraints(50);
            fieldGrid.getRowConstraints().add(row);
        }
    }

    private void initializePlayerGrid() {
        //Player 1 Grid
        player1Grid = new GridPane();
        player1Grid.setGridLinesVisible(true);

        ColumnConstraints player1Column = new ColumnConstraints(150);
        player1Grid.getColumnConstraints().add(player1Column);

        RowConstraints player1MinionListRow = new RowConstraints(300);
        RowConstraints player1AddRow = new RowConstraints(100);
        RowConstraints player1RemoveRow = new RowConstraints(100);

        player1Grid.getRowConstraints().add(player1MinionListRow);
        player1Grid.getRowConstraints().add(player1AddRow);
        player1Grid.getRowConstraints().add(player1RemoveRow);

        //Player 2 Grid
        player2Grid = new GridPane();
        player2Grid.setGridLinesVisible(true);

        ColumnConstraints player2Column = new ColumnConstraints(150);
        player2Grid.getColumnConstraints().add(player2Column);

        RowConstraints player2MinionListRow = new RowConstraints(300);
        RowConstraints player2AddRow = new RowConstraints(100);
        RowConstraints player2RemoveRow = new RowConstraints(100);

        player2Grid.getRowConstraints().add(player2MinionListRow);
        player2Grid.getRowConstraints().add(player2AddRow);
        player2Grid.getRowConstraints().add(player2RemoveRow);
    }

    private void initializeMinionList() {
        p1MinionList = new ListView<String>();
        p1Items = FXCollections.observableArrayList ();

        p1MinionList.setItems(p1Items);
        player1Grid.add(p1MinionList,0,0);


        p2MinionList = new ListView<String>();
        p2Items =FXCollections.observableArrayList ();

        p2MinionList.setItems(p2Items);
        player2Grid.add(p2MinionList,0,0);

        try {
            for (int id : game.getAllMinionIds(1)) {
                String c = game.getMinionInfoAsString(1,id);
                p1MinionList.getItems().add(p1MinionList.getItems().size(), c);
            }
        } catch (
            IllegalGameStateException e) {
            e.printStackTrace();
        } catch (InvalidMinionIDException e) {
            e.printStackTrace();
        }

        try {
            for (int id : game.getAllMinionIds(2)) {
                String c = game.getMinionInfoAsString(2,id);
                p2MinionList.getItems().add(p2MinionList.getItems().size(), c);
            }
        } catch (
            IllegalGameStateException e) {
            e.printStackTrace();
        } catch (InvalidMinionIDException e) {
            e.printStackTrace();
        }
    }

    private void refreshGameGrid(){

    }
}