package ch.zhaw.pm2.autochess.minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.minion.strategy.CowardStrategy;

/**
 * The ranger is a minion that is more focused on attacking from longer distances.
 * Its strategy is the CowardStrategy
 */
public class Ranger extends MinionBase {

    /**
     * Create new ranger based on config
     *
     * @param heroId hero that owns this minion
     * @throws InvalidMinionAttributeException An attribute in the config is invalid
     * @throws InvalidMinionTypeException The type is invalid
     */
    public Ranger(int heroId) throws InvalidMinionAttributeException, InvalidMinionTypeException {
        super(Config.MinionType.RANGER, new CowardStrategy(), Config.RANGER_PRICE, Config.RANGER_HEALTH, Config.RANGER_ATTACK, Config.RANGER_DEFENCE, Config.RANGER_MOVEMENT_RANGE, Config.RANGER_ATTACK_RANGE, Config.RANGER_AGILITY, heroId);
    }
}
