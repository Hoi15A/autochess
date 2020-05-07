package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Tank extends MinionBase {
    public Tank() throws MinionException {
        super(MinionType.TANK, 30, 3, 5, 1, 1);
    }
}
