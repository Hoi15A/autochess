package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Board.BoardManager;
import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardManagerTest {
    private BoardManager boardManager;
    @Mock private MinionBase minionOne;
    @Mock private MinionBase minionTwo;

    @BeforeEach
    void setUp() {
        boardManager = new BoardManager();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetMinionOnBoardValidPosition() throws InvalidPositionException, IllegalGameStateException {
        PositionVector positionVector = new PositionVector(0,0);
        MinionBase[][] board = boardManager.getBoardArray2d();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }

        boardManager.placeMinionOnBoard(minionOne, positionVector);
        assertEquals(minionOne, board[0][0]);

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(i > 0 & j > 0) {
                    assertEquals(null, board[i][j]);
                }
            }
        }
    }

    @Test
    public void testSetMinionOnBoardInvalidPositionOffBoard() {
        PositionVector positionVector = new PositionVector(2,7);
        MinionBase[][] board = boardManager.getBoardArray2d();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }

        assertThrows(InvalidPositionException.class, () -> boardManager.placeMinionOnBoard(minionOne, new PositionVector(15,5)));

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }
    }

    @Test
    public void testSetMinionOnBoardInvalidPositionOccupied() throws InvalidPositionException, IllegalGameStateException {
        PositionVector positionVector = new PositionVector(2,7);
        boardManager.placeMinionOnBoard(minionOne, positionVector);
        assertThrows(InvalidPositionException.class, () -> boardManager.placeMinionOnBoard(minionTwo, new PositionVector(15,5)));
    }

    @Test
    public void testRemoveMinionFromBoardValidId() throws InvalidPositionException, MinionNotOnBoardException, IllegalGameStateException {
        PositionVector positionVector = new PositionVector(2,7);
        MinionBase[][] board = boardManager.getBoardArray2d();
        assertEquals(null,board[7][2]);
        boardManager.placeMinionOnBoard(minionOne, positionVector);
        assertEquals(minionOne,board[7][2]);

        boardManager.removeMinionFromBoard(0);
        assertEquals(null, board[7][2]);
    }

    @Test
    public void testRemoveMinionFromBoardInvalidId() {
        MinionBase[][] board = boardManager.getBoardArray2d();
        assertEquals(null,board[7][2]);

        assertThrows(MinionNotOnBoardException.class, () -> boardManager.removeMinionFromBoard(0));

    }

    @Test
    public void testGetMinionPositionValidId() throws InvalidPositionException, MinionNotOnBoardException, IllegalGameStateException {
        PositionVector positionVector = new PositionVector(2,7);
        MinionBase[][] board = boardManager.getBoardArray2d();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }

        boardManager.placeMinionOnBoard(minionOne, positionVector);
        assertEquals(minionOne, board[7][2]);
        assertEquals(positionVector, boardManager.getMinionPosition(0));
    }


    @Test
    public void testGetMinionPositionInvalidId() {
        MinionBase[][] board = boardManager.getBoardArray2d();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }

        assertThrows(MinionNotOnBoardException.class, () -> boardManager.getMinionPosition(0));
    }



}
