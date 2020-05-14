package ch.zhaw.pm2.autochess.Minion.strategy;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.List;
import java.util.Objects;

/**
 * An Aggressive variation of the MoveStrategy.
 * Attempts to move as close as possible to the nearest enemy minion.
 * Will always attack the closest minion.
 */
public class AggressiveStrategy extends MoveStrategy {

    /**
     * Will always move to the closest minion it can see
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of where the strategy wants to move. May return null for no movement.
     */
    @Override
    public PositionVector move(MinionBase[][] board, PositionVector position, MinionBase self) {
        PositionVector targetPosition = null;

        // Pick the nearest enemy as a direction to move in
        List<PositionVector> enemies = getNonFriendlyPositions(board, self);
        double shortest = 0.0;
        PositionVector targetEnemy = null;
        for (PositionVector possibleTarget : enemies) {
            double distance = calculateDistance(possibleTarget, position);
            if (shortest == 0.0 || distance < shortest) {
                shortest = distance;
                targetEnemy = possibleTarget;
            }
        }

        // Calc distances between enemy & possible positions and pick
        shortest = 0.0;

        List<PositionVector> possiblePositions = findPositionsInRange(getUnoccupiedSpaces(board), position, self.getMovementRange());
        if (Objects.nonNull(targetEnemy)) {
            for (PositionVector possiblePosition : possiblePositions) {
                double distance = calculateDistance(possiblePosition, targetEnemy);
                if (shortest == 0.0 || distance < shortest) {
                    shortest = distance;
                    targetPosition = possiblePosition;
                }
            }
        }

        // If the calculated move is the same position as the minion, do nothing
        if (position.equals(targetPosition)) {
            targetPosition = null;
        }

        return targetPosition;
    }

    /**
     * Will always attack the closest minion within attack range.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of the minion that the strategy wants to attack. May return null for no attack.
     */
    @Override
    public PositionVector attack(MinionBase[][] board, PositionVector position, MinionBase self) {
        // TODO: Deal with duplication
        PositionVector targetPosition = null;

        List<PositionVector> possibleTargets = findPositionsInRange(getNonFriendlyPositions(board, self), position, self.getAttackRange());

        // find nearest target in range
        double shortest = 0.0;
        if (!possibleTargets.isEmpty()) {
            for (PositionVector possibleTarget : possibleTargets) {
                double distance = calculateDistance(possibleTarget, position);
                if (shortest == 0.0 || distance < shortest) {
                    shortest = distance;
                    targetPosition = possibleTarget;
                }
            }
        }

        return targetPosition;
    }
}
