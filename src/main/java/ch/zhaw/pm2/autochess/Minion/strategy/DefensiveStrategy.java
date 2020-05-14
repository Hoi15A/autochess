package ch.zhaw.pm2.autochess.Minion.strategy;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.List;
import java.util.Objects;

/**
 * A Defensive variation of the MoveStrategy. Similar to aggressive.
 * Will move to nearby enemy minions if they fall within its activation range.
 * Will always attack the closest minion.
 */
public class DefensiveStrategy extends MoveStrategy {

    private static final int ACTIVATION_RANGE = 3;
    private static final int MAX_NO_MOVE_TURNS = 4;
    private int didNotMoveCount = 0;

    /**
     * Will move to the closest minion it can see provided the minion is within a predefined activation range.
     * If there is no minion in the activation range no movement is made.
     * If the minion has not moved in MAX_NO_MOVE_TURNS turns it will increase the activation range by 10.
     *
     * @param board    Current board state.
     * @param position The position that the movement should be based on.
     * @param self     The minion being controlled by the strategy.
     * @return PositionVector of where the strategy wants to move. May return null for no movement.
     */
    @Override
    public PositionVector move(MinionBase[][] board, PositionVector position, MinionBase self) {

        PositionVector targetPosition = null;

        int activationRangeMod = 0;
        if (didNotMoveCount > MAX_NO_MOVE_TURNS) {
            activationRangeMod = 10;
            didNotMoveCount = 0;
        }

        List<PositionVector> enemies = findPositionsInRange(getNonFriendlyPositions(board, self), position, ACTIVATION_RANGE + activationRangeMod);
        double shortest = 0.0;
        PositionVector targetEnemy = null;
        for (PositionVector possibleTarget : enemies) {
            double distance = calculateDistance(possibleTarget, position);
            if (shortest == 0.0 || distance < shortest) {
                shortest = distance;
                targetEnemy = possibleTarget;
            }
        }

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

        if (Objects.isNull(targetPosition)) {
            didNotMoveCount++;
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
        // TODO: Deal with duplication
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
