package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

public class Ranger extends MinionBase {
    public Ranger() throws MinionException {
        super(MinionType.RANGER, 12, 5, 3, 3, 5);
    }
}
