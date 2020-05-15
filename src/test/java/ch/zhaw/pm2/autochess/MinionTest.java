package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.Ranger;
import ch.zhaw.pm2.autochess.Minion.Tank;
import ch.zhaw.pm2.autochess.Minion.Warrior;
import ch.zhaw.pm2.autochess.Minion.strategy.AggressiveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinionTest {
    private MinionBase minion;
    private final static int HERO_ID = 0;
    private final static int BIG_VALUE_OUT_OF_RANGE = 1000000;
    private final static int VALID_MODIFIER_IN_RANGE = 2;

    private class InvalidMinionTypeMinion extends MinionBase {
        public InvalidMinionTypeMinion() throws MinionException {
            super(null, null, 10, 10, 10, 10, 10, 10,10, HERO_ID);
        }
    }

    private class InvalidAttributeMinion extends MinionBase {
        public InvalidAttributeMinion() throws MinionException {
            super(Config.MinionType.TANK, new AggressiveStrategy(), -5, 10, 10, 10, 10 ,10, 10, HERO_ID);
        }
    }

    @BeforeEach
    public void reset() {
        MinionBase.resetMinionIdCounter();
    }

    @Test
    public void testCreateMinion() throws MinionException {
        int idCount = 0;
        minion = new Warrior(HERO_ID);
        assertEquals(idCount, minion.getId());
        assertEquals(Config.WARRIOR_AGILITY, minion.getAgility());
        assertEquals(Config.WARRIOR_ATTACK, minion.getAttack());
        assertEquals(Config.WARRIOR_DEFENCE, minion.getDefense());
        assertEquals(Config.WARRIOR_HEALTH, minion.getHealth());
        assertEquals(Config.WARRIOR_ATTACK_RANGE, minion.getAttackRange());
        assertEquals(Config.WARRIOR_MOVEMENT_RANGE, minion.getMovementRange());
        assertEquals(Config.WARRIOR_PRICE, minion.getPrice());

        minion = new Tank(HERO_ID);
        idCount++;
        assertEquals(idCount, minion.getId());
    }

    @Test
    public void testCreateInvalidMinionTypeMinion() {
        assertThrows(InvalidMinionTypeException.class, () -> {
            minion = new InvalidMinionTypeMinion();
        });
    }

    @Test
    public void testCreateInvalidAttributeMinion() {
        assertThrows(InvalidMinionAttributeException.class, () -> {
            minion = new InvalidAttributeMinion();
        });
    }

    @Test
    public void testValidHealthChange() throws MinionException {
        int modifier = -VALID_MODIFIER_IN_RANGE;
        minion = new Warrior(HERO_ID);
        int healthBefore = minion.getHealth();
        minion.changeHealth(modifier);
        int healthAfter = minion.getHealth();
        assertEquals(healthBefore + modifier, healthAfter);
    }

    @Test
    public void testMaxHealthLimit() throws MinionException {
        minion = new Warrior(HERO_ID);
        int healthBefore = minion.getHealth();
        minion.changeHealth(BIG_VALUE_OUT_OF_RANGE);
        int healthAfter = minion.getHealth();
        assertEquals(healthBefore, healthAfter);
    }

    @Test
    public void testMinHealthLimit() throws MinionException {
        minion = new Warrior(HERO_ID);
        minion.changeHealth(-BIG_VALUE_OUT_OF_RANGE);
        int healthAfter = minion.getHealth();
        assertEquals(0, healthAfter);
    }

    @Test
    public void testValidAttackModifier() throws MinionException {
        minion = new Ranger(HERO_ID);
        int atkBefore = minion.getAttack();
        minion.setAttackModifier(VALID_MODIFIER_IN_RANGE);
        assertEquals(atkBefore + VALID_MODIFIER_IN_RANGE, minion.getAttack());
    }

    @Test
    public void testInvalidAttackModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Ranger(HERO_ID);
            minion.setAttackModifier(-BIG_VALUE_OUT_OF_RANGE);
        });
    }

    @Test
    public void testValidDefenseModifier() throws MinionException {
        minion = new Tank(HERO_ID);
        int defBefore = minion.getDefense();
        minion.setDefenseModifier(VALID_MODIFIER_IN_RANGE);
        assertEquals(defBefore + VALID_MODIFIER_IN_RANGE, minion.getDefense());
    }

    @Test
    public void testInvalidDefenseModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Tank(HERO_ID);
            minion.setDefenseModifier(BIG_VALUE_OUT_OF_RANGE);
        });
    }

    @Test
    public void testValidRangeModifier() throws MinionException {
        minion = new Warrior(HERO_ID);
        int rangeBefore = minion.getMovementRange();
        minion.setMovementRangeModifier(VALID_MODIFIER_IN_RANGE);
        assertEquals(rangeBefore + VALID_MODIFIER_IN_RANGE, minion.getMovementRange());
    }

    @Test
    public void testInvalidRangeModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Warrior(HERO_ID);
            minion.setMovementRangeModifier(BIG_VALUE_OUT_OF_RANGE);
        });
    }

    @Test
    public void testValidAgilityModifier() throws MinionException {
        minion = new Warrior(HERO_ID);
        int agilBefore = minion.getAgility();
        minion.setAgilityModifier(VALID_MODIFIER_IN_RANGE);
        assertEquals(agilBefore + VALID_MODIFIER_IN_RANGE, minion.getAgility());
    }

    @Test
    public void testInvalidAgilityModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Warrior(HERO_ID);
            minion.setAgilityModifier(BIG_VALUE_OUT_OF_RANGE);
        });
    }
}
