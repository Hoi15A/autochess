package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Board.BoardManager;
import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Board.exceptions.NoMinionFoundException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
    public void testSetMinionOnBoardValidPosition() throws InvalidPositionException {
        PositionVector positionVector = new PositionVector(0,0);
        MinionBase[][] board = boardManager.getBoardArray2d();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }

        boardManager.setMinionOnBoard(minionOne, positionVector);
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

        assertThrows(InvalidPositionException.class, () -> boardManager.setMinionOnBoard(minionOne, new PositionVector(15,5)));

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }
    }

    @Test
    public void testSetMinionOnBoardInvalidPositionOccupied() throws InvalidPositionException {
        PositionVector positionVector = new PositionVector(2,7);
        boardManager.setMinionOnBoard(minionOne, positionVector);
        assertThrows(InvalidPositionException.class, () -> boardManager.setMinionOnBoard(minionTwo, new PositionVector(15,5)));
    }

    @Test
    public void testRemoveMinionFromBoardValidId() throws InvalidPositionException, NoMinionFoundException {
        PositionVector positionVector = new PositionVector(2,7);
        MinionBase[][] board = boardManager.getBoardArray2d();
        assertEquals(null,board[7][2]);
        boardManager.setMinionOnBoard(minionOne, positionVector);
        assertEquals(minionOne,board[7][2]);

        boardManager.removeMinionFromBoard(0);
        assertEquals(null, board[7][2]);
    }

    @Test
    public void testRemoveMinionFromBoardInvalidId() {
        MinionBase[][] board = boardManager.getBoardArray2d();
        assertEquals(null,board[7][2]);

        assertThrows(NoMinionFoundException.class, () -> boardManager.removeMinionFromBoard(0));

    }

    @Test
    public void testGetMinionPositionValidId() throws InvalidPositionException, NoMinionFoundException {
        PositionVector positionVector = new PositionVector(2,7);
        MinionBase[][] board = boardManager.getBoardArray2d();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                assertEquals(null, board[i][j]);
            }
        }

        boardManager.setMinionOnBoard(minionOne, positionVector);
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

        assertThrows(NoMinionFoundException.class, () -> boardManager.getMinionPosition(0));
    }



}
