package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.strategy.DefensiveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DefensiveStrategyTest {

    private MinionBase[][] board;
    @Mock private MinionBase defMinion;
    @Mock private MinionBase dummyMinion;
    private DefensiveStrategy strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        board = new MinionBase[8][8];

        strategy = new DefensiveStrategy();
    }

    @Test
    void testNoTargetNoMove() {
        board[0][0] = defMinion;

        setUpDefensiveMinionMock();

        PositionVector resultPos = strategy.move(board, new PositionVector(0, 0), defMinion);

        assertNull(resultPos);
    }

    @Test
    void testTargettedMove() {
        board[0][0] = defMinion;
        setUpDefensiveMinionMock();

        board[7][7] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector result = strategy.move(board, new PositionVector(0, 0), defMinion);
        assertNull(result);

        board[7][7] = null;
        board[1][1] = dummyMinion;

        result = strategy.move(board, new PositionVector(0, 0), defMinion);
        assertEquals(new PositionVector(1, 0), result);
    }

    @Test
    void testNullMove() {
        assertThrows(NullPointerException.class, () -> {
            strategy.move(null, null, null);
        });
    }

    @Test
    void testAttackNoTarget() {
        board[0][0] = defMinion;
        setUpDefensiveMinionMock();

        PositionVector resultPos = strategy.attack(board, new PositionVector(0, 0), defMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargetedAttack() {
        board[0][0] = defMinion;
        setUpDefensiveMinionMock();

        board[1][1] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector resultPos = strategy.attack(board, new PositionVector(0, 0), defMinion);
        assertEquals(new PositionVector(1, 1), resultPos);

        board[1][0] = dummyMinion;
        resultPos = strategy.attack(board, new PositionVector(0, 0), defMinion);
        assertEquals(new PositionVector(0, 1), resultPos);
    }

    @Test
    void testNullAttack() {
        assertThrows(NullPointerException.class, () -> {
            PositionVector result = strategy.attack(null, null, null);
        });
    }


    private void setUpDefensiveMinionMock() {
        when(defMinion.getMovementRange()).thenReturn(1);
        when(defMinion.getAttackRange()).thenReturn(1);
        when(defMinion.getId()).thenReturn(0);
        when(defMinion.getHeroId()).thenReturn(0);
    }

    private void setUpDummyMinionMock() {
        when(dummyMinion.getId()).thenReturn(2);
        when(dummyMinion.getHeroId()).thenReturn(1);
    }
}
