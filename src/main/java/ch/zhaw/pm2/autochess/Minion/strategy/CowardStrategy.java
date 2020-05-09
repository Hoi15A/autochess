package ch.zhaw.pm2.autochess.Minion.strategy;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

public class CowardStrategy extends MoveStrategy {
    @Override
    public PositionVector move(MinionBase[][] board, PositionVector position, int movementRange) {
        if(board[position.getY()][position.getX() +1] == null) {
            return new PositionVector(1,0);
        } else {
            return null;
        }
    }

    @Override
    public PositionVector attack(MinionBase[][] board, PositionVector position, int movementRange) {
        if(board[position.getY()][position.getX()+1] == null) {
            return null;
        } else {
            return new PositionVector(6,3);
        }
    }
}
