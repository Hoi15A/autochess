package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Board.MoveStrategy;
import ch.zhaw.pm2.autochess.Minion.*;
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

    private class InvalidMinionTypeMinion extends MinionBase {
        public InvalidMinionTypeMinion() throws MinionException {
            super(null, null, 10, 10, 10, 10, 10, 10,10 );
        }
    }

    private class InvalidAttributeMinion extends MinionBase {
        public InvalidAttributeMinion() throws MinionException {
            super(MinionType.TANK, MoveStrategy.StrategyType.AGGRESSIVE, -5, 10, 10, 10, 10 ,10, 10);
        }
    }

    @BeforeEach
    public void reset() {
        MinionBase.resetMinionIdCounter();
    }

    @Test
    public void testCreateMinion() throws MinionException {
        minion = new Warrior(0);
        assertEquals(0, minion.getId());
        assertEquals(20, minion.getHealth());
        assertEquals(7, minion.getAttack());
        minion = new Tank(0);
        assertEquals(1, minion.getId());
        assertEquals(5, minion.getDefense());
        assertEquals(1, minion.getMovementRange());
        assertEquals(1, minion.getAgility());
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
        int modifier = -5;
        minion = new Warrior(0);
        int healthBefore = minion.getHealth();
        minion.changeHealth(modifier);
        int healthAfter = minion.getHealth();
        assertEquals(healthBefore + modifier, healthAfter);
    }

    @Test
    public void testMaxHealthLimit() throws MinionException {
        int modifier = 10000;
        minion = new Warrior(0);
        int healthBefore = minion.getHealth();
        minion.changeHealth(modifier);
        int healthAfter = minion.getHealth();
        assertEquals(healthBefore, healthAfter);
    }

    @Test
    public void testMinHealthLimit() throws MinionException {
        int modifier = -10000;
        minion = new Warrior(0);
        minion.changeHealth(modifier);
        int healthAfter = minion.getHealth();
        assertEquals(0, healthAfter);
    }

    @Test
    public void testInitMinionLevel() throws MinionException {
        minion = new Tank(0);
        assertEquals(1, minion.getLevel());
    }

    @Test
    public void testChangeMinionLevel() throws MinionException {
        minion = new Tank(0);
        minion.modifyLevel(1);
        assertEquals(2, minion.getLevel());
    }

    @Test
    public void testMinMinionLevel() throws MinionException {
        minion = new Tank(0);
        minion.modifyLevel(-100);
        assertEquals(1, minion.getLevel());
    }

    @Test
    public void testMaxMinionLevel() throws MinionException {
        minion = new Tank(0);
        minion.modifyLevel(100);
        assertEquals(3, minion.getLevel());
    }

    @Test
    public void testValidAttackModifier() throws MinionException {
        int modifier = 1;
        minion = new Ranger(0);
        int atkBefore = minion.getAttack();
        minion.setAttackModifier(modifier);
        assertEquals(atkBefore + modifier, minion.getAttack());
    }

    @Test
    public void testInvalidAttackModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Ranger(0);
            minion.setAttackModifier(-100000);
        });
    }

    @Test
    public void testValidDefenseModifier() throws MinionException {
        int modifier = 1;
        minion = new Tank(0);
        int defBefore = minion.getDefense();
        minion.setDefenseModifier(modifier);
        assertEquals(defBefore + modifier, minion.getDefense());
    }

    @Test
    public void testInvalidDefenseModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Tank(0);
            minion.setDefenseModifier(100000);
        });
    }

    @Test
    public void testValidRangeModifier() throws MinionException {
        int modifier = 3;
        minion = new Warrior(0);
        int rangeBefore = minion.getMovementRange();
        minion.setMovementRangeModifier(modifier);
        assertEquals(rangeBefore + modifier, minion.getMovementRange());
    }

    @Test
    public void testInvalidRangeModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Warrior(0);
            minion.setMovementRangeModifier(100000);
        });
    }

    @Test
    public void testValidAgilityModifier() throws MinionException {
        int modifier = -2;
        minion = new Warrior(0);
        int agilBefore = minion.getAgility();
        minion.setAgilityModifier(modifier);
        assertEquals(agilBefore + modifier, minion.getAgility());
    }

    @Test
    public void testInvalidAgilityModifier() {
        assertThrows(InvalidMinionAttributeModifierException.class, () -> {
            minion = new Warrior(0);
            minion.setAgilityModifier(100000);
        });
    }

}
