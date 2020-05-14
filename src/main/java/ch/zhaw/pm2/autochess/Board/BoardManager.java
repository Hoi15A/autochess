package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.*;

public class BoardManager {

    private MinionBase[][] boardArray2d = new MinionBase[Config.BOARD_HEIGHT][Config.BOARD_WIDTH];
    private List<BattleLog> battleLogs = new ArrayList<>();

    //ONLY FOR TESTING
    public MinionBase[][] getBoardArray2d() {
        return boardArray2d;
    }

    public void clearBoard() {
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                boardArray2d[i][j] = null;
            }
        }
    }

    private MinionBase getContentFromPosition(PositionVector position) throws InvalidPositionException {
        validatePosOnBoard(position);
        return boardArray2d[position.getY()][position.getX()];
    }

    private void validatePosOnBoard(PositionVector position) throws InvalidPositionException {
        if(position == null || position.getX() < 0 || position.getY() < 0 || position.getX() >= Config.BOARD_WIDTH || position.getY() >= Config.BOARD_HEIGHT) {
            throw new InvalidPositionException("Invalid position: " + position + ". Valid between: " + Config.BOARD_WIDTH + " and " + Config.BOARD_HEIGHT);
        }
    }

    private void validatePosEmpty(PositionVector position) throws InvalidPositionException {
        validatePosOnBoard(position);
        MinionBase minion = getContentFromPosition(position);
        if(Objects.nonNull(minion)) {
            throw new InvalidPositionException("Invalid position: " + position + ". Occupied by: " + minion);
        }
    }

    public void setMinionOnBoard(MinionBase minion, PositionVector pos) throws InvalidPositionException {
        validatePosOnBoard(pos);
        validatePosEmpty(pos);
        try {
            getMinionPosition(minion.getId());
            throw new InvalidPositionException("Invalid placement (MinionID:" + minion.getId() + "). Already placed.");
        }catch (MinionNotOnBoardException e) {
            boardArray2d[pos.getY()][pos.getX()] = minion;
        }
    }

    public void removeMinionFromBoard(int minionId) throws MinionNotOnBoardException {
        PositionVector pos = getMinionPosition(minionId);
        boardArray2d[pos.getY()][pos.getX()] = null;
    }

    public PositionVector getMinionPosition(int minionId) throws MinionNotOnBoardException {
        PositionVector position = null;
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                MinionBase minion = boardArray2d[i][j];
                if(Objects.nonNull(minion) && minion.getId() == minionId) {
                    position = new PositionVector(j, i);
                }
            }
        }
        if(Objects.isNull(position)) {
            throw new MinionNotOnBoardException("Invalid minion ID: " + minionId + ". Matches no minion on board");
        }
        return position;
    }

    public ArrayList<MinionBase> getAllMinionsOnBoard() {
        ArrayList<MinionBase> listActiveMinions = new ArrayList<>();
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                MinionBase minion = boardArray2d[i][j];
                if(Objects.nonNull(minion)) {
                    listActiveMinions.add(minion);
                }
            }
        }
        return listActiveMinions;
    }

    public ArrayList<MinionBase> getSpecHeroMinionsFromBoard(int heroId) {
        ArrayList<MinionBase> listActiveMinions = new ArrayList<>();
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                MinionBase minion = boardArray2d[i][j];
                if(Objects.nonNull(minion) && minion.getHeroId() == heroId) {
                    listActiveMinions.add(minion);
                }
            }
        }
        return listActiveMinions;
    }

    public int getNumberOfMinionsPerHero(int heroId) {
        return getSpecHeroMinionsFromBoard(heroId).size();
    }

    private boolean checkEachHeroActiveMinions(ArrayList<MinionBase> activeMinions) {
        //todo: maybe check with getNumberOfMinionsOfHero (Would need to know hero Id for that)
        Set<Integer> heroSet = new HashSet<>();
        for(MinionBase minion : activeMinions) {
            heroSet.add(minion.getHeroId());
        }
        boolean isGreaterOneHero = false;
        if(heroSet.size() > 1) {
            isGreaterOneHero = true;
        }
        return isGreaterOneHero;
    }

    public void doBattle() throws MinionNotOnBoardException, InvalidPositionException {
        ArrayList<MinionBase> activeMinions = getAllMinionsOnBoard();
        //todo: sort by agility
        //todo: implement battleLog
        //todo: replace prints with battleLog
        
        System.out.println("All active minions");
        for(MinionBase minion : activeMinions) {
            minion.printInfo();
        }
        System.out.println(" ");

        int loopCounter = 0;
        while(checkEachHeroActiveMinions(activeMinions) && loopCounter <= Config.MAX_BATTLE_LOOPS){
            for(Iterator<MinionBase> it = activeMinions.iterator(); it.hasNext(); ) {
                MinionBase minion = it.next();
                if(minion.getHealth() > 0) {
                    System.out.print("Current : ");
                    minion.printInfo();
                    minionDoMove(minion);
                    minionDoAttack(minion);
                } else {
                    it.remove();
                }
            }
            loopCounter++;
        }
    }

    private void minionDoMove(MinionBase minion) throws MinionNotOnBoardException, InvalidPositionException {
        //todo: replace prints with battleLog
        PositionVector currentPos = getMinionPosition(minion.getId());
        PositionVector movePosition = minion.move(boardArray2d, currentPos);
        BattleLog log = new BattleLog();
        if(movePosition == null) {
            System.out.println("-- NO_MOVE: minion " + minion.getId() + " " + "Pos" + currentPos);
            log.setNoMoveLog(minion.getId(), currentPos);

        } else {
            validatePosOnBoard(movePosition);
            validatePosEmpty(movePosition);
            removeMinionFromBoard(minion.getId());
            PositionVector newPos = PositionVector.add(currentPos, movePosition);
            setMinionOnBoard(minion, newPos);
            System.out.println("-- MOVED: minion " + minion.getId() + " " + "From: " + currentPos + " To: " + newPos);
            log.setMoveLog(minion.getId(), currentPos, newPos);
        }
        battleLogs.add(log);
    }

    private void minionDoAttack(MinionBase attacker) throws MinionNotOnBoardException, InvalidPositionException {
        //todo: replace prints with battleLog
        PositionVector currentPos = getMinionPosition(attacker.getId());
        PositionVector attackPosition = attacker.attack(boardArray2d, currentPos);

        if (attackPosition == null) {
            System.out.println("-- NO_ATTACK: minion " + attacker.getId() + " " + "Pos" + getMinionPosition(attacker.getId()));
            BattleLog log = new BattleLog();
            log.setNoAttackLog(attacker.getId(), getMinionPosition(attacker.getId()));
            battleLogs.add(log);
        } else {
            validatePosOnBoard(attackPosition);
            if(getContentFromPosition(attackPosition) == null) {
                throw new InvalidPositionException("Not a valid position on board or no defender found");
            }else {
                MinionBase defender = getContentFromPosition(attackPosition);
                int damage = (defender.getDefense() - attacker.getAttack());
                defender.changeHealth(damage);
                System.out.println("-- ATTACK: attacker " + attacker.getId() + getMinionPosition(attacker.getId()));
                System.out.println("           defender " + defender.getId() + getMinionPosition(defender.getId()));
                System.out.println("           attack " + attacker.getAttack() + ", defence " + defender.getDefense() + ", damage " + damage);
                BattleLog log = new BattleLog();
                log.setAttackLog(attacker.getId(), getMinionPosition(attacker.getId()), defender.getId(), getMinionPosition(defender.getId()), damage);
                battleLogs.add(log);
                if (defender.getHealth() <= 0) {
                    removeMinionFromBoard(defender.getId());
                    System.out.println("-- DEATH: minion " + attacker.getId());
                    BattleLog deathLog = new BattleLog();
                    deathLog.setDeathLog(defender.getId());
                    battleLogs.add(deathLog);
                }
            }
        }
    }

    //ONLY NEEDED WITHOUT GUI
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < boardArray2d.length; row++) {
            for(int col = 0; col < boardArray2d[row].length; col++) {
                sb.append("|");
                if(boardArray2d[row][col] == null) {
                    sb.append(row + "" + col);
                }else {
                    sb.append(" " + boardArray2d[row][col].toString());
                }
                sb.append("|");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    //ONLY NEED WITHOUT GUI
    public void printBoard() {
        System.out.print(toString());
    }

}
