package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public enum MinionType {
    WARRIOR(10), RANGER(20), TANK(15);

    private final int price;

    MinionType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static MinionBase getMinionFromType(MinionType minionType) throws IllegalArgumentException, MinionException {
        MinionBase newMinion;

        switch (minionType) {
            case WARRIOR:
                newMinion = new Warrior();
                break;
            case RANGER:
                newMinion = new Ranger();
                break;
            case TANK:
                newMinion = new Tank();
                break;
            default:
                throw new IllegalArgumentException("Invalid minionType");
        }

        return newMinion;
    }
}
