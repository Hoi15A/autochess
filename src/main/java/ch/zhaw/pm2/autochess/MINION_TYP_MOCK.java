package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Hero.MinionMOCK;

public enum MINION_TYP_MOCK {
    WARRIOR(10);

    int price;

    MINION_TYP_MOCK(int price) {
        this.price = price;
    }

    public static MinionMOCK getMinion() {
        return null;
    }

    public int getPrice() {
        return price;
    }
}
