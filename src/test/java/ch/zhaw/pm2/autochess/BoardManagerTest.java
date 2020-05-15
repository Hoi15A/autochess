package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.board.BoardManager;
import ch.zhaw.pm2.autochess.board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.minion.MinionBase;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoardManagerTest {

    private BoardManager boardManager;
    private MinionBase[][] board;
    private static final PositionVector POS_1 = new PositionVector(1,1);
    private static final PositionVector POS_2 = new PositionVector(2,2);
    private static final PositionVector POS_3 = new PositionVector(3,3);
    private static final PositionVector POS_4 = new PositionVector(4,4);
    private static final PositionVector INVALID_POS = new PositionVector(Config.BOARD_HEIGHT +1, 0);
    private static final int MINION_ID_1 = 5;
    private static final int MINION_ID_2 = 7;
    private static final int HERO_ID_1 = 1;
    private static final int HERO_ID_2 = 2;
    private static final int INVALID_HERO_ID = 5;

    @Mock private MinionBase minionOne;
    @Mock private MinionBase minionTwo;

    @BeforeEach
    public void setUp() {
        boardManager = new BoardManager();
        board = boardManager.getBoardArray2d();
        MockitoAnnotations.initMocks(this);
    }

    private void placeTwoMinions() {
        board[POS_1.getY()][POS_1.getX()] = minionOne;
        board[POS_2.getY()][POS_2.getX()] = minionTwo;
    }

    private void checkBoardClear() {
        boardManager.clearBoard();
        for (MinionBase[] minionBases : board) {
            for (int j = 0; j < board.length; j++) {
                assertNull(minionBases[j]);
            }
        }
    }

    @Test
    public void testClearBoard() {
        placeTwoMinions();
        boardManager.clearBoard();
        checkBoardClear();
    }

    @Test
    public void testPlaceMinionOnBoardValidPosition() throws InvalidPositionException, IllegalGameStateException {
        checkBoardClear();
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        boardManager.placeMinionOnBoard(minionOne, POS_1);
        assertEquals(minionOne, board[POS_1.getY()][POS_1.getX()]);

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(i == POS_1.getY() && j ==POS_1.getX()) {
                    assertEquals(minionOne, board[i][j]);
                }else {
                    assertNull(board[i][j]);
                }
            }
        }
    }

    @Test
    public void testPlaceMinionOnBoardInvalidPositionOffBoard() {
        checkBoardClear();
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        assertThrows(InvalidPositionException.class, () -> boardManager.placeMinionOnBoard(minionOne, INVALID_POS));
        checkBoardClear();
    }

    @Test
    public void testPlaceMinionOnBoardInvalidPositionOccupied() throws InvalidPositionException, IllegalGameStateException {
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        when(minionTwo.getHeroId()).thenReturn(HERO_ID_2);
        boardManager.placeMinionOnBoard(minionOne, POS_1);
        assertThrows(InvalidPositionException.class, () -> boardManager.placeMinionOnBoard(minionTwo, POS_1));
    }

    @Test
    public void testPlaceMinionOnBoardInvalidPositionAlreadyPlaced() throws InvalidPositionException, IllegalGameStateException {
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        boardManager.placeMinionOnBoard(minionOne, POS_1);
        assertThrows(InvalidPositionException.class, () -> boardManager.placeMinionOnBoard(minionOne, POS_1));
    }

    @Test
    public void testPlaceMinionOnBoardNullMinion() {
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        assertThrows(NullPointerException.class, () -> boardManager.placeMinionOnBoard(null, POS_1));
    }

    @Test
    public void testPlaceMinionInvalidHeroId() {
        when(minionOne.getHeroId()).thenReturn(INVALID_HERO_ID);
        assertThrows(IllegalGameStateException.class, () -> boardManager.placeMinionOnBoard(minionOne, POS_1));
    }

    @Test
    public void testPlaceMinionOnBoardNullPos() {
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        assertThrows(InvalidPositionException.class, () -> boardManager.placeMinionOnBoard(minionOne,null));
    }

    @Test
    public void testRemoveMinionFromBoardValidId() throws MinionNotOnBoardException {
        checkBoardClear();
        placeTwoMinions();

        assertEquals(minionOne, board[POS_1.getY()][POS_1.getX()]);
        assertEquals(minionTwo, board[POS_2.getY()][POS_2.getX()]);

        when(minionOne.getId()).thenReturn(MINION_ID_1);
        boardManager.removeMinionFromBoard(MINION_ID_1);
        assertNull(board[POS_1.getY()][POS_1.getX()]);
        assertEquals(minionTwo, board[POS_2.getY()][POS_2.getX()]);

        when(minionTwo.getId()).thenReturn(MINION_ID_2);
        boardManager.removeMinionFromBoard(MINION_ID_2);
        checkBoardClear();
    }

    @Test
    public void testRemoveMinionFromBoardInvalidId() {
        checkBoardClear();
        assertThrows(MinionNotOnBoardException.class, () -> boardManager.removeMinionFromBoard(MINION_ID_1));
    }

    @Test
    public void testGetMinionPositionValidId() throws MinionNotOnBoardException {
        checkBoardClear();
        placeTwoMinions();
        when(minionOne.getId()).thenReturn(MINION_ID_1);
        assertEquals(POS_1, boardManager.getMinionPosition(MINION_ID_1));
    }


    @Test
    public void testGetMinionPositionInvalidId() {
        checkBoardClear();
        assertThrows(MinionNotOnBoardException.class, () -> boardManager.getMinionPosition(MINION_ID_1));
    }

    @Test
    public void testGetAllMinionsOnBoard() {
        checkBoardClear();
        placeTwoMinions();

        ArrayList<MinionBase> referenceList = new ArrayList<>();
        referenceList.add(minionOne);
        referenceList.add(minionTwo);
        ArrayList<MinionBase> boardManagerList = boardManager.getAllMinionsOnBoard();

        for(int i = 0; i < referenceList.size(); i++ ) {
            assertEquals(referenceList.get(i), boardManagerList.get(i));
        }
    }

    @Test
    public void testGetSpecMinionsFromBoard() {
        checkBoardClear();
        placeTwoMinions();

        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        when(minionTwo.getHeroId()).thenReturn(HERO_ID_2);
        ArrayList<MinionBase> referenceList = new ArrayList<>();
        referenceList.add(minionOne);
        ArrayList<MinionBase> boardManagerList = boardManager.getSpecHeroMinionsFromBoard(HERO_ID_1);

        for(int i = 0; i < referenceList.size(); i++ ) {
            assertEquals(referenceList.get(i), boardManagerList.get(i));
        }
    }

    @Test
    public void doBattleValidMove() throws InvalidPositionException, MinionNotOnBoardException {
        checkBoardClear();
        placeTwoMinions();
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        when(minionTwo.getHeroId()).thenReturn(HERO_ID_2);
        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionTwo.getId()).thenReturn(MINION_ID_2);
        when(minionOne.move(board, POS_1)).thenReturn(POS_3);
        when(minionTwo.move(board, POS_2)).thenReturn(POS_4);
        when(minionOne.attack(board, POS_1)).thenReturn(null);
        when(minionTwo.attack(board, POS_2)).thenReturn(null);

        boardManager.doBattle();

        assertEquals(POS_1, boardManager.getMinionPosition(MINION_ID_1));
        assertEquals(POS_2, boardManager.getMinionPosition(MINION_ID_2));
    }

    @Test
    public void doBattleNoMoveNoAttack() throws InvalidPositionException, MinionNotOnBoardException {
        checkBoardClear();
        placeTwoMinions();
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        when(minionTwo.getHeroId()).thenReturn(HERO_ID_2);
        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionTwo.getId()).thenReturn(MINION_ID_2);
        when(minionOne.move(board, POS_1)).thenReturn(null);
        when(minionTwo.move(board, POS_2)).thenReturn(null);
        when(minionOne.attack(board, POS_1)).thenReturn(null);
        when(minionTwo.attack(board, POS_2)).thenReturn(null);

        boardManager.doBattle();

        assertEquals(POS_1, boardManager.getMinionPosition(MINION_ID_1));
        assertEquals(POS_2, boardManager.getMinionPosition(MINION_ID_2));

        verify(minionOne, times(0)).getAttack();
        verify(minionTwo, times(0)).getAttack();
    }

    @Test
    public void doBattleValidAttack() throws InvalidPositionException, MinionNotOnBoardException {
        checkBoardClear();
        placeTwoMinions();
        when(minionOne.getHeroId()).thenReturn(HERO_ID_1);
        when(minionTwo.getHeroId()).thenReturn(HERO_ID_2);
        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionTwo.getId()).thenReturn(MINION_ID_2);
        when(minionOne.getAttack()).thenReturn(10);
        when(minionTwo.getHealth()).thenReturn(1);
        when(minionOne.getHealth()).thenReturn(10);
        when(minionTwo.getDefense()).thenReturn(1);
        when(minionOne.move(board, POS_1)).thenReturn(null);
        when(minionTwo.move(board, POS_2)).thenReturn(null);
        when(minionOne.attack(board, POS_1)).thenReturn(POS_2);
        when(minionTwo.attack(board, POS_2)).thenReturn(null);

        boardManager.doBattle();

        verify(minionOne, times(Config.MAX_BATTLE_LOOPS)).getAttack();
        verify(minionTwo, times(Config.MAX_BATTLE_LOOPS)).getDefense();
    }

}
