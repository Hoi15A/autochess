package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Hero.HeroAlien;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Hero.exceptions.*;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HeroTest {
    private HeroBase hero;
    @Mock private MinionBase minionOne;
    @Mock private MinionBase minionTwo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        //HeroBase.resetIdCounter();
    }

    @Test
    void testInitHero() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(1);
        assertEquals(1, hero.getId());
        hero = new HeroAlien(2);
        assertEquals(2, hero.getId());
    }

    @Test
    void testIncreaseHealthValidAmount() throws IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(100, 50, 1);
        assertEquals(100, hero.getHealth());

        hero.decreaseHealth(40);
        hero.increaseHealth(20);
        assertEquals(80, hero.getHealth());

        hero.increaseHealth(100);
        assertEquals(100, hero.getHealth());
    }

    @Test
    void testIncreaseHealthInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.increaseHealth(-20));
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.increaseHealth(100000000));
        assertEquals(50, hero.getHealth());
    }

    @Test
    void testDecreaseHealthValidAmount() throws IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getHealth());

        hero.decreaseHealth(30);
        assertEquals(20, hero.getHealth());

        hero.decreaseHealth(150);
        assertEquals(0, hero.getHealth());
    }

    @Test
    void testDecreaseHealthInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.increaseHealth(-20));
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.increaseHealth(100000000));
        assertEquals(50, hero.getHealth());
    }

    @Test
    void testIncreaseFundsValidAmount() throws IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getFunds());

        hero.increaseFunds(30);
        assertEquals(80, hero.getFunds());

        hero.increaseFunds(150);
        assertEquals(100, hero.getFunds());
    }

    @Test
    void testIncreaseFundsInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.increaseFunds(-20));
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.increaseFunds(100000000));
        assertEquals(50, hero.getFunds());
    }

    @Test
    void testDecreaseFundsValidAmount() throws IllegalValueException, IllegalFundsReductionException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getFunds());

        hero.decreaseFunds(30);
        assertEquals(20, hero.getFunds());

        assertThrows(IllegalFundsReductionException.class, () -> hero.decreaseFunds(150));
        assertEquals(20, hero.getFunds());
    }

    @Test
    void testDecreaseFundsInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(50, 50, 1);
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.decreaseFunds(-20));
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.decreaseFunds(100000000));
        assertEquals(50, hero.getFunds());
    }

    @Test
    void testBuyMinionFundsAvailable() throws MinionException, IllegalFundsReductionException, IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        //todo: can't mock enum!? when(getPrice())

        hero = new HeroAlien(100,100, 1);
        assertEquals(100, hero.getFunds());

        hero.buyMinion(Config.MinionType.WARRIOR);

        assertEquals(90, hero.getFunds());
        assertEquals(1, hero.getMinionList().size());
    }

    @Test
    void testBuyMinionFundsNotAvailable() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(100, 0, 1);
        assertEquals(0, hero.getFunds());

        assertThrows(IllegalFundsReductionException.class, () -> hero.buyMinion(Config.MinionType.WARRIOR));
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testBuyMinionNullMinionTyp() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(100, 100, 1);
        assertEquals(100, hero.getFunds());

        assertThrows(InvalidMinionTypeException.class, () -> hero.buyMinion(null));
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testSellMinionValidMinionId() throws IllegalValueException, InvalidMinionIDException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(100, 0, 1);
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(3);
        when(minionOne.getType()).thenReturn(Config.MinionType.WARRIOR);
        when(minionOne.getPrice()).thenReturn(10);

        hero.sellMinion(3);

        assertEquals(10, hero.getFunds());
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testSellMinionInvalidMinionId() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(100, 0, 1);
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(3);
        when(minionOne.getType()).thenReturn(Config.MinionType.WARRIOR);

        assertThrows(InvalidMinionIDException.class, () -> hero.sellMinion(1));
        assertEquals(1, hero.getMinionList().size());
    }

    /*
    @Test
    void testSellMinionInvalidMinionTyp() {
        //dont know if test needed
        //sell checks minions minionTyp. could somehow be null. no check in code
    }
     */

    @Test
    void testGetAllMinionIds() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(100, 100, 1);
        hero.getMinionList().add(minionOne);
        hero.getMinionList().add(minionTwo);
        assertEquals(2, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(3);
        when(minionTwo.getId()).thenReturn(5);

        Set<Integer> minionIdSet = hero.getAllMinionIds();

        assertEquals(2, minionIdSet.size());
        assertTrue(minionIdSet.contains(3));
        assertTrue(minionIdSet.contains(5));
        assertFalse(minionIdSet.contains(2));
    }

}
