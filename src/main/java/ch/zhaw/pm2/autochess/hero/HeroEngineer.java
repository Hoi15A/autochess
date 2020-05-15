package ch.zhaw.pm2.autochess.hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.hero.exceptions.IllegalValueException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidHeroAttributeException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroEngineer extends HeroBase{

    /**
     * Constructor for concrete Engineer hero.
     * @throws InvalidHeroTypeException thrown if {@link Config} enum value is invalid
     * @throws InvalidHeroAttributeException thrown if {@link Config} parameter values are invalid
     */
    public HeroEngineer(int heroId) throws InvalidHeroTypeException, InvalidHeroAttributeException {
        super(Config.ENG_HEALTH, Config.ENG_START_FUNDS, Config.HeroType.ENGINEER, heroId);
    }

    @Override
    public void doAbility() throws IllegalValueException, InvalidMinionAttributeModifierException {
        if (abilityAvailable) {
            for(MinionBase minion : getMinionList()) {
                minion.setDefenseModifier(Config.ENG_ABILITY_DEF);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalValueException("Ability not available");
        }
    }

    @Override
    public void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException {
        minion.setDefenseModifier(Config.ENG_BUFF_DEF);
        minion.increaseHealth(Config.ENG_BUFF_HP);
    }
}
