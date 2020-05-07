package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Board.MoveStrategy;

public class Ranger extends MinionBase {

    public Ranger(int heroId) {
        super(MinionType.RANGER, MoveStrategy.StrategyType.COWARD, 12, 5, 3, 3, 2, 5, heroId);
    }

    @Override
    public String toString() {
        return "R";
    }
}