package ch.zhaw.pm2.autochess.minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.minion.strategy.DefensiveStrategy;

/**
 * The tank is a minion that focuses more on defense.
 * It uses the DefensiveStrategy.
 */
public class Tank extends MinionBase {

    /**
     * Create new tank based on config
     *
     * @param heroId hero that owns this minion
     * @throws InvalidMinionAttributeException An attribute in the config is invalid
     * @throws InvalidMinionTypeException The type is invalid
     */
    public Tank(int heroId) throws InvalidMinionAttributeException, InvalidMinionTypeException {
        super(Config.MinionType.TANK, new DefensiveStrategy(), Config.TANK_PRICE, Config.TANK_HEALTH, Config.TANK_ATTACK, Config.TANK_DEFENCE, Config.TANK_MOVEMENT_RANGE, Config.TANK_ATTACK_RANGE, Config.TANK_AGILITY, heroId);
    }
}
