package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroSpaceMarine extends HeroBase {

    public HeroSpaceMarine() {
        super(100, 20);
    }

    @Override
    public void doAbility() throws InvalidMinionAttributeModifierException, IllegalHeroValueException {
        if (abilityAvailable) {
            for(MinionBase minion : getMinionList()) {
                minion.setAttackModifier(2);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalHeroValueException("Ability not available");
        }
    }

    @Override
    public void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException {
        minion.setMovementRangeModifier(2);
        if(minion.getAttackRange() > 1) {
            minion.setAttackRangeModifier(2);
        }
    }
}
