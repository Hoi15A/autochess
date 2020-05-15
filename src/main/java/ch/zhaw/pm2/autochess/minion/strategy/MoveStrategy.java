package ch.zhaw.pm2.autochess.minion.strategy;

import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MoveStrategy {

    /**
     * Calculates the next move for a minion based on its position, movement range and the current state of the board.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of where the strategy wants to move. May return null for no movement.
     */
    public abstract PositionVector move(MinionBase[][] board, PositionVector position, MinionBase self);

    /**
     * Calculates the next attack for a minion based on its position, attack range and the current state of the board.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of the minion that the strategy wants to attack. May return null for no attack.
     */
    public abstract PositionVector attack(MinionBase[][] board, PositionVector position, MinionBase self);

    /**
     * Returns a list of PositionVectors containing minions that do not have the same owner as the minion provided.
     *
     * @param board The current board state.
     * @param self  The minion to use for checking if other minions are enemies or not.
     * @return Enemy positions.
     */
    protected List<PositionVector> getNonFriendlyPositions(MinionBase[][] board, MinionBase self) {
        List<PositionVector> positions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                MinionBase current = board[row][col];
                if (Objects.nonNull(current) && current.getId() != self.getId() && current.getHeroId() != self.getHeroId()) {
                    positions.add(new PositionVector(col, row));
                }
            }
        }

        return positions;
    }

    /**
     * Returns a list of PositionVectors within a certain range around a position.
     *
     * @param potentialTargets Targets to check the range for.
     * @param pos              Center position.
     * @param range            Range around the center.
     * @return Positions that are in range.
     */
    protected List<PositionVector> findPositionsInRange(List<PositionVector> potentialTargets, PositionVector pos, int range) {
        List<PositionVector> positionsInRange = new ArrayList<>();
        for (PositionVector target : potentialTargets) {
            int distance = (int) calculateDistance(target, pos);
            if (range >= distance) {
                positionsInRange.add(target);
            }
        }
        return positionsInRange;
    }

    /**
     * Returns a list of all spaces that contain no minions.
     *
     * @param board Current board state.
     * @return Positions with no minions.
     */
    protected List<PositionVector> getUnoccupiedSpaces(MinionBase[][] board) {
        List<PositionVector> unoccupied = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null) {
                    unoccupied.add(new PositionVector(col, row));
                }
            }
        }

        return unoccupied;
    }

    /**
     * Calculates the distance between two points.
     * Calculation: `sqrt(dotP(a-b, a-b)`
     *
     * @param a Starting position.
     * @param b Ending position.
     * @return distance
     */
    protected double calculateDistance(PositionVector a, PositionVector b) {
        PositionVector distanceVec = PositionVector.subtract(a, b);
        return Math.sqrt(PositionVector.scalarProduct(distanceVec, distanceVec));
    }
}
