package ch.zhaw.pm2.autochess.Minion.strategy;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

public abstract class MoveStrategy {

    public abstract PositionVector move(MinionBase[][] board, PositionVector position, int movementRange);

    public abstract PositionVector attack(MinionBase[][] board, PositionVector position, int attackRange);
}
