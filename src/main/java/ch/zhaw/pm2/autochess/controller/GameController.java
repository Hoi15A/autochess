package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.board.BattleLog;
import ch.zhaw.pm2.autochess.board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.game.Game;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidMinionIDException;
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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is the controller class of the GameWindow.
 * It initializes all the components.
 */
public class GameController implements Initializable {
    //data fields
    private Game game;

    private GridPane fieldSectionGridPane;

    private static final int FIELD_COLS = 8;
    private static final int FIELD_ROWS = 8;

    protected Button battleButton;

    private List<BattleLog> battleLogList;

    //Player 1 data fields (p1 = player1 h1 = hero 1)
    private GridPane player1SectionGridPane;
    private GridPane p1PlaceSectionGridPane;
    private GridPane p1RemoveSectionGridPane;

    private ListView<String> p1MinionList;
    private ObservableList<String> p1Items;

    private TextField p1PlaceMinionIdTextField;
    private TextField p1fieldPosXTextField;
    private TextField p1fieldPosYTextField;
    private TextField p1RemoveMinionIdTextField;

    private Button p1PlaceButton;
    private Button p1RemoveButton;
    private Button p1UseAbilityButton;


    //Player 2 data fields (p1 = player1 h1 = hero 1)
    private GridPane player2SectionGridPane;
    private GridPane p2PlaceSectionGridPane;
    private GridPane p2RemoveSectionGridPane;

    private ListView<String> p2MinionList;
    private ObservableList<String> p2Items;

    private TextField p2PlaceMinionIdTextField;
    private TextField p2fieldPosXTextField;
    private TextField p2fieldPosYTextField;
    private TextField p2RemoveMinionIdTextField;

    private Button p2PlaceButton;
    private Button p2RemoveButton;
    private Button p2UseAbilityButton;


    @FXML
    private GridPane gameMainGrid;

    public GameController(Game currentGame) {
        game = currentGame;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeFieldGrid();
        initializePlayer1Grid();
        initializePlayer2Grid();
        initializePlayer1MinionList();
        initializePlayer2MinionList();
        initializePlayer1PlaceMinionSection();
        initializePlayer2PlaceMinionSection();
        initializePlayer1RemoveMinionSection();
        initializePlayer2RemoveMinionSection();
        initializeBattleAndAbilityButtons();
        addComponentsToMainGrid();
    }

    private void addComponentsToMainGrid() {
        player1SectionGridPane.add(p1PlaceSectionGridPane, 0, 1);
        player1SectionGridPane.add(p1RemoveSectionGridPane, 0, 2);

        player2SectionGridPane.add(p2PlaceSectionGridPane, 0, 1);
        player2SectionGridPane.add(p2RemoveSectionGridPane, 0, 2);

        gameMainGrid.add(fieldSectionGridPane, 1, 0);
        gameMainGrid.add(player1SectionGridPane, 0, 0);
        gameMainGrid.add(player2SectionGridPane, 2, 0);

        gameMainGrid.add(battleButton, 1, 1);
        gameMainGrid.add(p1UseAbilityButton, 0, 1);
        gameMainGrid.add(p2UseAbilityButton, 2, 1);
    }

    private void initializeFieldGrid() {
        fieldSectionGridPane = new GridPane();
        fieldSectionGridPane.setGridLinesVisible(true);

        for (int i = 0; i < FIELD_COLS; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            fieldSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < FIELD_ROWS; i++) {
            RowConstraints row = new RowConstraints(50);
            fieldSectionGridPane.getRowConstraints().add(row);
        }
    }

    private void initializePlayer1Grid() {
        //Player 1 Grid
        player1SectionGridPane = new GridPane();
        player1SectionGridPane.setGridLinesVisible(true);

        ColumnConstraints player1Column = new ColumnConstraints(150);
        player1SectionGridPane.getColumnConstraints().add(player1Column);

        RowConstraints player1MinionListRow = new RowConstraints(300);
        RowConstraints player1AddRow = new RowConstraints(100);
        RowConstraints player1RemoveRow = new RowConstraints(100);

        player1SectionGridPane.getRowConstraints().add(player1MinionListRow);
        player1SectionGridPane.getRowConstraints().add(player1AddRow);
        player1SectionGridPane.getRowConstraints().add(player1RemoveRow);
    }

    private void initializePlayer2Grid() {
        //Player 2 Grid
        player2SectionGridPane = new GridPane();
        player2SectionGridPane.setGridLinesVisible(true);

        ColumnConstraints player2Column = new ColumnConstraints(150);
        player2SectionGridPane.getColumnConstraints().add(player2Column);

        RowConstraints player2MinionListRow = new RowConstraints(300);
        RowConstraints player2AddRow = new RowConstraints(100);
        RowConstraints player2RemoveRow = new RowConstraints(100);

        player2SectionGridPane.getRowConstraints().add(player2MinionListRow);
        player2SectionGridPane.getRowConstraints().add(player2AddRow);
        player2SectionGridPane.getRowConstraints().add(player2RemoveRow);
    }

