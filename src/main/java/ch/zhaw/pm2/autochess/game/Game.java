package ch.zhaw.pm2.autochess.game;

import ch.zhaw.pm2.autochess.board.BattleLog;
import ch.zhaw.pm2.autochess.board.BoardManager;
import ch.zhaw.pm2.autochess.board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.hero.HeroBase;
import ch.zhaw.pm2.autochess.hero.exceptions.IllegalFundsReductionException;
import ch.zhaw.pm2.autochess.hero.exceptions.IllegalValueException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidHeroAttributeException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Game {
    private static final int NO_WINNER = -1;

    private final ArrayList<HeroBase> heroArrayList;
    private BoardManager boardManager;

    /**
     * Constructor for Game class. Initializes two hoeres of the given type and adds them the hero list.
     * @param heroTypePlayer1 heroType for hero1
     * @param heroTypePlayer2 heroType for hero2
     * @throws IllegalGameStateException
     */
    public Game(Config.HeroType heroTypePlayer1, Config.HeroType heroTypePlayer2) throws IllegalGameStateException {
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
        addHero(heroTypePlayer1, Config.HERO_ID_1);
        addHero(heroTypePlayer2, Config.HERO_ID_2);

    }

    //ONLY USED FOR TESTING
    public List<HeroBase> getHeroList() {
        return heroArrayList;
    }

    /**
     * Getter method for the int value Hero ID winner of the game. If no winner returns -1.
     * @return int hero ID value or -1 if no winner
     */
    public int getWinner() {
        int winner = NO_WINNER;
        if(checkWinner()) {
            for (HeroBase hero : heroArrayList) {
                if (hero.getHealth() > 0) {
                    winner = hero.getId();
                }
            }
        }
        return winner;
    }

    private boolean checkWinner() {
        boolean winner = false;
        for(HeroBase hero : heroArrayList) {
            if(hero.getHealth() <= 0) {
                winner = true;
            }
        }
        return winner;
    }

    //*******************************
    //Hero methods
    //*******************************

    private void validateHeroId(int heroId) throws IllegalGameStateException {
        boolean searching = true;
        for(HeroBase hero : heroArrayList) {
            if(hero.getId() == heroId) {
                searching = false;
            }
        }
        if(searching) {
            throw new IllegalGameStateException("Invalid hero ID: " + heroId);
        }
    }

    private void addHero(Config.HeroType heroType, int heroId) throws IllegalGameStateException{
        try {
            heroArrayList.add(HeroBase.newHeroFromType(heroType, heroId));
        } catch (InvalidHeroTypeException | InvalidHeroAttributeException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    private HeroBase getHero(int heroId) throws IllegalGameStateException {
        validateHeroId(heroId);
        HeroBase foundHero = null;
        for(HeroBase hero : heroArrayList) {
            if(hero.getId() == heroId) {
                foundHero = hero;
            }
        }
        Objects.requireNonNull(foundHero, "Hero corresponding to valid ID not found");
        return foundHero;
    }

    /**
     * Getter method for funds attribute of hero matching given int ID value.
     * @param heroId int ID value
     * @return int funds value
     * @throws IllegalGameStateException thrown if no hero matches the given ID
     */
    public int getHeroFunds(int heroId) throws IllegalGameStateException {
        return getHero(heroId).getFunds();
    }

    /**
     * Getter method for health attribute of hero matching given int ID value.
     * @param heroId int ID Value
     * @return int health value
     * @throws IllegalGameStateException thrown if no hero matches the given ID
     */
    public int getHeroHealth(int heroId) throws IllegalGameStateException {
        return getHero(heroId).getHealth();

    }

    /**
     * Method to activate hero ability. Cannot only be activated once.
     * @param heroId int ID value
     * @throws IllegalGameStateException thrown if attempted modification not allowed or hero ID value not found
     */
    public void doHeroAbility(int heroId) throws IllegalGameStateException {
        try {
            getHero(heroId).doAbility();
        } catch(InvalidMinionAttributeModifierException | IllegalValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    /**
     * Getter method for hero ability status boolean. If ability has been activated, status is false;
     * @param heroId int ID value
     * @return boolean of ability status
     * @throws IllegalGameStateException thrown if no hero matches the given ID
     */
    public boolean getHeroAbilityStatus(int heroId) throws IllegalGameStateException {
        return getHero(heroId).isAbilityAvailable();
    }

    //***********************
    //Minion methods
    //***********************

    /**
     * Method to let hero matching given ID attempt to buy a minion of the given Type. Fails if type is invalid,
     * price value invalid or funds reduction not allowed.
     * If successful, adds minion of said type to hero minion list.
     * @param heroId int ID value
     * @param minionType {@link Config.MinionType} to buy
     * @throws IllegalFundsReductionException thrown if funds not available
     * @throws IllegalGameStateException thrown if internal values incorrect
     */
    public void buyMinion(int heroId, Config.MinionType minionType) throws IllegalFundsReductionException, IllegalGameStateException {
        try {
            HeroBase hero = getHero(heroId);
            hero.buyMinion(minionType);
        } catch (IllegalValueException | InvalidMinionTypeException | InvalidMinionAttributeException | InvalidMinionAttributeModifierException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    /**
     * Method to let hero matching given ID attempt to sell minion matching given ID.
     * If successful, removes minion from hero minion list and increases funds.
     * @param heroId
     * @param minionId
     * @throws IllegalGameStateException
     * @throws InvalidMinionIDException
     */
    public void sellMinion(int heroId, int minionId) throws IllegalGameStateException, InvalidMinionIDException {
        try {
            HeroBase hero = getHero(heroId);
            hero.sellMinion(minionId);
        } catch (IllegalValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    /**
     * Method to place minion matching given ID of hero matching given ID on the board at the given position.
     * @param heroId int ID value
     * @param minionId int ID value
     * @param pos PositionVector of placement
     * @throws InvalidPositionException thrown if position is not valid
     * @throws IllegalGameStateException thrown if hero ID value matches no hero
     */
    public void placeMinionOnBoard(int heroId, int minionId, PositionVector pos) throws InvalidPositionException, IllegalGameStateException {
        try {
            boardManager.placeMinionOnBoard(getHero(heroId).getMinion(minionId), pos);
        } catch (InvalidMinionIDException e) {
            throw new IllegalGameStateException(e.getMessage());
        }

    }

    /**
     * Getter method for minion info String.
     * @param heroId int ID value
     * @param minionId int ID value
     * @return String info of minion
     * @throws IllegalGameStateException thrown if hero ID value matches no hero
     * @throws InvalidMinionIDException thrown if minion ID value matches no minion
     */
    public String getMinionInfoAsString(int heroId, int minionId) throws IllegalGameStateException, InvalidMinionIDException {
            return getHero(heroId).getMinionInfoAsString(minionId);
    }

    /**
     * Getter method for list of int ID values of the hero matching the given ID.
     * @param heroId int ID value
     * @return Integer Set of all int minion IDs
     * @throws IllegalGameStateException
     */
    public Set<Integer> getAllMinionIds(int heroId) throws IllegalGameStateException {
        return getHero(heroId).getAllMinionIds();
    }

    /**
     * Getter method for minion type of minion matching given ID value from hero matching given ID value.
     * @param heroId int ID value
     * @param minionId int ID value
     * @return {@link Config.MinionType} of minion
     * @throws IllegalGameStateException
     */
    public Config.MinionType getMinionType(int heroId, int minionId) throws IllegalGameStateException {
        try {
            return getHero(heroId).getMinionType(minionId);
        } catch (InvalidMinionIDException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    //*******************************
    //BoardManager methods
    //*******************************

    /**
     * Method to remove the minion matching the given int ID value from the baord.
     * @param minionId int ID value
     * @throws MinionNotOnBoardException thrown if minion not on board
     */
    public void removeMinionFromBoard(int minionId) throws MinionNotOnBoardException {
        boardManager.removeMinionFromBoard(minionId);
    }

    /**
     * Getter method for minion Position from board.
     * @param minionId int ID value
     * @return PositonVector of minion
     * @throws MinionNotOnBoardException thrown if minion not on board
     */
    public PositionVector getMinionPos(int minionId) throws MinionNotOnBoardException {
        return boardManager.getMinionPosition(minionId);
    }

    /**
     * Method to do a battle with the minions placed on the board. After battle calculates damage,
     * distributes funds, resets minion health and clears the board of minion.
     * @throws IllegalGameStateException thrown if internal values invalid
     */
    public void doBattle() throws IllegalGameStateException {
        try {
            boardManager.clearBattleLogs();
            boardManager.doBattle();
            doHeroDamage();
            distributeFunds();
            resetAllMinionHealth();
            boardManager.clearBoard();
        }catch(MinionNotOnBoardException | InvalidPositionException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    private void doHeroDamage() throws IllegalGameStateException {
        try {
            getHero(Config.HERO_ID_1).decreaseHealth(boardManager.getNumberOfMinionsPerHero(Config.HERO_ID_2));
            getHero(Config.HERO_ID_2).decreaseHealth(boardManager.getNumberOfMinionsPerHero(Config.HERO_ID_1));
        }catch (IllegalValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    private void distributeFunds() throws IllegalGameStateException {
        for(HeroBase hero : heroArrayList) {
            try {
                hero.increaseFunds(30);
            } catch (IllegalValueException e) {
                throw new IllegalGameStateException(e.getMessage());
            }
        }
    }

    private void resetAllMinionHealth() {
        for(HeroBase hero: heroArrayList) {
            hero.resetAllMinionHealth();
        }
    }

    /**
     * Getter method for list of BattleLog objects generated form battle.
     * @return List of BattleLog objects
     */
    public List<BattleLog> getBattleLog() {
        return boardManager.getBattleLogs();
    }

}
