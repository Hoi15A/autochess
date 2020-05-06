package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;

public class MinionBase {

    private int price;
    private int id;
    private MinionType minionType;
    private int level;

    public MinionBase(int price, MinionType minionType) {
        this.price = price;
        this.minionType = minionType;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public MinionType getMinionType() {
        return minionType;
    }

    public int getLevel() {
        return 1;
    }

    public void increaseLevel() {

    }

    public void decreaseLevel() {

    }

    public enum MinionType {
        WARRIOR(10), RANGER(20), TANK(15);

        private int price;

        private MinionType(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public static MinionBase getMinionFromType(MinionType minionType) throws InvalidMinionTypeException {
            switch (minionType) {
                case WARRIOR:
                    return new MinionWorrior(minionType.getPrice(), minionType);
                case RANGER:
                    return new MinionRanger(minionType.getPrice(), minionType);
                case TANK:
                    return new MinionTank(minionType.getPrice(), minionType);
                default:
                    throw new InvalidMinionTypeException("No such minion type available");
            }
        }
    }
}
