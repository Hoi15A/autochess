package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.minion.strategy.AggressiveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AggressiveStrategyTest {

    private MinionBase[][] board;
    @Mock private MinionBase aggroMinion;
    @Mock private MinionBase dummyMinion;
    private AggressiveStrategy strategy;

    private final static int HERO_ID_A = 0;
    private final static int HERO_ID_B = 1;
    private final static int AGGRO_MINION_ATK_RANGE = 1;
    private final static int AGGRO_MINION_MOV_RANGE = 1;
    private final static int AGGRO_MINION_ID = 0;
    private final static int DUMMY_MINION_ID = 1;
    private final static int ROWS = 8;
    private final static int COLS = 8;
    private final static PositionVector TOP_LEFT = new PositionVector(0, 0);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        board = new MinionBase[ROWS][COLS];

        strategy = new AggressiveStrategy();
    }

    @Test
    void testNoTargetNoMove() {
        board[0][0] = aggroMinion;
        setUpAggroMinionMock();

        PositionVector resultPos = strategy.move(board, TOP_LEFT, aggroMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargettedMove() {
        board[0][0] = aggroMinion;
        setUpAggroMinionMock();

        board[7][7] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector result = strategy.move(board, TOP_LEFT, aggroMinion);
        assertEquals(new PositionVector(1, 1), result);

        board[0][0] = null;
        board[1][1] = aggroMinion;

        result = strategy.move(board, new PositionVector(1, 1), aggroMinion);
        assertEquals(new PositionVector(2, 2), result);
    }

    @Test
    void testNullMove() {
        assertThrows(NullPointerException.class, () -> {
            PositionVector result = strategy.move(null, null, null);
        });
    }

    @Test
    void testAttackNoTarget() {
        board[0][0] = aggroMinion;
        setUpAggroMinionMock();

        PositionVector resultPos = strategy.attack(board, TOP_LEFT, aggroMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargettedAttack() {
        board[0][0] = aggroMinion;
        setUpAggroMinionMock();

        board[1][1] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector resultPos = strategy.attack(board, TOP_LEFT, aggroMinion);
        assertEquals(new PositionVector(1, 1), resultPos);

        board[1][0] = dummyMinion;
        resultPos = strategy.attack(board, TOP_LEFT, aggroMinion);
        assertEquals(new PositionVector(0, 1), resultPos);
    }

    @Test
    void testNullAttack() {
        assertThrows(NullPointerException.class, () -> {
            strategy.attack(null, null, null);
        });
    }


    private void setUpAggroMinionMock() {
        when(aggroMinion.getMovementRange()).thenReturn(AGGRO_MINION_MOV_RANGE);
        when(aggroMinion.getAttackRange()).thenReturn(AGGRO_MINION_ATK_RANGE);
        when(aggroMinion.getId()).thenReturn(AGGRO_MINION_ID);
        when(aggroMinion.getHeroId()).thenReturn(HERO_ID_A);
    }

    private void setUpDummyMinionMock() {
        when(dummyMinion.getId()).thenReturn(DUMMY_MINION_ID);
        when(dummyMinion.getHeroId()).thenReturn(HERO_ID_B);
    }
}
