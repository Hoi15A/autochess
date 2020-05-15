package ch.zhaw.pm2.autochess.minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.minion.strategy.AggressiveStrategy;

/**
 * The warrior is a minion that focuses on attack.
 * It uses the AggressiveStrategy.
 */
public class Warrior extends MinionBase {

    /**
     * Create new warrior based on config
     *
     * @param heroId hero that owns this minion
     * @throws InvalidMinionAttributeException An attribute in the config is invalid
     * @throws InvalidMinionTypeException The type is invalid
     */
    public Warrior(int heroId) throws InvalidMinionAttributeException, InvalidMinionTypeException {
        super(Config.MinionType.WARRIOR, new AggressiveStrategy(), Config.WARRIOR_PRICE, Config.WARRIOR_HEALTH, Config.WARRIOR_ATTACK, Config.WARRIOR_DEFENCE, Config.WARRIOR_MOVEMENT_RANGE,Config.WARRIOR_ATTACK_RANGE, Config.WARRIOR_AGILITY, heroId);
    }
}
