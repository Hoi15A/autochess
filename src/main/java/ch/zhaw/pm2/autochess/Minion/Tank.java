package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.strategy.DefenciveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Tank extends MinionBase {
    public Tank(int heroId) throws MinionException {
        super(MinionType.TANK, new DefenciveStrategy(), 10, 30, 3, 5, 1, 2, 1, heroId);
    }

    @Override
    public String toString() {
        return "T";
    }

}
