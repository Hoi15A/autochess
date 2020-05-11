package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroSpaceMarine extends HeroBase {

    public HeroSpaceMarine() throws IllegalHeroValueException {
        super(Config.MARINE_HEALTH, Config.MARINE_START_FUNDS);
    }

    @Override
    public void doAbility() throws InvalidMinionAttributeModifierException, IllegalHeroValueException {
        if (abilityAvailable) {
            for(MinionBase minion : getMinionList()) {
                minion.setAttackModifier(Config.MARINE_ABILITY_ATTACK);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalHeroValueException("Ability not available");
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
