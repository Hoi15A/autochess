package ch.zhaw.pm2.autochess.Minion.strategy;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.List;

/**
 * A Defensive variation of the MoveStrategy. Similar to aggressive.
 * Will move to nearby enemy minions if they fall within its activation range.
 * Will always attack the closest minion.
 */
public class Defensive extends MoveStrategy {

    private static final int ACTIVATION_RANGE = 3;

    /**
     * Will move to the closest minion it can see provided the minion is within a predefined activation range.
     * If there is no minion in the activation range no movement is made.
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
        List<PositionVector> l = getNonFriendlyPositions(board, self);
        List<PositionVector> enemies = findPositionsInRange(l, position, ACTIVATION_RANGE);
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
        if (targetEnemy != null) {
            for (PositionVector possiblePosition : possiblePositions) {
                double distance = calculateDistance(possiblePosition, targetEnemy);
                if (shortest == 0.0 || distance < shortest) {
                    shortest = distance;
                    targetPosition = possiblePosition;
                }
            }
        }

        return targetPosition;
    }

    /**
     * Will always attack the closest minion within attack range.
     * Same as the aggressive strategy.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of the minion that the strategy wants to attack. May return null for no attack.
     */
    @Override
    public PositionVector attack(MinionBase[][] board, PositionVector position, MinionBase self) {
        // this code is essentially a duplicate of aggressive, however putting it in the superclass doesnt really make much sense.
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
