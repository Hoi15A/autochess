package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.PositionVector;

/**
 * Objects that hold data describing an action a minion took.
 */
public class BattleLog {

    private LogType type;
    private int attacker;
    private PositionVector attackerPos;
    private int defender;
    private PositionVector defenderPos;
    private int damageDealt;
    private int movingMinion;
    private PositionVector oldPos;
    private PositionVector newPos;
    private int deadMinion;

    /**
     * The type of action this log represents
     */
    public enum LogType {
        ATTACK, NO_ATTACK, MOVE, NO_MOVE, DEATH
    }

    /**
     * Set this log to be an attack log
     *
     * @param attackerId Id of minion that attacked
     * @param attackerPos Position of minion that attacked
     * @param defenderId Id of minion that is defending
     * @param defenderPos Position of minion that is defending
     * @param damage Damage dealt to defending minion
     */
    public void setAttackLog(int attackerId, PositionVector attackerPos, int defenderId, PositionVector defenderPos, int damage) {
        type = LogType.ATTACK;
        attacker = attackerId;
        this.attackerPos = attackerPos;
        defender = defenderId;
        this.defenderPos = defenderPos;
        damageDealt = damage;
    }

    /**
     * Set this log to be a no attack log
     *
     * @param attackerId Id of minion that did not attack
     * @param attackerPos Position of minion that did not attack
     */
    public void setNoAttackLog(int attackerId, PositionVector attackerPos) {
        type = LogType.NO_ATTACK;
        attacker = attackerId;
        this.attackerPos = attackerPos;
    }

    /**
     * Set this log to be a movement log
     *
     * @param minionId Id of minion that moved
     * @param currentPos Position the minion started at
     * @param newPos Position the minion moved to
     */
    public void setMoveLog(int minionId, PositionVector currentPos, PositionVector newPos) {
        type = LogType.MOVE;
        movingMinion = minionId;
        oldPos = currentPos;
        this.newPos = newPos;
    }

    /**
     * Set this log to be a no movement log
     *
     * @param minionId Id of minion that did not move
     * @param currentPos Position of the minion
     */
    public void setNoMoveLog(int minionId, PositionVector currentPos) {
        type = LogType.NO_MOVE;
        movingMinion = minionId;
        oldPos = currentPos;
    }

    /**
     * Set this log to be a death log
     *
     * @param minionId Id of minion that died
     */
    public void setDeathLog(int minionId) {
        type = LogType.DEATH;
        deadMinion = minionId;
    }

    /**
     * Get the logs type
     * @return type
     */
    public LogType getType() {
        return type;
    }

    /**
     * Get the attacking minion id
     * @return minionId
     */
    public int getAttacker() {
        return attacker;
    }

    /**
     * Get the attacking minion position
     * @return position
     */
    public PositionVector getAttackerPos() {
        return attackerPos;
    }

    /**
     * Get the defending minion id
     * @return minionId
     */
    public int getDefender() {
        return defender;
    }

    /**
     * Get the defending minions position
     * @return position
     */
    public PositionVector getDefenderPos() {
        return defenderPos;
    }

    /**
     * Get the damage dealt to the defending minion
     * @return damage
     */
    public int getDamageDealt() {
        return damageDealt;
    }

    /**
     * Get the minion that is moving
     * @return minionId
     */
    public int getMovingMinion() {
        return movingMinion;
    }

    /**
     * Get the position the moving minion started at
     * @return position
     */
    public PositionVector getOldPos() {
        return oldPos;
    }

    /**
     * Get the position the moving minion ended at
     * @return position
     */
    public PositionVector getNewPos() {
        return newPos;
    }

    /**
     * Get the minion that died
     * @return minionId
     */
    public int getDeadMinion() {
        return deadMinion;
    }
}
