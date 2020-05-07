package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

public class CowardStrategy extends MoveStrategy {
    @Override
    PositionVector move(MinionBase[][] board, PositionVector position, int movementRange) {
        return new PositionVector(0,1);
    }

    @Override
    PositionVector attack() {
        return null;
    }
}
