package ch.zhaw.pm2.autochess.hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.hero.exceptions.IllegalValueException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidHeroAttributeException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroSpaceMarine extends HeroBase {

    /**
     * Constructor for concrete Space Marine hero.
     * @throws InvalidHeroTypeException thrown if {@link Config} enum value is invalid
     * @throws InvalidHeroAttributeException thrown if {@link Config} parameter values are invalid
     */
    public HeroSpaceMarine(int heroId) throws InvalidHeroTypeException, InvalidHeroAttributeException {
        super(Config.MARINE_HEALTH, Config.MARINE_START_FUNDS, Config.HeroType.SPACE_MARINE, heroId);
    }

    @Override
    public void doAbility() throws InvalidMinionAttributeModifierException, IllegalValueException {
        if (abilityAvailable) {
            for(MinionBase minion : getMinionList()) {
                minion.setAttackModifier(Config.MARINE_ABILITY_ATTACK);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalValueException("Ability not available");
        }
    }

    @Override
    public void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException {
        minion.setMovementRangeModifier(Config.MARINE_BUFF_MOVE_RANGE);
        if(minion.getAttackRange() > 1) {
            minion.setAttackRangeModifier(Config.MARINE_BUFF_ATTACK_RANGE);
        }
    }
}
