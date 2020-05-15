package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.game.Game;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.hero.HeroBase;
import ch.zhaw.pm2.autochess.hero.exceptions.*;
import ch.zhaw.pm2.autochess.minion.exceptions.MinionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;

    private static final Config.HeroType TEST_HERO_TYPE_1 = Config.HeroType.ALIEN;
    private static final Config.HeroType TEST_HERO_TYPE_2 = Config.HeroType.ENGINEER;
    private static final int HERO_ID_1 = 1;
    private static final int HERO_ID_2 = 2;
    private static final int HERO_HEALTH = 5;
    private static final int HERO_FUNDS = 20;
    private static final boolean HERO_ABILITY_STATUS = false;
    private static final Config.MinionType TEST_MINION_TYPE = Config.MinionType.WARRIOR;
    private static final int MINION_PRICE = Config.WARRIOR_PRICE;
    private static final int MINION_ID = 5;
    private static final String TEST_STRING = "Test";

    @Mock private HeroBase heroOne;
    @Mock private HeroBase heroTwo;

    @BeforeEach
    public void setUp() throws IllegalGameStateException {
        MockitoAnnotations.initMocks(this);
        game = new Game(TEST_HERO_TYPE_1, TEST_HERO_TYPE_2);
    }

    private void initGameHeroMocks() {
        game.getHeroList().clear();
        game.getHeroList().add(heroOne);
        game.getHeroList().add(heroTwo);
    }

    @Test
    public void testGetWinnerNoWinner() {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroTwo.getId()).thenReturn(HERO_ID_2);
        when(heroOne.getHealth()).thenReturn(HERO_HEALTH);
        when(heroTwo.getHealth()).thenReturn(HERO_HEALTH);

        assertEquals(-1, game.getWinner());
    }

    @Test
    public void testGetWinnerValid() {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroTwo.getId()).thenReturn(HERO_ID_2);
        when(heroOne.getHealth()).thenReturn(HERO_HEALTH);
        when(heroTwo.getHealth()).thenReturn(0);

        assertEquals(HERO_ID_1, game.getWinner());
    }

    @Test
    public void testGetHeroFundsValidId() throws IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroOne.getFunds()).thenReturn(HERO_FUNDS);
        assertEquals(HERO_FUNDS, game.getHeroFunds(HERO_ID_1));
    }

    @Test
    public void testGetHeroFundsInvalidId() {
        initGameHeroMocks();
        assertThrows(IllegalGameStateException.class, () -> game.getHeroFunds(HERO_ID_1));
    }

    @Test
    public void testGetHeroHealthValidId() throws IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroOne.getHealth()).thenReturn(HERO_HEALTH);
        assertEquals(HERO_HEALTH, game.getHeroHealth(HERO_ID_1));
    }

    @Test
    public void testGetHeroHealthInvalidId() {
        initGameHeroMocks();
        assertThrows(IllegalGameStateException.class, () -> game.getHeroHealth(HERO_ID_1));
    }

    @Test
    public void testGetHeroAbilityStatusValidId() throws IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroOne.isAbilityAvailable()).thenReturn(HERO_ABILITY_STATUS);
        assertEquals(HERO_ABILITY_STATUS, game.getHeroAbilityStatus(HERO_ID_1));
    }

    @Test
    public void testGetHeroAbilityStatusInvalidId() {
        initGameHeroMocks();
        assertThrows(IllegalGameStateException.class, () -> game.getHeroAbilityStatus(HERO_ID_1));
    }

    @Test
    public void testBuyMinion() throws MinionException, HeroException, IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        game.buyMinion(HERO_ID_1, TEST_MINION_TYPE);
        verify(heroOne, times(1)).buyMinion(TEST_MINION_TYPE);
    }

    @Test
    public void testSellMinion() throws MinionException, HeroException, IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        game.sellMinion(HERO_ID_1, MINION_ID);
        verify(heroOne, times(1)).sellMinion(MINION_ID);
    }

    @Test
    public void testGetMinionInfoAsStringValidId() throws InvalidMinionIDException, IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroOne.getMinionInfoAsString(MINION_ID)).thenReturn(TEST_STRING);
        assertEquals(TEST_STRING, game.getMinionInfoAsString(HERO_ID_1, MINION_ID));
    }

    @Test
    public void testGetMinionInfoAsStringInvalidId() {
        initGameHeroMocks();
        assertThrows(IllegalGameStateException.class, () -> game.getMinionType(HERO_ID_1, MINION_ID));
    }

    @Test
    public void testGetMinionTypeValidId() throws InvalidMinionIDException, IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroOne.getMinionType(MINION_ID)).thenReturn(TEST_MINION_TYPE);
        assertEquals(TEST_MINION_TYPE, game.getMinionType(HERO_ID_1, MINION_ID));
    }

    @Test
    public void testGetMinionTypeInvalidId() {
        initGameHeroMocks();
        assertThrows(IllegalGameStateException.class, () -> game.getMinionType(HERO_ID_1, MINION_ID));
    }

    @Test
    public void testDoBattle() throws IllegalValueException, IllegalGameStateException {
        initGameHeroMocks();
        when(heroOne.getId()).thenReturn(HERO_ID_1);
        when(heroTwo.getId()).thenReturn(HERO_ID_2);
        game.doBattle();
        verify(heroOne, times(1)).decreaseHealth(any(Integer.class));
        verify(heroOne, times(1)).increaseFunds(any(Integer.class));
        verify(heroOne, times(1)).resetAllMinionHealth();
        verify(heroTwo, times(1)).decreaseHealth(any(Integer.class));
        verify(heroTwo, times(1)).increaseFunds(any(Integer.class));
        verify(heroTwo, times(1)).resetAllMinionHealth();
    }
}
