package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.strategy.CowardStrategy;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        board = new MinionBase[8][8];

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

        PositionVector resultPos = strategy.attack(board, new PositionVector(0, 0), cowardMinion);
        assertNull(resultPos);
    }

    @Test
    void testTargetedAttack() {
        board[0][0] = cowardMinion;
        setUpCowardMinionMock();

        board[1][1] = dummyMinion;
        setUpDummyMinionMock();

        PositionVector resultPos = strategy.attack(board, new PositionVector(0, 0), cowardMinion);
        assertEquals(new PositionVector(1, 1), resultPos);

        board[1][0] = dummyMinion;
        resultPos = strategy.attack(board, new PositionVector(0, 0), cowardMinion);
        assertEquals(new PositionVector(0, 1), resultPos);
    }

    @Test
    void testNullAttack() {
        assertThrows(NullPointerException.class, () -> {
            strategy.attack(null, null, null);
        });
    }


    private void setUpCowardMinionMock() {
        when(cowardMinion.getMovementRange()).thenReturn(2);
        when(cowardMinion.getAttackRange()).thenReturn(1);
        when(cowardMinion.getId()).thenReturn(0);
        when(cowardMinion.getHeroId()).thenReturn(0);
    }

    private void setUpDummyMinionMock() {
        when(dummyMinion.getId()).thenReturn(2);
        when(dummyMinion.getHeroId()).thenReturn(1);
    }
}
