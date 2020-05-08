package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

public abstract class MoveStrategy {

    abstract PositionVector move(MinionBase[][] board, PositionVector position, int movementRange);

    abstract PositionVector attack(MinionBase[][] board, PositionVector position, int attackRange);

    public enum StrategyType {
        AGGRESSIVE, COWARD, DEFENCIVE;

        public static MoveStrategy getStrategyFromType(StrategyType strategyType) throws IllegalArgumentException{
            switch (strategyType) {
                case AGGRESSIVE:
                    return new AggressiveStrategy();
                case COWARD:
                    return new CowardStrategy();
                case DEFENCIVE:
                    return new DefenciveStrategy();
                default:
                    throw new IllegalArgumentException("No such Strategy found");
            }
        }
    }
}
