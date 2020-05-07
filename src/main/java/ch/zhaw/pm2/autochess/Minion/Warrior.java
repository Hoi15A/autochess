package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Board.MoveStrategy;

public class Warrior extends MinionBase {
    public Warrior(int heroId) {
        super(MinionType.WARRIOR, MoveStrategy.StrategyType.AGGRESSIVE, 20, 7, 4, 1,2, 3, heroId);
    }

    @Override
    public String toString() {
        return "X";
    }
}
