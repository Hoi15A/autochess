package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Minion.strategy.CowardStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Ranger extends MinionBase {

    public Ranger(int heroId) throws MinionException {
        super(Config.MinionType.RANGER, new CowardStrategy(), Config.RANGER_PRICE, Config.RANGER_HEALTH, Config.RANGER_ATTACK, Config.RANGER_DEFENCE, Config.RANGER_MOVEMENT_RANGE, Config.RANGER_ATTACK_RANGE, Config.RANGER_AGILITY, heroId);
    }

    @Override
    public String toString() {
        return "R";
    }
}
