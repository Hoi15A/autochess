package ch.zhaw.pm2.autochess.Board;

public abstract class MoveStrategy {

    abstract void move();

    abstract void attack();

    public enum StrategyType {
        AGGRESSIVE, COWARD, DEFENCIVE;

        private StrategyType() {
        }

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
