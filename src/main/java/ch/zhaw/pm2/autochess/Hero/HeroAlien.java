package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroAlien extends HeroBase {

    public HeroAlien() throws IllegalHeroValueException, InvalidHeroTypeException {
        super(Config.ALIEN_HEALTH, Config.ALIEN_START_FUNDS, Config.HeroType.ALIEN);
    }

    public HeroAlien(int health, int startingFunds) throws IllegalHeroValueException, InvalidHeroTypeException {
        super(health, startingFunds, Config.HeroType.ALIEN);
    }

    @Override
    public void doAbility() throws InvalidMinionAttributeModifierException, IllegalHeroValueException {
        if (abilityAvailable) {
            for (MinionBase minion : getMinionList()) {
                minion.setMovementRangeModifier(Config.ALIEN_ABILITY_MOVE_RANGE);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalHeroValueException("Ability not available");
        }
    }

    @Override
    public void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException {
        minion.setAttackModifier(Config.ALIEN_BUFF_ATTACK);
        minion.setDefenseModifier(Config.ALIEN_BUFF_DEF);
    }
}