    private void initializePlayer1MinionList() {
        p1MinionList = new ListView<String>();
        p1Items = FXCollections.observableArrayList();

        p1MinionList.setItems(p1Items);
        player1SectionGridPane.add(p1MinionList, 0, 0);

        try {
            for (int id : game.getAllMinionIds(1)) {
                String c = game.getMinionInfoAsString(1, id);
                p1MinionList.getItems().add(p1MinionList.getItems().size(), c);
            }
        } catch (
            IllegalGameStateException e) {
            e.printStackTrace();
        } catch (InvalidMinionIDException e) {
            e.printStackTrace();
        }
    }

    private void initializePlayer2MinionList() {
        p2MinionList = new ListView<String>();
        p2Items = FXCollections.observableArrayList();

        p2MinionList.setItems(p2Items);
        player2SectionGridPane.add(p2MinionList, 0, 0);

        try {
            for (int id : game.getAllMinionIds(2)) {
                String c = game.getMinionInfoAsString(2, id);
                p2MinionList.getItems().add(p2MinionList.getItems().size(), c);
            }
        } catch (
            IllegalGameStateException e) {
            e.printStackTrace();
        } catch (InvalidMinionIDException e) {
            e.printStackTrace();
        }
    }

    private void initializePlayer1PlaceMinionSection() {
        p1PlaceMinionIdTextField = new TextField();
        p1fieldPosXTextField = new TextField();
        p1fieldPosYTextField = new TextField();
        p1PlaceButton = new Button("Place");

        p1PlaceSectionGridPane = new GridPane();

        p1fieldPosXTextField.setPromptText("X-Axis");
        p1fieldPosYTextField.setPromptText("Y-Axis");
        p1PlaceMinionIdTextField.setPromptText("MinionID");

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p1PlaceSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints(50);
            p1PlaceSectionGridPane.getRowConstraints().add(row);
        }

        p1PlaceSectionGridPane.add(p1fieldPosXTextField, 0, 0);
        p1PlaceSectionGridPane.add(p1fieldPosYTextField, 1, 0);
        p1PlaceSectionGridPane.add(p1PlaceMinionIdTextField, 0, 1);
        p1PlaceSectionGridPane.add(p1PlaceButton, 1, 1);

        observeTextField(p1fieldPosXTextField);
        observeTextField(p1fieldPosYTextField);
        observeTextField(p1PlaceMinionIdTextField);

