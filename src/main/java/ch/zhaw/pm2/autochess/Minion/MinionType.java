package ch.zhaw.pm2.autochess.Minion;

public enum MinionType {
    WARRIOR(10), RANGER(20), TANK(15);

    private final int price;

    MinionType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public static MinionBase getMinionFromType(MinionType minionType, int heroId) throws IllegalArgumentException {
        MinionBase newMinion;

        switch (minionType) {
            case WARRIOR:
                newMinion = new Warrior(heroId);
                break;
            case RANGER:
                newMinion = new Ranger(heroId);
                break;
            case TANK:
                newMinion = new Tank(heroId);
                break;
            default:
                throw new IllegalArgumentException("Invalid minionType");
        }

        return newMinion;
    }
}
