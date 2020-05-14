package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroAttributeException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroEngineer extends HeroBase{

    public HeroEngineer() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        super(Config.ENG_HEALTH, Config.ENG_START_FUNDS, Config.HeroType.ENGINEER);
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
        minion.changeHealth(Config.ENG_BUFF_HP);
    }
}