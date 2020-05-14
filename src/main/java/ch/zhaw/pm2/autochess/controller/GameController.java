package ch.zhaw.pm2.autochess.controller;

import ch.zhaw.pm2.autochess.Game.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private GridPane fieldGrid;
    private GridPane player1Grid;
    private GridPane player2Grid;

    private static final int FIELD_COLS = 8;
    private static final int FIELD_ROWS = 8;

    private Game game;

    @FXML
    private GridPane gameMainGrid;

    public GameController(Game game) {
        this.game = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*todo
        -Minion list display -> liste alles minions in besitz anzeigen -> getAllMinionIds(int heroId) -> getMinionInfoAsString(int heroId, int minionId)
             iterieren und anzeigen
        -place minion: TextFiel (minionID) TextField (fieldposX) TextField (fieldposY) Button
           button.onClick  -> get values x&Y (check not empty) -> set boarder red (falss empty)
           x&Y -> positionVector
           -> placeMinionOnBoard(int heroId, int minionId, PositionVector pos)
           -> updateBoard -> getAllMinionIDS(heroID) iterate ->  getMinionPos(int minionId) if(no exc) -> draw minionType on grid   else -> do nothing

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

        initializeFieldGrid();
        initializePlayerGrid();

        gameMainGrid.add(fieldGrid,1,0);
        gameMainGrid.add(player1Grid,0,0);
        gameMainGrid.add(player2Grid,2,0);
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

    private void initializePlayerGrid(){
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
}
