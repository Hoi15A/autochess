package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.strategy.AggressiveStrategy;

public class Warrior extends MinionBase {
    public Warrior(int heroId) throws InvalidMinionAttributeException, InvalidMinionTypeException {
        super(Config.MinionType.WARRIOR, new AggressiveStrategy(), Config.WARRIOR_PRICE, Config.WARRIOR_HEALTH, Config.WARRIOR_ATTACK, Config.WARRIOR_DEFENCE, Config.WARRIOR_MOVEMENT_RANGE,Config.WARRIOR_ATTACK_RANGE, Config.WARRIOR_AGILITY, heroId);
    }


}
