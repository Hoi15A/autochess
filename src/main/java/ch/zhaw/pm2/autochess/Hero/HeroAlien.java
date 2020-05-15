package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroAttributeException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroAlien extends HeroBase {

    /**
     * Constructor for concrete Alien hero.
     * @throws InvalidHeroTypeException thrown if {@link Config} enum value is invalid
     * @throws InvalidHeroAttributeException thrown if {@link Config} parameter values are invalid
     */
    public HeroAlien(int heroId) throws InvalidHeroTypeException, InvalidHeroAttributeException {
        super(Config.ALIEN_HEALTH, Config.ALIEN_START_FUNDS, Config.HeroType.ALIEN, heroId);
    }

    //ONLY FOR TESTING
    public HeroAlien(int health, int startingFunds, int heroId) throws InvalidHeroTypeException, InvalidHeroAttributeException {
        super(health, startingFunds, Config.HeroType.ALIEN, heroId);
    }

    @Override
    public void doAbility() throws InvalidMinionAttributeModifierException, IllegalValueException {
        if (abilityAvailable) {
            for (MinionBase minion : getMinionList()) {
                minion.setMovementRangeModifier(Config.ALIEN_ABILITY_MOVE_RANGE);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalValueException("Ability not available");
        }
    }

    @Override
    public void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException {
        minion.setAttackModifier(Config.ALIEN_BUFF_ATTACK);
        minion.setDefenseModifier(Config.ALIEN_BUFF_DEF);
    }
}
