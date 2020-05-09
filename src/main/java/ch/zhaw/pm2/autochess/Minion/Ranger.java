package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.strategy.CowardStrategy;
import ch.zhaw.pm2.autochess.Minion.strategy.MoveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Ranger extends MinionBase {

    public Ranger(int heroId) throws MinionException {
        super(MinionType.RANGER, new CowardStrategy(), 12, 5, 3, 3, 2, 5, heroId);
    }

    @Override
    public String toString() {
        return "R";
    }
}
