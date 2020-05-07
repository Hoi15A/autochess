package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;
import javafx.geometry.Pos;

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
        if(isValidPosition(pos) && boardArray2d[pos.getY()][pos.getX()] == null && isMinionNotOnBoard(minion.getId())) {
            boardArray2d[pos.getY()][pos.getX()] = minion;
        } else {
            throw new IllegalArgumentException("Not a valid placement. Off board, occupied or minion already on board");
        }
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
        for(MinionBase minion : activeMinions) {
            minion.printInfo();
        }

        int loopCounter = 0;
        while(checkEachHeroActiveMinions(activeMinions) && loopCounter < MAXBATTLELOOPS){
            for(Iterator<MinionBase> it = activeMinions.iterator(); it.hasNext(); ) {
                MinionBase minion = it.next();
                System.out.print("Current : ");
                minion.printInfo();
                moveMinion(minion, MoveStrategy.StrategyType.getStrategyFromType(minion.getStrategyType()).move(boardArray2d, getMinionPosition(minion.getId()), minion.getMovementRange()));
                printBoard();
            }
            loopCounter++;
        }
    }

    private void moveMinion(MinionBase minion, PositionVector moveVector) {
        PositionVector currentPos = getMinionPosition(minion.getId());
        removeMinionFromBoard(minion.getId());
        setMinionOnBoard(minion, PositionVector.add(currentPos, moveVector));
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
        //return "hallo2";
    }

    public void printBoard() {
        System.out.print(toString());
    }

}
