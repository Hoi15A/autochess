package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Warrior extends MinionBase {
    public Warrior() throws MinionException {
        super(MinionType.WARRIOR, 20, 7, 4, 1, 3);
    }
}
