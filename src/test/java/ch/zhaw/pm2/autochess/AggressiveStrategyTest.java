package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.strategy.AggressiveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AggressiveStrategyTest {

    private MinionBase[][] board;
    @Mock private MinionBase aggroMinion;
    @Mock private MinionBase dummyMinion;
    private AggressiveStrategy strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        board = new MinionBase[8][8];

        strategy = new AggressiveStrategy();
    }

    // TODO: finish tests and document while at it
    @Test
    void testNoTargetNoMove() {
        int row = 0;
        int col = 0;

        board[row][col] = aggroMinion;

        setUpAggroMinionMock();

        PositionVector resultPos = strategy.move(board, new PositionVector(col, row), aggroMinion);

        assertNull(resultPos);
    }

    @Test
    void testTargettedMove() {
        board[0][0] = aggroMinion;
        setUpAggroMinionMock();

        board[7][7] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector result = strategy.move(board, new PositionVector(0, 0), aggroMinion);
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

        PositionVector resultPos = strategy.attack(board, new PositionVector(0, 0), aggroMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargettedAttack() {
        board[0][0] = aggroMinion;
        setUpAggroMinionMock();

        board[1][1] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector resultPos = strategy.attack(board, new PositionVector(0, 0), aggroMinion);
        assertEquals(new PositionVector(1, 1), resultPos);

        board[1][0] = dummyMinion;
        resultPos = strategy.attack(board, new PositionVector(0, 0), aggroMinion);
        assertEquals(new PositionVector(0, 1), resultPos);
    }

    @Test
    void testNullAttack() {
        assertThrows(NullPointerException.class, () -> {
            PositionVector result = strategy.attack(null, null, null);
        });
    }


    private void setUpAggroMinionMock() {
        when(aggroMinion.getMovementRange()).thenReturn(1);
        when(aggroMinion.getAttackRange()).thenReturn(1);
        when(aggroMinion.getId()).thenReturn(0);
        when(aggroMinion.getHeroId()).thenReturn(0);
    }

    private void setUpDummyMinionMock() {
        when(dummyMinion.getId()).thenReturn(2);
        when(dummyMinion.getHeroId()).thenReturn(1);
    }
}
