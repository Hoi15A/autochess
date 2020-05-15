package ch.zhaw.pm2.autochess.minion.strategy;

import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.List;

public class CowardStrategy extends MoveStrategy {

    private static final int ACTIVATION_RANGE = 2;

    /**
     * Searches for nearby enemy minions in activation range. If it finds any it considers the closest minion the biggest threat
     * and will try run as far away from it as possible.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of where the strategy wants to move. May return null for no movement.
     */
    @Override
    public PositionVector move(MinionBase[][] board, PositionVector position, MinionBase self) {

        PositionVector targetPosition = null;

        List<PositionVector> enemies = findPositionsInRange(getNonFriendlyPositions(board, self), position, ACTIVATION_RANGE);
        double shortest = 0.0;
        PositionVector closestEnemy = null;
        for (PositionVector possibleTarget : enemies) {
            double distance = calculateDistance(possibleTarget, position);
            if (shortest == 0.0 || distance < shortest) {
                shortest = distance;
                closestEnemy = possibleTarget;
            }
        }

        double longest = 0.0;
        List<PositionVector> possiblePositions = findPositionsInRange(getUnoccupiedSpaces(board), position, self.getMovementRange());
        if (closestEnemy != null) {
            for (PositionVector possiblePosition : possiblePositions) {
                double distance = calculateDistance(possiblePosition, closestEnemy);
                if (distance > longest) {
                    longest = distance;
                    targetPosition = possiblePosition;
                }
            }
        }

        return targetPosition;
    }

    /**
     * Will always attack the closest minion within attack range.
     * This is a duplicate of the aggressive attack strategy. It has been purposefully not been put in the superclass
     * as in future it could be made unique per strategy.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of the minion that the strategy wants to attack. May return null for no attack.
     */
    @Override
    public PositionVector attack(MinionBase[][] board, PositionVector position, MinionBase self) {
        PositionVector targetPosition = null;

        List<PositionVector> possibleTargets = findPositionsInRange(getNonFriendlyPositions(board, self), position, self.getAttackRange());

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
