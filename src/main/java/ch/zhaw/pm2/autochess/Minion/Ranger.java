package ch.zhaw.pm2.autochess.Minion;

public class Ranger extends MinionBase {
    public Ranger(int heroId) {
        super(MinionType.RANGER, 12, 5, 3, 3, 5, heroId);
    }

    @Override
    public String toString() {
        return "R";
    }
}
