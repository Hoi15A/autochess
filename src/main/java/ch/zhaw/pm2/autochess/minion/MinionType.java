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

    public static Minion getMinionFromType(MinionType minionType, int player) throws IllegalArgumentException {
        Minion newMinion;

        switch (minionType) {
            case WARRIOR:
                newMinion = new Minion(player, minionType, 10, 5, 5, 1, 3);
                break;
            case RANGER:
                newMinion = new Minion(player, minionType, 8, 3, 2, 3, 5);
                break;
            case TANK:
                newMinion = new Minion(player, minionType, 20, 2, 5, 1, 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid minionType");
        }

        return newMinion;
    }
}
