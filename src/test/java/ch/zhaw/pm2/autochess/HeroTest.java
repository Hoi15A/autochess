package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Hero.HeroAlien;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Hero.MinionMOCK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HeroTest {
    private HeroBase hero;
    @Mock private MinionMOCK minionOne;
    @Mock private MinionMOCK minionTwo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testInitHero() {
        hero = new HeroAlien();
        assertEquals(0, hero.getId());
        hero = new HeroAlien();
        assertEquals(1, hero.getId());
    }

    @Test
    void testIncreaseHealthValidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getHealth());

        hero.increaseHealth(30);
        assertEquals(80, hero.getHealth());

        hero.increaseHealth(100);
        assertEquals(100, hero.getHealth());
    }

    @Test
    void testIncreaseHealthInvalidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalArgumentException.class, () -> hero.increaseHealth(-20));
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalArgumentException.class, () -> hero.increaseHealth(100000000));
        assertEquals(50, hero.getHealth());
    }

    @Test
    void testDecreaseHealthValidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getHealth());

        hero.decreaseHealth(30);
        assertEquals(20, hero.getHealth());

        hero.decreaseHealth(150);
        assertEquals(0, hero.getHealth());
    }

    @Test
    void testDecreaseHealthInvalidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalArgumentException.class, () -> hero.increaseHealth(-20));
        assertEquals(50, hero.getHealth());

        assertThrows(IllegalArgumentException.class, () -> hero.increaseHealth(100000000));
        assertEquals(50, hero.getHealth());
    }

    @Test
    void testIncreaseFundsValidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getFunds());

        hero.increaseFunds(30);
        assertEquals(80, hero.getFunds());

        hero.increaseFunds(150);
        assertEquals(100, hero.getFunds());
    }

    @Test
    void testIncreaseFundsInvalidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.increaseFunds(-20));
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.increaseFunds(100000000));
        assertEquals(50, hero.getFunds());
    }

    @Test
    void testDecreaseFundsValidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getFunds());

        hero.decreaseFunds(30);
        assertEquals(20, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.decreaseFunds(150));
        assertEquals(20, hero.getFunds());
    }

    @Test
    void testDecreaseFundsInvalidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.decreaseFunds(-20));
        assertEquals(50, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.decreaseFunds(100000000));
        assertEquals(50, hero.getFunds());
    }

    @Test
    void testBuyMinionFundsAvailable() {
        //todo: can't mock enum!? when(getPrice())

        hero = new HeroAlien(100,100);
        assertEquals(100, hero.getFunds());

        hero.buyMinion(MINION_TYP_MOCK.WARRIOR);

        assertEquals(90, hero.getFunds());
        assertEquals(1, hero.getMinionList().size());
    }

    @Test
    void testBuyMinionFundsNotAvailable() {
        hero = new HeroAlien(100, 0);
        assertEquals(0, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.buyMinion(MINION_TYP_MOCK.WARRIOR));
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testBuyMinionNullMinionTyp() {
        hero = new HeroAlien(100, 100);
        assertEquals(100, hero.getFunds());

        assertThrows(IllegalArgumentException.class, () -> hero.buyMinion(null));
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testSellMinionValidMinionId() {
        hero = new HeroAlien(100, 0);
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(3);
        when(minionOne.getMinionTyp()).thenReturn(MINION_TYP_MOCK.WARRIOR);

        hero.sellMinion(3);

        assertEquals(10, hero.getFunds());
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testSellMinionInvalidMinionId() {
        hero = new HeroAlien(100, 0);
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(3);
        when(minionOne.getMinionTyp()).thenReturn(MINION_TYP_MOCK.WARRIOR);

        assertThrows(IllegalArgumentException.class, () -> hero.sellMinion(1));
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
    void testGetAllMinionIds() {
        hero = new HeroAlien(100, 100);
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
