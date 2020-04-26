package ch.zhaw.pm2.autochess;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private Board board;
    private GamePhase currentGamePhase;
    private PhaseStatus phaseCompletionStatus;


    /**
     * Constructor for Game object. Initializes board and players. Sets currentGamePhase to
     * GAME_START.
     * @param numberOfPlayers int representing the number of players
     * @throws IllegalArgumentException thrown if number of players invalid
     */
    public Game(int numberOfPlayers) throws IllegalArgumentException{

    }

    private void initBoard() {

    }

    private void initPlayerArrayList(int numberOfPlayers) throws IllegalArgumentException{

    }

    /**
     * Getter for Player specific Player object
     * @param playerId String to match with playerID attribute
     * @return Player object whose playerID attribute matches the given String
     * @throws IllegalArgumentException
     */
    public Player getPlayer(String playerId) throws IllegalArgumentException{
        return null;
    }

    /**
     * Getter method for the Board object attribute
     * @return Board object
     */
    public Board getBoard() {
        return null;
    }

    /**
     * Place the given Minion object on the board object
     * @param minion Minion object to be placed on board
     * @throws IllegalArgumentException thrown if values of given Minion object not valid for board
     */
    public void setMinionOnBoard(Minion minion) throws IllegalArgumentException{

    }

    /**
     * Enum of possible game phases
     */
    public enum GamePhase {
        /* GAME_START_PHASE, BUY_PHASE, PLACEMENT_PHASE, BATTLE_PHASE, GAME_OVER_PHASE */
    }

    public enum PhaseStatus {
        /* PHASE_START, PHASE_RUNNING, PHASE_END */
    }

    /**
     * Getter method for currentGamePhase attribute
     * @return GamePhase object assigned to currentGamePhase
     */
    public GamePhase getCurrentGamePhase() {
        return null;
    }

    /**
     * Getter method for phaseCompletionStatus attribute
     * @return boolean value of phaseCompletionStatus
     */
    public boolean getPhaseCompletionStatus() {
        return false;
    }

    private void togglePhaseCompletionStatus() {

    }

    private void setCurrentGamePhase() {

    }

    /**
     * Change the current game phase to the next logical phase if current phase is completed.
     * Sets phase set
     * @throws IllegalStateException thrown if current phase no yet complete
     */
    public void nextGamePhase() throws IllegalStateException{

    }

    /**
     * Start the execution of the current game phase if phase status set to PHASE_START
     */
    public void executeCurrentGamePhase() {
        //switch case
    }
}