        p1PlaceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p1PlaceMinionIdTextField.getText());
                int x = Integer.parseInt(p1fieldPosXTextField.getText());
                int y = Integer.parseInt(p1fieldPosYTextField.getText());
                PositionVector posVector = new PositionVector();
                posVector.setX(x);
                posVector.setY(y);

                try {
                    game.placeMinionOnBoard(1, id, posVector);
                    fieldSectionGridPane.add(new Text(game.getMinionType(1, id).toString()), posVector.getX(), posVector.getY());
                } catch (InvalidPositionException e) {
                    e.printStackTrace();
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                }
                p1fieldPosXTextField.setText("");
                p1fieldPosYTextField.setText("");
                p1PlaceMinionIdTextField.setText("");
            }
        });
    }

    private void initializePlayer2PlaceMinionSection() {
        p2PlaceMinionIdTextField = new TextField();
        p2fieldPosXTextField = new TextField();
        p2fieldPosYTextField = new TextField();
        p2PlaceButton = new Button("Place");

        p2PlaceSectionGridPane = new GridPane();

        p2fieldPosXTextField.setPromptText("X-Axis");
        p2fieldPosYTextField.setPromptText("Y-Axis");
        p2PlaceMinionIdTextField.setPromptText("MinionID");

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p2PlaceSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints(50);
            p2PlaceSectionGridPane.getRowConstraints().add(row);
        }

        p2PlaceSectionGridPane.add(p2fieldPosXTextField, 0, 0);
        p2PlaceSectionGridPane.add(p2fieldPosYTextField, 1, 0);
        p2PlaceSectionGridPane.add(p2PlaceMinionIdTextField, 0, 1);
        p2PlaceSectionGridPane.add(p2PlaceButton, 1, 1);

        observeTextField(p2fieldPosXTextField);
        observeTextField(p2fieldPosYTextField);
        observeTextField(p2PlaceMinionIdTextField);

        p2PlaceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p2PlaceMinionIdTextField.getText());
                int x = Integer.parseInt(p2fieldPosXTextField.getText());
                int y = Integer.parseInt(p2fieldPosYTextField.getText());
                PositionVector posVector = new PositionVector();
                posVector.setX(x);
                posVector.setY(y);

                try {
                    game.placeMinionOnBoard(2, id, posVector);
                    fieldSectionGridPane.add(new Text(game.getMinionType(2, id).toString()), posVector.getX(), posVector.getY());
                } catch (InvalidPositionException e) {
                    e.printStackTrace();
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                }
                p2fieldPosXTextField.setText("");
                p2fieldPosYTextField.setText("");
                p2PlaceMinionIdTextField.setText("");
            }
        });
    }

    private void initializePlayer1RemoveMinionSection() {
        p1RemoveMinionIdTextField = new TextField();
        p1RemoveButton = new Button("remove");
        p1RemoveSectionGridPane = new GridPane();

        p1RemoveMinionIdTextField.setPromptText("MinionID");

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p1RemoveSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(100);
            p1RemoveSectionGridPane.getRowConstraints().add(row);
        }

        p1RemoveSectionGridPane.add(p1RemoveMinionIdTextField, 0, 0);
        p1RemoveSectionGridPane.add(p1RemoveButton, 1, 0);

        observeTextField(p1RemoveMinionIdTextField);

        p1RemoveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p1RemoveMinionIdTextField.getText());
                try {
                    removeMinionFromGrid(game.getMinionPos(id));
                } catch (MinionNotOnBoardException e) {
                    e.printStackTrace();
                }
                try {
                    game.removeMinionFromBoard(id);
                } catch (MinionNotOnBoardException e) {
                    e.printStackTrace();
                }
                p1RemoveMinionIdTextField.setText("");
            }
        });
    }

    private void initializePlayer2RemoveMinionSection() {
        p2RemoveMinionIdTextField = new TextField();
        p2RemoveButton = new Button("remove");
        p2RemoveSectionGridPane = new GridPane();

        p2RemoveMinionIdTextField.setPromptText("MinionID");

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(75);
            p2RemoveSectionGridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints(100);
            p2RemoveSectionGridPane.getRowConstraints().add(row);
        }

        p2RemoveSectionGridPane.add(p2RemoveMinionIdTextField, 0, 0);
        p2RemoveSectionGridPane.add(p2RemoveButton, 1, 0);

        observeTextField(p2RemoveMinionIdTextField);

        p2RemoveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int id = Integer.parseInt(p2RemoveMinionIdTextField.getText());
                try {
                    removeMinionFromGrid(game.getMinionPos(id));
                } catch (MinionNotOnBoardException e) {
                    e.printStackTrace();
                }
                try {
                    game.removeMinionFromBoard(id);
                } catch (MinionNotOnBoardException e) {
                    e.printStackTrace();
                }
                p2RemoveMinionIdTextField.setText("");
            }
        });
    }

    protected void doBattle() {
        try {
            game.doBattle();
            battleLogList = game.getBattleLog();

            Iterator<BattleLog> battleLogListIterator = battleLogList.iterator();

            while (battleLogListIterator.hasNext()) {
                BattleLog battleLog = battleLogListIterator.next();

                switch (battleLog.getType()) {
                    case ATTACK:
                        doAttack(battleLog.getActorId(), battleLog.getActorPos(), battleLog.getDefenderId(), battleLog.getDefenderPos(), battleLog.getDamageDealt());
                        break;
                    case MOVE:
                        doMove(battleLog.getActorId(), battleLog.getActorPos(), battleLog.getNewPos(), battleLog.getActorType());
                        break;
                    case DEATH:
                        doDeath(battleLog.getActorPos());
                        break;
                    default:
                        break;
                }
            }
        } catch (IllegalGameStateException e) {
            e.printStackTrace();
        }
    }

    private void doAttack(int actorId, PositionVector actorPos, int defenderId, PositionVector defenderPos, int damage) {
        //Animation attack eg. field red
    }

    private void doMove(int actorId, PositionVector actorPos, PositionVector actorNewPos, Config.MinionType minionType) {
        removeMinionFromGrid(actorPos);
        fieldSectionGridPane.add(new Text(minionType.toString()), actorNewPos.getX(), actorNewPos.getY());
    }

    private void doDeath(PositionVector deadMinionPos) {
        removeMinionFromGrid(deadMinionPos);
    }

    private void observeTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (textField.getText().matches("\\d*")) {
            } else {
                textField.setText(textField.getText().replaceAll("[^\\d]", ""));
            }
        });
    }

    private void removeMinionFromGrid(PositionVector minionPos) {
        int row = minionPos.getY();
        int col = minionPos.getX();
        ObservableList<Node> childrens = fieldSectionGridPane.getChildren();
        try {
            for (Node node : childrens) {
                if(fieldSectionGridPane.getRowIndex(node) == null && fieldSectionGridPane.getRowIndex(node) == null) {
                }else {
                    if (fieldSectionGridPane.getRowIndex(node) == row && fieldSectionGridPane.getColumnIndex(node) == col) {
                        fieldSectionGridPane.getChildren().remove(node);
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
        }
    }

    private void initializeBattleAndAbilityButtons() {
        battleButton = new Button("Battle");
        p1UseAbilityButton = new Button("Use Ability");
        p2UseAbilityButton = new Button("Use Ability");

        p1UseAbilityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.doHeroAbility(1);
                    p1UseAbilityButton.setDisable(true);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                }
            }
        });

        p2UseAbilityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    game.doHeroAbility(2);
                    p2UseAbilityButton.setDisable(true);
                } catch (IllegalGameStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}