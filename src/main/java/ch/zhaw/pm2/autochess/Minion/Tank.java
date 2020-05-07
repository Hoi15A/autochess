package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Board.MoveStrategy;

public class Tank extends MinionBase {
    public Tank(int heroId) {
        super(MinionType.TANK, MoveStrategy.StrategyType.DEFENCIVE, 30, 3, 5, 1, 2, 1, heroId);
    }

    @Override
    public String toString() {
        return "T";
    }

}
