package ch.zhaw.pm2.autochess.Game;

import ch.zhaw.pm2.autochess.Board.BoardManager;
import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Board.exceptions.MinionNotOnBoardException;
import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Game.exceptions.InvalidIdentifierException;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalFundsStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class Game {
    private static final int NO_WINNER = -1;

    private final ArrayList<HeroBase> heroArrayList;
    private BoardManager boardManager;

    public Game(Config.HeroType heroTypePlayer1, Config.HeroType heroTypePlayer2) throws IllegalGameStateException {
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
        addHero(heroTypePlayer1);
        addHero(heroTypePlayer2);

    }

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

    private void validateHeroId(int heroId) throws InvalidIdentifierException {
        boolean searching = true;
        for(HeroBase hero : heroArrayList) {
            if(hero.getId() == heroId) {
                searching = false;
            }
        }
        if(searching) {
            throw new InvalidIdentifierException("Invalid hero ID: " + heroId);
        }
    }

    private void addHero(Config.HeroType heroType) throws IllegalGameStateException{
        try {
            heroArrayList.add(HeroBase.getHeroFromType(heroType));
        } catch (InvalidHeroTypeException | IllegalHeroValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    private HeroBase getHero(int heroId) throws InvalidIdentifierException {
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

    public int getHeroFunds(int heroId) throws InvalidIdentifierException {
        return getHero(heroId).getFunds();

    }

    public int getHeroHealth(int heroId) throws InvalidIdentifierException {
        return getHero(heroId).getHealth();

    }

    public void doHeroAbility(int heroId) throws IllegalGameStateException, InvalidIdentifierException {
        try {
            getHero(heroId).doAbility();
        } catch(InvalidMinionAttributeModifierException | IllegalHeroValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    public boolean getHeroAbilityStatus(int heroId) throws InvalidIdentifierException {
        return getHero(heroId).isAbilityAvailable();
    }

    //***********************
    //Minion methods
    //***********************

    public void buyMinion(int heroId, Config.MinionType minionType) throws InvalidIdentifierException,IllegalFundsStateException, IllegalGameStateException {
        try {
            HeroBase hero = getHero(heroId);
            hero.buyMinion(minionType);
        } catch (IllegalHeroValueException | MinionException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    public void sellMinion(int heroId, int minionId) throws InvalidIdentifierException, IllegalGameStateException{
        try {
            HeroBase hero = getHero(heroId);
            hero.sellMinion(minionId);
        } catch (InvalidMinionIDException e) {
            throw new InvalidIdentifierException(e.getMessage());
        } catch (IllegalHeroValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    public void placeMinionOnBoard(int heroId, int minionId, PositionVector pos) throws InvalidIdentifierException, InvalidPositionException {
        try {
            boardManager.setMinionOnBoard(getHero(heroId).getMinion(minionId), pos);
        }catch (InvalidMinionIDException e) {
            throw new InvalidIdentifierException(e.getMessage());
        }
    }

    public String getMinionInfoAsString(int heroId, int minionId) throws InvalidIdentifierException {
        try {
            return getHero(heroId).getMinionInfoAsString(minionId);
        } catch (InvalidMinionIDException e) {
           throw new InvalidIdentifierException(e.getMessage());
        }
    }

    public Set<Integer> getAllMinionIds(int heroId) throws InvalidIdentifierException{
        return getHero(heroId).getAllMinionIds();
    }

    public Config.MinionType getMinionType(int heroId, int minionId) throws InvalidIdentifierException {
        try {
            return getHero(heroId).getMinionType(minionId);
        }catch (InvalidMinionIDException e) {
            throw new InvalidIdentifierException(e.getMessage());
        }
    }

    //*******************************
    //BoardManager methods
    //*******************************

    public void removeMinionFromBoard(int minionId) throws MinionNotOnBoardException {
        boardManager.removeMinionFromBoard(minionId);
    }

    public PositionVector getMinionPos(int minionId) throws MinionNotOnBoardException {
        return boardManager.getMinionPosition(minionId);
    }

    public void doBattle() throws IllegalGameStateException {
        try {
            boardManager.doBattle();
            doHeroDamage();
            boardManager.clearBoard();
        }catch(MinionNotOnBoardException | InvalidPositionException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    private void doHeroDamage() throws IllegalGameStateException {
        try {
            for(HeroBase hero : heroArrayList) {
                hero.decreaseHealth(boardManager.getNumberOfMinionsPerHero(hero.getId()));
            }
        }catch (IllegalHeroValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    public void getBattleLog() {

    }

    public void printBoard() {
        boardManager.printBoard();
    }
}
