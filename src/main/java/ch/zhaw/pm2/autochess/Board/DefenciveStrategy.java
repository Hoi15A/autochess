package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

public class DefenciveStrategy extends MoveStrategy {
    @Override
    PositionVector move(MinionBase[][] board, PositionVector position, int movementRange) {
        return null;
    }

    @Override
    PositionVector attack(MinionBase[][] board, PositionVector position, int movementRange) {
        return null;
    }
}
