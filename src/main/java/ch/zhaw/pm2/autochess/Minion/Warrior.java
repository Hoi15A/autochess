package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.strategy.AggressiveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Warrior extends MinionBase {
    public Warrior(int heroId) throws MinionException {
        super(MinionType.WARRIOR, new AggressiveStrategy(), 10, 20, 7, 4, 1,2, 3, heroId);
    }

    @Override
    public String toString() {
        return "W";
    }
}
