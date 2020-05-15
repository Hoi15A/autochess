package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.minion.strategy.CowardStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CowardStrategyTest {

    private MinionBase[][] board;
    @Mock private MinionBase cowardMinion;
    @Mock private MinionBase dummyMinion;
    private CowardStrategy strategy;

    private final static int HERO_ID_A = 0;
    private final static int HERO_ID_B = 1;
    private final static int COWARD_MINION_ATK_RANGE = 1;
    private final static int COWARD_MINION_MOV_RANGE = 2;
    private final static int COWARD_MINION_ID = 0;
    private final static int DUMMY_MINION_ID = 1;
    private final static int ROWS = 8;
    private final static int COLS = 8;
    private final static PositionVector TOP_LEFT = new PositionVector(0, 0);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        board = new MinionBase[ROWS][COLS];

        strategy = new CowardStrategy();
    }

    @Test
    void testNoTargetNoMove() {
        board[4][4] = cowardMinion;
        setUpCowardMinionMock();

        PositionVector resultPos = strategy.move(board, new PositionVector(4, 4), cowardMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargetedMove() {
        board[3][3] = cowardMinion;
        setUpCowardMinionMock();

        board[4][4] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector result = strategy.move(board, new PositionVector(3, 3), cowardMinion);
        assertEquals(new PositionVector(1, 1), result);
    }

    @Test
    void testNullMove() {
        assertThrows(NullPointerException.class, () -> {
            strategy.move(null, null, null);
        });
    }

    @Test
    void testAttackNoTarget() {
        board[0][0] = cowardMinion;
        setUpCowardMinionMock();

        PositionVector resultPos = strategy.attack(board, TOP_LEFT, cowardMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargetedAttack() {
        board[0][0] = cowardMinion;
        setUpCowardMinionMock();

        board[1][1] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector resultPos = strategy.attack(board, TOP_LEFT, cowardMinion);
        assertEquals(new PositionVector(1, 1), resultPos);

        board[1][0] = dummyMinion;
        resultPos = strategy.attack(board, TOP_LEFT, cowardMinion);
        assertEquals(new PositionVector(0, 1), resultPos);
    }

    @Test
    void testNullAttack() {
        assertThrows(NullPointerException.class, () -> {
            strategy.attack(null, null, null);
        });
    }


    private void setUpCowardMinionMock() {
        when(cowardMinion.getMovementRange()).thenReturn(COWARD_MINION_MOV_RANGE);
        when(cowardMinion.getAttackRange()).thenReturn(COWARD_MINION_ATK_RANGE);
        when(cowardMinion.getId()).thenReturn(COWARD_MINION_ID);
        when(cowardMinion.getHeroId()).thenReturn(HERO_ID_A);
    }

    private void setUpDummyMinionMock() {
        when(dummyMinion.getId()).thenReturn(DUMMY_MINION_ID);
        when(dummyMinion.getHeroId()).thenReturn(HERO_ID_B);
    }
}
