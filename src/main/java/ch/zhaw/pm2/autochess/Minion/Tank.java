package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Minion.strategy.DefenciveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Tank extends MinionBase {
    public Tank(int heroId) throws MinionException {
        super(Config.MinionType.TANK, new DefenciveStrategy(), Config.TANK_PRICE, Config.TANK_HEALTH, Config.TANK_ATTACK, Config.TANK_DEFENCE, Config.TANK_MOVEMENT_RANGE, Config.TANK_ATTACK_RANGE, Config.TANK_AGILITY, heroId);
    }

    @Override
    public String toString() {
        return "T";
    }

}
