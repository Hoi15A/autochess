package ch.zhaw.pm2.autochess;

import java.util.ArrayList;

public class Game {
    private ArrayList<PlayerMOCK> playerArrayList = new ArrayList<>();
    private BoardMOCK board;
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
    public PlayerMOCK getPlayer(String playerId) throws IllegalArgumentException{
        PlayerMOCK foundPlayer = null;
        for(PlayerMOCK player : playerArrayList) {
            if(player.getPlayerId().equals(playerId)) {
                foundPlayer = player;
            }
        }
        if(foundPlayer.equals(null)) {
            throw new IllegalArgumentException("No player-ID matches the given parameter");
        }
        return foundPlayer;
    }

    /**
     * Getter method for the Board object attribute
     * @return Board object
     */
    public BoardMOCK getBoard() {
        return board;
    }

    /**
     * Place the given Minion object on the board object
     * @param minion Minion object to be placed on board
     * @throws IllegalArgumentException thrown if values of given Minion object not valid for board
     */
    public void passMinionToBoard(MinionMOCK minion) throws IllegalArgumentException{
        board.processMinion(minion);
    }

    /**
     * Enum of possible game phases
     */
    public enum GamePhase {
        GAME_START_PHASE(0), ROUND_START_PHASE(1), BUY_PHASE(2), PLACEMENT_PHASE(3), BATTLE_PHASE(4),
        ROUND_END_PHASE(5), GAME_OVER_PHASE(6);

        private final int phaseCode;

        private GamePhase(int phaseCode) {
            this.phaseCode = phaseCode;
        }

        public  int getPhaseCode() {
            return phaseCode;
        }

        public static GamePhase getPhaseFromCode(int phaseCode){
            for(GamePhase phase : values()){
                if( phase.getPhaseCode() == phaseCode){
                    return phase;
                }
            }
            return null;
        }
    }

    public enum PhaseStatus {
        PHASE_START(0), PHASE_RUNNING(1), PHASE_END(2);

        private final int statusCode;

        private PhaseStatus(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    /**
     * Getter method for currentGamePhase attribute
     * @return GamePhase object assigned to currentGamePhase
     */
    public GamePhase getCurrentGamePhase() {
        return currentGamePhase;
    }

    /**
     * Getter method for phaseCompletionStatus attribute
     * @return boolean value of phaseCompletionStatus
     */
    public PhaseStatus getPhaseCompletionStatus() {
        return phaseCompletionStatus;
    }

    private void setPhaseCompletionStatus(PhaseStatus status)  {
        if(status == null) {
            throw new IllegalArgumentException("PhaseStatus null received");
        } else {
            phaseCompletionStatus = status;
        }
    }

    private void setCurrentGamePhase(GamePhase phase) {
        if(phase == null) {
            throw new IllegalArgumentException("GamePhase null received");
        } else {
            currentGamePhase = phase;
        }
    }

    /**
     * Change the current game phase to the next logical phase if current phase is completed.
     * Sets phase set
     * @throws IllegalStateException thrown if current phase no yet complete
     */
    public void nextGamePhase() throws IllegalStateException{
        if(phaseCompletionStatus.equals(PhaseStatus.PHASE_END)) {
            if(currentGamePhase.equals(GamePhase.GAME_OVER_PHASE)) {
                throw new IllegalStateException("Game over phase already reached");
            } else if(currentGamePhase.equals(GamePhase.ROUND_END_PHASE) && !checkWinner()) {
                setCurrentGamePhase(GamePhase.ROUND_START_PHASE);
            } else {
                setCurrentGamePhase(GamePhase.getPhaseFromCode(currentGamePhase.getPhaseCode() + 1));
            }
            setPhaseCompletionStatus(PhaseStatus.PHASE_START);
        }else {
            throw new IllegalStateException("Current phase not complete. PhaseState: " + phaseCompletionStatus);
        }
    }

    private boolean checkWinner() {
        return false;
    }

    /**
     * Start the execution of the current game phase if phase status set to PHASE_START
     */
    public void executeCurrentGamePhase() {
        if(phaseCompletionStatus.equals(PhaseStatus.PHASE_START)) {
            phaseCompletionStatus = PhaseStatus.PHASE_RUNNING;
            switch (currentGamePhase) {
                case GAME_START_PHASE:
                    executeGameStartPhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                case ROUND_START_PHASE:
                    executeRoundStartPhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                case BUY_PHASE:
                    executeBuyPhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                case PLACEMENT_PHASE:
                    executePlacementPhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                case BATTLE_PHASE:
                    executeBattlePhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                case ROUND_END_PHASE:
                    executeRoundEndPhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                case GAME_OVER_PHASE:
                    executeGameOverPhase();
                    phaseCompletionStatus = PhaseStatus.PHASE_END;
                    break;
                default:
                    throw new IllegalStateException("Not a valid GamePhase");
            }
        } else {
            throw new IllegalStateException("Current phase not completed");
        }
    }

    private void executeGameStartPhase() {

    }

    private void executeRoundStartPhase() {

    }

    private void executeBuyPhase() {

    }

    private void executePlacementPhase() {

    }

    private void executeBattlePhase() {

    }

    private void executeRoundEndPhase() {

    }

    private void executeGameOverPhase() {

    }
}
