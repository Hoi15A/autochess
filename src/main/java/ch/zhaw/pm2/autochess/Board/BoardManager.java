package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.strategy.MoveStrategy;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BoardManager {

    private static final int HEIGHT = 8;
    private static final int WIDTH =8;
    private static final int MAXBATTLELOOPS = 10;
    private MinionBase[][] boardArray2d = new MinionBase[HEIGHT][WIDTH];

    public BoardManager() {
    }

    public MinionBase[][] getBoardArray2d() {
        return boardArray2d;
    }

    public void setMinionOnBoard(MinionBase minion, PositionVector pos) {
        if(isValidPosition(pos) && getContentFromPosition(pos) == null && isMinionNotOnBoard(minion.getId())) {
            boardArray2d[pos.getY()][pos.getX()] = minion;
        } else {
            throw new IllegalArgumentException("Not a valid placement. Off board, occupied or minion already on board");
        }
    }

    private MinionBase getContentFromPosition(PositionVector pos) {
        return boardArray2d[pos.getY()][pos.getX()];
    }

    private boolean isValidPosition(PositionVector pos) {
        if(pos != null) {
            if(pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < HEIGHT && pos.getY() < WIDTH ) {
                return true;
            }
        }
        return false;
    }

    private boolean isMinionNotOnBoard(int minionId) {
        try {
            getMinionPosition(minionId);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    public void removeMinionFromBoard(int minionId) {
        PositionVector pos = getMinionPosition(minionId);
        boardArray2d[pos.getY()][pos.getX()] = null;
    }

    public PositionVector getMinionPosition(int minionId) {
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                MinionBase minion = boardArray2d[i][j];
                if(minion != null && minion.getId() == minionId) {
                    return new PositionVector(j, i);
                }
            }
        }
        throw new IllegalArgumentException("No minion on board has that ID");
    }

    public ArrayList<MinionBase> getAllMinionsFromBoard() {
        ArrayList<MinionBase> listActiveMinions = new ArrayList<>();
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                MinionBase minion = boardArray2d[i][j];
                if(minion != null) {
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
                if(minion != null && minion.getHeroId() == heroId) {
                    listActiveMinions.add(minion);
                }
            }
        }
        return listActiveMinions;
    }

    public void doBattle() {
        ArrayList<MinionBase> activeMinions = getAllMinionsFromBoard();
        //todo: sort by agility;
        
        System.out.println("All active minions");
        for(MinionBase minion : activeMinions) {
            minion.printInfo();
        }
        System.out.println(" ");

        int loopCounter = 0;
        while(checkEachHeroActiveMinions(activeMinions) && loopCounter < MAXBATTLELOOPS){
            for(Iterator<MinionBase> it = activeMinions.iterator(); it.hasNext(); ) {
                MinionBase minion = it.next();
                if(minion.getHealth() > 0) {
                    System.out.print("Current : ");
                    minion.printInfo();
                    moveMinion(minion);
                    attackMinion(minion);
                } else {
                    it.remove();
                }
            }
            loopCounter++;
        }
    }

    private void moveMinion(MinionBase minion) throws IllegalArgumentException{
        //todo: check valid move? i.e still on board? or assumption move is correct?
        PositionVector currentPos = getMinionPosition(minion.getId());
        PositionVector moveVector = minion.move(boardArray2d, currentPos);
        if(moveVector != null) {
            if(isValidPosition(moveVector)) {
                removeMinionFromBoard(minion.getId());
                PositionVector newPos = PositionVector.add(currentPos, moveVector);
                setMinionOnBoard(minion, newPos);
                System.out.println("-- MOVED: minion " + minion.getId() + " " + "From: " + currentPos + " To: " + newPos);
            }else {
                throw new IllegalArgumentException("Not a valid position");
            }
        } else {
            System.out.println("-- NO_MOVE: minion " + minion.getId() + " " + "Pos" + currentPos);
        }
    }

    private void attackMinion(MinionBase attacker) {
        //todo: check valid attack vector? i.e still on board? or assumption move is correct?
        PositionVector currentPos = getMinionPosition(attacker.getId());
        PositionVector attackVector = attacker.attack(boardArray2d, currentPos);
        if (attackVector != null) {
            if (isValidPosition(attackVector) && getContentFromPosition(attackVector) != null) {
                MinionBase defender = getContentFromPosition(attackVector);
                int damage = (defender.getDefense() - attacker.getAttack());
                defender.changeHealth(damage);
                System.out.println("-- ATTACK: attacker " + attacker.getId() + getMinionPosition(attacker.getId()));
                System.out.println("           defender " + defender.getId() + getMinionPosition(defender.getId()));
                System.out.println("           attack " + attacker.getAttack() + ", defence " + defender.getDefense() + ", damage " + damage);
                if (defender.getHealth() <= 0) {
                    removeMinionFromBoard(defender.getId());
                    System.out.println("-- DEATH: minion " + attacker.getId());
                }
            } else {
                throw new IllegalArgumentException("Not a valid position on board or no defender found");
            }
        }else {
            System.out.println("-- NO_ATTACK: minion " + attacker.getId() + " " + "Pos" + getMinionPosition(attacker.getId()));
        }
    }

    private boolean checkEachHeroActiveMinions(ArrayList<MinionBase> activeMinions) {
        Set<Integer> heroSet = new HashSet<>();
        for(MinionBase minion : activeMinions) {
            heroSet.add(minion.getHeroId());
        }
        if(heroSet.size() > 1) {
            return true;
        }
        return false;
    }


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

    public void printBoard() {
        System.out.print(toString());
    }

}
