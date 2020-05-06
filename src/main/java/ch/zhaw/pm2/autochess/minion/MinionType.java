package ch.zhaw.pm2.autochess.minion;

public enum MinionType {
    WARRIOR(10), RANGER(20), TANK(15);

    private final int price;

    MinionType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static Minion getMinionFromType(MinionType minionType) throws IllegalArgumentException {
        Minion newMinion;

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
