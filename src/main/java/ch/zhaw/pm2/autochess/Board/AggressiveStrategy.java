package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

public class AggressiveStrategy extends MoveStrategy {
    @Override
    public PositionVector move(MinionBase[][] board, PositionVector position, int movementRange) {
        return new PositionVector(0,0);
    }

    @Override
    public PositionVector attack() {
        return null;
    }
}
