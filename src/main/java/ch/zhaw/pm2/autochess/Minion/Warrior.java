package ch.zhaw.pm2.autochess.Minion;

public class Warrior extends MinionBase {
    public Warrior(int heroId) {
        super(MinionType.WARRIOR, 20, 7, 4, 1, 3, heroId);
    }

    @Override
    public String toString() {
        return "X";
    }
}
