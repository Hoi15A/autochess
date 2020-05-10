package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;

public class HeroAlien extends HeroBase {

    public HeroAlien() {
        super(80, 25);
    }

    public HeroAlien(int health, int startingFunds) {
        super(health, startingFunds);
    }

    @Override
    public void doAbility() throws InvalidMinionAttributeModifierException, IllegalHeroValueException {
        if (abilityAvailable) {
            for (MinionBase minion : getMinionList()) {
                minion.setMovementRangeModifier(2);
            }
            abilityAvailable = false;
        } else {
            throw new IllegalHeroValueException("Ability not available");
        }
    }

    @Override
    public void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException {
        minion.setAttackModifier(6);
        minion.setDefenseModifier(-4);
    }
}
