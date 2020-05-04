package ch.zhaw.pm2.autochess;

public abstract class MoveStrategy {

    abstract void move();

    abstract void attack();

    public enum STRATEGY_TYP {
        AGGRESSIVE;

        private STRATEGY_TYP() {
        }

        public static MoveStrategy getStrategyFromType(STRATEGY_TYP strategy_typ) throws IllegalArgumentException{
            return new AggressiveStrategy();
        }
    }
}
