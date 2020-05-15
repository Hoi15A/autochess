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

    private static final int HERO_ID_1 = Config.HERO_ID_1;
    private static final int HERO_ID_2 = Config.HERO_ID_2;
    private static final int INVALID_AMOUNT_NEGATIVE = -5;
    private static final int INVALID_AMOUNT_TOO_GREAT = 100000;
    private static final int HERO_HEALTH = 100;
    private static final int HERO_HEALTH_AMOUNT = 30;
    private static final int HERO_HEALTH_CALC = HERO_HEALTH - HERO_HEALTH_AMOUNT;

    private static final int HERO_FUNDS_TOP = Config.MAX_FUNDS;
    private static final int HERO_FUNDS_HALF = 50;
    private static final int HERO_FUNDS_AMOUNT = 10;
    private static final int HERO_FUNDS_INCREASE = HERO_FUNDS_HALF + HERO_FUNDS_AMOUNT;
    private static final int HERO_FUNDS_DECREASE = HERO_FUNDS_HALF - HERO_FUNDS_AMOUNT;

    private static final Config.HeroType TEST_HERO_TYPE = Config.HeroType.ALIEN;
    private static final Config.MinionType TEST_MINION_TYPE = Config.MinionType.WARRIOR;
    private static final Config.MinionType INVALID_MINION_TYPE= null;

    private static final int MINION_ID_1 = 3;
    private static final int MINION_ID_2 = 5;
    private static final int INVALID_MINION_ID = 50;
    private static final int MINION_PRICE = 10;
    private static final String TEST_STRING = "TEST";

    private HeroBase hero;

    @Mock private MinionBase minionOne;
    @Mock private MinionBase minionTwo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        //HeroBase.resetIdCounter();
    }

    @Test
    public void testNewHeroFromValidType() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = HeroBase.newHeroFromType(TEST_HERO_TYPE, HERO_ID_1);
        assertNotNull(hero);
        assertEquals(TEST_HERO_TYPE, hero.getHeroType());
    }

    @Test
    public void testNewHeroFromInvalidType() {
        assertThrows(InvalidHeroTypeException.class, () -> hero = HeroBase.newHeroFromType(null, HERO_ID_1));
    }

    @Test
    void testInitHero() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_ID_1);
        assertEquals(HERO_ID_1, hero.getId());
        hero = new HeroAlien(HERO_ID_2);
        assertEquals(HERO_ID_2, hero.getId());
    }

    @Test
    void testIncreaseHealthValidAmount() throws IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_TOP, HERO_ID_1);
        assertEquals(HERO_HEALTH, hero.getHealth());

        hero.decreaseHealth(HERO_HEALTH_AMOUNT);
        hero.decreaseHealth(HERO_HEALTH_AMOUNT);
        hero.increaseHealth(HERO_HEALTH_AMOUNT);
        assertEquals(HERO_HEALTH_CALC, hero.getHealth());

        hero.increaseHealth(HERO_HEALTH);
        assertEquals(HERO_HEALTH, hero.getHealth());
    }

    @Test
    void testIncreaseHealthInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_TOP, HERO_ID_1);
        assertEquals(HERO_HEALTH, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.increaseHealth(INVALID_AMOUNT_NEGATIVE));
        assertEquals(HERO_HEALTH, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.increaseHealth(INVALID_AMOUNT_TOO_GREAT));
        assertEquals(HERO_HEALTH, hero.getHealth());
    }

    @Test
    void testDecreaseHealthValidAmount() throws IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_TOP, HERO_ID_1);
        assertEquals(HERO_HEALTH, hero.getHealth());

        hero.decreaseHealth(HERO_HEALTH_AMOUNT);
        assertEquals(HERO_HEALTH_CALC, hero.getHealth());

        hero.increaseHealth(HERO_HEALTH);
        assertEquals(HERO_HEALTH, hero.getHealth());
    }

    @Test
    void testDecreaseHealthInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_TOP, HERO_ID_1);
        assertEquals(HERO_HEALTH, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.decreaseHealth(INVALID_AMOUNT_NEGATIVE));
        assertEquals(HERO_HEALTH, hero.getHealth());

        assertThrows(IllegalValueException.class, () -> hero.decreaseHealth(INVALID_AMOUNT_TOO_GREAT));
        assertEquals(HERO_HEALTH, hero.getHealth());
    }

    @Test
    void testIncreaseFundsValidAmount() throws IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        hero.increaseFunds(HERO_FUNDS_AMOUNT);
        assertEquals(HERO_FUNDS_INCREASE, hero.getFunds());

        hero.increaseFunds(HERO_FUNDS_TOP);
        assertEquals(HERO_FUNDS_TOP, hero.getFunds());
    }

    @Test
    void testIncreaseFundsInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.increaseFunds(INVALID_AMOUNT_NEGATIVE));
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.increaseFunds(INVALID_AMOUNT_TOO_GREAT));
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());
    }

    @Test
    void testDecreaseFundsValidAmount() throws IllegalValueException, IllegalFundsReductionException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        hero.decreaseFunds(HERO_FUNDS_AMOUNT);
        assertEquals(HERO_FUNDS_DECREASE, hero.getFunds());

        assertThrows(IllegalFundsReductionException.class, () -> hero.decreaseFunds(HERO_FUNDS_TOP));
        assertEquals(HERO_FUNDS_DECREASE, hero.getFunds());
    }

    @Test
    void testDecreaseFundsInvalidAmount() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.decreaseFunds(INVALID_AMOUNT_NEGATIVE));
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        assertThrows(IllegalValueException.class, () -> hero.decreaseFunds(INVALID_AMOUNT_TOO_GREAT));
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());
    }

    @Test
    void testBuyMinionFundsAvailable() throws MinionException, IllegalFundsReductionException, IllegalValueException, InvalidHeroTypeException, InvalidHeroAttributeException {
        //Cannot mock, buy calls on static newMinionFromType method to receive concrete Minion
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        hero.buyMinion(TEST_MINION_TYPE);
        assertEquals(HERO_FUNDS_DECREASE, hero.getFunds());
        assertEquals(1, hero.getMinionList().size());
    }

    @Test
    void testBuyMinionFundsNotAvailable() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, 0, HERO_ID_1);
        assertEquals(0, hero.getFunds());

        assertThrows(IllegalFundsReductionException.class, () -> hero.buyMinion(TEST_MINION_TYPE));
        assertEquals(0, hero.getMinionList().size());
        assertEquals(0, hero.getFunds());

    }

    @Test
    void testBuyMinionNullMinionTyp() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());

        assertThrows(InvalidMinionTypeException.class, () -> hero.buyMinion(INVALID_MINION_TYPE));
        assertEquals(0, hero.getMinionList().size());
        assertEquals(HERO_FUNDS_HALF, hero.getFunds());
    }

    @Test
    void testSellMinionValidMinionId() throws IllegalValueException, InvalidMinionIDException, InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, 0, HERO_ID_1);
        assertEquals(0, hero.getFunds());
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionOne.getType()).thenReturn(TEST_MINION_TYPE);
        when(minionOne.getPrice()).thenReturn(MINION_PRICE);

        hero.sellMinion(MINION_ID_1);

        assertEquals(MINION_PRICE, hero.getFunds());
        assertEquals(0, hero.getMinionList().size());
    }

    @Test
    void testSellMinionInvalidMinionId() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, 0, HERO_ID_1);
        assertEquals(0, hero.getFunds());
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionOne.getType()).thenReturn(TEST_MINION_TYPE);
        when(minionOne.getPrice()).thenReturn(MINION_PRICE);

        assertThrows(InvalidMinionIDException.class, () -> hero.sellMinion(INVALID_MINION_ID));
        assertEquals(1, hero.getMinionList().size());
    }

    @Test
    void testGetMinionValidId() throws InvalidHeroTypeException, InvalidHeroAttributeException, InvalidMinionIDException {
        hero = new HeroAlien(HERO_HEALTH, 0, HERO_ID_1);
        hero.getMinionList().add(minionOne);
        assertEquals(1, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(MINION_ID_1);

        assertEquals(minionOne, hero.getMinion(MINION_ID_1));
    }

    @Test
    void testGetMinionInvalidId() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, 0, HERO_ID_1);
        when(minionOne.getId()).thenReturn(MINION_ID_1);
        assertThrows(InvalidMinionIDException.class, () -> hero.getMinion(MINION_ID_1));
    }

    @Test
    void testGetAllMinionIds() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        hero.getMinionList().add(minionOne);
        hero.getMinionList().add(minionTwo);
        assertEquals(2, hero.getMinionList().size());

        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionTwo.getId()).thenReturn(MINION_ID_2);

        Set<Integer> minionIdSet = hero.getAllMinionIds();

        assertEquals(2, minionIdSet.size());
        assertTrue(minionIdSet.contains(MINION_ID_1));
        assertTrue(minionIdSet.contains(MINION_ID_2));
    }

    @Test
    void testGetMinionTypeValidId() throws InvalidHeroTypeException, InvalidHeroAttributeException, InvalidMinionIDException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        hero.getMinionList().add(minionOne);

        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionOne.getType()).thenReturn(TEST_MINION_TYPE);

        assertEquals(TEST_MINION_TYPE, hero.getMinionType(MINION_ID_1));
    }

    @Test
    void testGetMinionTypeInvalidId() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertThrows(InvalidMinionIDException.class, () -> hero.getMinionType(MINION_ID_1));
    }

    @Test
    void testGetMinionInfoAsStringValidId() throws InvalidHeroTypeException, InvalidHeroAttributeException, InvalidMinionIDException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        hero.getMinionList().add(minionOne);
        when(minionOne.getId()).thenReturn(MINION_ID_1);
        when(minionOne.getInfoAsString()).thenReturn(TEST_STRING);

        assertEquals(TEST_STRING, hero.getMinionInfoAsString(MINION_ID_1));
    }

    @Test
    void testGetMinionInfoAsStringInvalidId() throws InvalidHeroTypeException, InvalidHeroAttributeException {
        hero = new HeroAlien(HERO_HEALTH, HERO_FUNDS_HALF, HERO_ID_1);
        assertThrows(InvalidMinionIDException.class, () -> hero.getMinionType(MINION_ID_1));
    }

}
