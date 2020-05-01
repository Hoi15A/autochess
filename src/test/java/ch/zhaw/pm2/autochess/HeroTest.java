package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Hero.HeroAlien;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Hero.MinionMOCK;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HeroTest {
    private HeroBase hero;
    @Mock private MinionMOCK minion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testInitHero() {

    }

    @Test
    void testIncreaseHealthValidAmount() {
        hero = new HeroAlien(50, 50);
        assertEquals(50, hero.getHealth());

        hero.increaseHealth(30);
        assertEquals(80, hero.getHealth());
    }
}
