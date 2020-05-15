package ch.zhaw.pm2.autochess.board;

import ch.zhaw.pm2.autochess.board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.minion.MinionBase;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class BoardManager {

    private MinionBase[][] boardArray2d = new MinionBase[Config.BOARD_HEIGHT][Config.BOARD_WIDTH];
    private List<BattleLog> battleLogs = new ArrayList<>();

    //ONLY FOR TESTING
    public MinionBase[][] getBoardArray2d() {
        return boardArray2d;
    }

    /**
     * Clear the board of all placed {@link MinionBase} objects
     * Needed after Battle.
     */
    public void clearBoard() {
        for(int i = 0; i < boardArray2d.length; i++) {
            for(int j = 0; j < boardArray2d.length; j++) {
                boardArray2d[i][j] = null;
            }
        }
    }

    /**
     * Clear the battle log entries.
     * Sets all entries to null.
     */
    public void clearBattleLogs() {
        battleLogs.clear();
    }

    /**
     * Getter method for list holding generated {@link BattleLog} objects.
     * @return List of BattleLog objects
     */
    public List<BattleLog> getBattleLogs() {
        return battleLogs;
    }

    private MinionBase getContentFromPosition(PositionVector position) throws InvalidPositionException {
        validatePosOnBoard(position);
        return boardArray2d[position.getY()][position.getX()];
    }

    private void validatePosOnBoard(PositionVector position) throws InvalidPositionException {
        if(position == null || position.getX() < 0 || position.getY() < 0 || position.getX() >= Config.BOARD_WIDTH || position.getY() >= Config.BOARD_HEIGHT) {
            throw new InvalidPositionException("Invalid position: " + position + ". Valid between: " + (Config.BOARD_WIDTH-1) + " and " + (Config.BOARD_HEIGHT-1));
        }
    }

    private void validatePosEmpty(PositionVector position) throws InvalidPositionException {
        validatePosOnBoard(position);
        MinionBase minion = getContentFromPosition(position);
        if(Objects.nonNull(minion)) {
            throw new InvalidPositionException("Invalid position: " + position + ". Occupied by: " + minion);
        }
    }

    private void validatePosZone(PositionVector position, int heroId) throws InvalidPositionException, IllegalGameStateException {
        if(heroId == Config.HERO_ID_1 || heroId == Config.HERO_ID_2) {
            if (heroId == Config.HERO_ID_1 && position.getY() > Config.PLACE_ZONE_HEIGHT) {
                throw new InvalidPositionException("Invalid position: " + position + ". Not in this hero's placement zone ");
            } else if (heroId == Config.HERO_ID_2 && position.getY() < Config.BOARD_HEIGHT - Config.PLACE_ZONE_HEIGHT) {
                throw new InvalidPositionException("Invalid position: " + position + ". Not in this hero's placement zone ");
            }
        } else {
            throw new IllegalGameStateException("Invalid hero ID: " + heroId + ". Not a config value");
        }
    }

    private void validateNumberOfMinions(int heroId) throws IllegalGameStateException {
        if(heroId == Config.HERO_ID_1 || heroId == Config.HERO_ID_2) {
            if (heroId == Config.HERO_ID_1 && getNumberOfMinionsPerHero(Config.HERO_ID_1) >= Config.MAX_MINIONS_ON_BOARD ) {
                throw new IllegalGameStateException("Invalid placement: Already placed " + getNumberOfMinionsPerHero(Config.HERO_ID_1) + " of " + Config.MAX_MINIONS_ON_BOARD + " minions");
            } else if (heroId == Config.HERO_ID_2 && getNumberOfMinionsPerHero(Config.HERO_ID_2) >= Config.MAX_MINIONS_ON_BOARD ) {
                throw new IllegalGameStateException("Invalid placement: Already placed " + getNumberOfMinionsPerHero(Config.HERO_ID_2) + " of " + Config.MAX_MINIONS_ON_BOARD + " minions");
            }
        } else {
            throw new IllegalGameStateException("Invalid hero ID: " + heroId + ". Not a config value");
        }
    }

    /**
     * Method to place a {@link MinionBase} on the board at the given {@link PositionVector}.
     * Successful if pos on board, vacant and in placement zone.
     * @param minion Minion to be placed
     * @param pos PositionVector of location to place minion
     * @throws IllegalGameStateException thrown if pos not vacant, off board, not in placement zone or too many minions on board
     * @throws InvalidPositionException thrown if minion hero not a valid config value
     */
    public void placeMinionOnBoard(MinionBase minion, PositionVector pos) throws IllegalGameStateException, InvalidPositionException {
        Objects.requireNonNull(minion, "Minion to place is null");
        validatePosOnBoard(pos);
        validatePosZone(pos, minion.getHeroId());
        validateNumberOfMinions(minion.getHeroId());
        setMinionOnBoard(minion, pos);
    }

    private void setMinionOnBoard(MinionBase minion, PositionVector pos) throws InvalidPositionException {
        Objects.requireNonNull(minion, "Minion to place is null");
        validatePosOnBoard(pos);
        validatePosEmpty(pos);
        try {
            getMinionPosition(minion.getId());
            throw new InvalidPositionException("Invalid placement (MinionID:" + minion.getId() + "). Already placed.");
        }catch (MinionNotOnBoardException e) {
            boardArray2d[pos.getY()][pos.getX()] = minion;
        }
    }

    /**
     * Remove a minion from the board.
     * @param minionId int ID value of minion to be removed
     * @throws MinionNotOnBoardException thrown if no minion on board matches the given ID
     */
    public void removeMinionFromBoard(int minionId) throws MinionNotOnBoardException {
        PositionVector pos = getMinionPosition(minionId);
        boardArray2d[pos.getY()][pos.getX()] = null;
    }

    /**
     * Getter method for the {@link PositionVector} of the minion matching the given int ID value.
     * @param minionId int ID value of minion
     * @return PositionVector of minion matching ID
     * @throws MinionNotOnBoardException thrown if no minion on board matches the given ID
     */
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

    /**
     * Getter method for all placed minions on board
     * @return List of {@link MinionBase} objects
     */
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

    /**
     * Getter method for all placed minions matching the specified int hero ID value.
     * @param heroId int hero ID value to match
     * @return List of {@link MinionBase} objects
     */
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

    /**
     * Getter method for int number of placed minions matching the specified int hero ID value.
     * @param heroId int hero ID value to match
     * @return List of {@link MinionBase} objects
     */
    public int getNumberOfMinionsPerHero(int heroId) {
        return getSpecHeroMinionsFromBoard(heroId).size();
    }

    private boolean checkEachHeroActiveMinions() {
        boolean isGreaterZeroMinions = false;
        if(getNumberOfMinionsPerHero(Config.HERO_ID_1) > 0 && getNumberOfMinionsPerHero(Config.HERO_ID_2) > 0) {
            isGreaterZeroMinions = true;
        }
        return isGreaterZeroMinions;
    }

    /**
     * Method to execute a battle with the current board state. Creates a list of all minions on the board
     * to iterate through. Each minion is called on to make a move and an attack. If a minion death event occurs,
     * the dead minion is immediately removed form the board and removed from the list upon their next turn.
     * Each event (Move, No Move, Attack, etc) are logged as battleLog objects and stored for later usage.
     * @throws MinionNotOnBoardException thrown if the listed minion is not on the board
     * @throws InvalidPositionException thrown if the given position to set minion is not valid
     */
    public void doBattle() throws MinionNotOnBoardException, InvalidPositionException {
        ArrayList<MinionBase> activeMinions = getAllMinionsOnBoard();
        //todo: sort by agility
        //todo: remove prints
        /*
        System.out.println("All active minions");
        for(MinionBase minion : activeMinions) {
            minion.printInfo();
        }
        System.out.println(" ");
         */
        int loopCounter = 0;
        while(checkEachHeroActiveMinions() && loopCounter < Config.MAX_BATTLE_LOOPS){
            for(Iterator<MinionBase> it = activeMinions.iterator(); it.hasNext(); ) {
                MinionBase minion = it.next();
                if(minion.getHealth() > 0) {
                    //System.out.print("Current : ");
                    //minion.printInfo();
                    minionDoMove(minion);
                    minionDoAttack(minion);
                } else {
                    //System.out.println("Removed dead minion from list");
                    it.remove();
                }
                printBoard();
            }
            loopCounter++;
        }
    }

    private void minionDoMove(MinionBase minion) throws MinionNotOnBoardException, InvalidPositionException {
        //todo: remove prints
        PositionVector currentPos = getMinionPosition(minion.getId());
        PositionVector movePosition = minion.move(boardArray2d, currentPos);
        BattleLog log = new BattleLog();
        if(movePosition == null) {
            //System.out.println("-- NO_MOVE: minion " + minion.getId() + " " + "Pos" + currentPos);
            log.setNoMoveLog(minion.getId(), currentPos);

        } else {
            validatePosOnBoard(movePosition);
            validatePosEmpty(movePosition);
            removeMinionFromBoard(minion.getId());
            setMinionOnBoard(minion, movePosition);
            log.setMoveLog(minion.getId(), currentPos, movePosition, minion.getType());
            //System.out.println("-- MOVED: minion " + minion.getId() + " " + "From: " + currentPos + " To: " + movePosition);
        }
        battleLogs.add(log);
    }

    private void minionDoAttack(MinionBase attacker) throws MinionNotOnBoardException, InvalidPositionException {
        //todo: remove prints
        PositionVector currentPos = getMinionPosition(attacker.getId());
        PositionVector attackPosition = attacker.attack(boardArray2d, currentPos);

        if (attackPosition == null) {
            //System.out.println("-- NO_ATTACK: minion " + attacker.getId() + " " + "Pos" + getMinionPosition(attacker.getId()));
            BattleLog log = new BattleLog();
            log.setNoAttackLog(attacker.getId(), getMinionPosition(attacker.getId()));
            battleLogs.add(log);
        } else {
            validatePosOnBoard(attackPosition);
            if(getContentFromPosition(attackPosition) == null) {
                throw new InvalidPositionException("Not a valid position on board or no defender found");
            }else {
                MinionBase defender = getContentFromPosition(attackPosition);

                int damage = (attacker.getAttack() - defender.getDefense());
                defender.decreaseHealth(damage);
                //System.out.println("-- ATTACK: attacker " + attacker.getId() + getMinionPosition(attacker.getId()));
                //System.out.println("           defender " + defender.getId() + getMinionPosition(defender.getId()));
                //System.out.println("           attack " + attacker.getAttack() + ", defence " + defender.getDefense() + ", damage " + damage);

                BattleLog log = new BattleLog();
                log.setAttackLog(attacker.getId(), getMinionPosition(attacker.getId()), defender.getId(), getMinionPosition(defender.getId()), damage);
                battleLogs.add(log);
                if (defender.getHealth() <= 0) {
                    removeMinionFromBoard(defender.getId());
                    //System.out.println("-- DEATH: minion " + defender.getId());
                    BattleLog deathLog = new BattleLog();
                    deathLog.setDeathLog(defender.getId(), getMinionPosition(defender.getId()));
                    battleLogs.add(deathLog);
                    removeMinionFromBoard(defender.getId());
                    System.out.println("-- DEATH: minion " + defender.getId());
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
