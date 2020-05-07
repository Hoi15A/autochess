package ch.zhaw.pm2.autochess.Minion;

public class Tank extends MinionBase {
    public Tank(int heroId) {
        super(MinionType.TANK, 30, 3, 5, 1, 1, heroId);
    }

    @Override
    public String toString() {
        return "T";
    }

}
