package ch.zhaw.pm2.autochess.Game;

import ch.zhaw.pm2.autochess.Board.BoardManager;
import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Board.exceptions.NoMinionFoundException;
import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Game.exceptions.InvalidIdentifierException;
import ch.zhaw.pm2.autochess.Game.exceptions.InvalidTypeException;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalFundsStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;
import ch.zhaw.pm2.autochess.PositionVector;

import java.util.ArrayList;
import java.util.Set;

public class Game {
    private static int NO_WINNER = -1;

    private ArrayList<HeroBase> heroArrayList;
    private BoardManager boardManager;

    public Game() throws IllegalArgumentException{
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
    }

    public void printBoard() {
        boardManager.printBoard();
    }

    //*******************************
    //Hero methods
    //*******************************

    private HeroBase getHero(int heroId) {
        HeroBase foundHero = null;
        for(HeroBase hero : heroArrayList) {
            if(hero.getId() == heroId) {
                foundHero = hero;
            }
        }
        return foundHero;
    }

    private boolean isValidId(int heroId) {
        for(HeroBase hero : heroArrayList) {
            if(hero.getId() == heroId) {
                return true;
            }
        }
        return false;
    }

    private boolean checkWinner() {
        boolean winner = false;
        for(HeroBase hero : heroArrayList) {
            if(hero.getHealth() < 1) {
                winner = true;
            }
        }
        return true;
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

    public void addHero(Config.HeroType heroType) throws InvalidTypeException, IllegalGameStateException{
        try {
            heroArrayList.add(HeroBase.getHeroFromType(heroType));
        } catch (InvalidHeroTypeException e) {
            throw new InvalidTypeException(e.getMessage());
        } catch (IllegalHeroValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    public void doHeroAbility(int heroId) throws IllegalGameStateException{
        try {
            getHero(heroId).doAbility();
        } catch(InvalidMinionAttributeModifierException e) {
            throw new IllegalGameStateException(e.getMessage());
        } catch(IllegalHeroValueException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    //***********************
    //Minion methods
    //***********************

    public void buyMinion(int heroId, Config.MinionType minionType) throws InvalidIdentifierException, InvalidTypeException, IllegalGameStateException{
        if(isValidId(heroId)) {
            try {
                HeroBase hero = getHero(heroId);
                hero.buyMinion(minionType);
            } catch (InvalidMinionTypeException e) {
                throw new InvalidTypeException(e.getMessage());
            } catch (IllegalHeroValueException | IllegalFundsStateException e) {
                throw new IllegalGameStateException(e.getMessage());
            } catch (MinionException e) {
                throw new IllegalGameStateException(e.getMessage());
            }
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public void sellMinion(int heroId, int minionId) throws InvalidIdentifierException, IllegalGameStateException{
        if(isValidId(heroId)) {
            try {
                HeroBase hero = getHero(heroId);
                hero.sellMinion(minionId);
            } catch (InvalidMinionIDException e) {
                throw new InvalidIdentifierException(e.getMessage());
            } catch (IllegalHeroValueException e) {
                throw new IllegalGameStateException(e.getMessage());
            }
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public void placeMinionOnBoard(int heroId, int minionId, int xPos, int yPos) throws InvalidIdentifierException, InvalidMinionIDException, InvalidPositionException {
        if(isValidId(heroId)) {
            boardManager.setMinionOnBoard(getHero(heroId).getMinion(minionId), new PositionVector(xPos, yPos));
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public void removeMinionFromBoard(int xPos, int yPos) {
        //todo: BoardManager: find minion at x,y and remove from board
    }

    public int getMinionPos(int heroId, int minionId) throws InvalidIdentifierException{
        if(isValidId(heroId)) {
            //todo: BoardManager: find minion on board and return pos
            return 0;
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public Set<Integer> getAllMinionIds(int heroId) throws InvalidIdentifierException{
        if(isValidId(heroId)) {
            return getHero(heroId).getAllMinionIds();
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public Config.MinionType getMinionType(int heroId, int minionId) throws InvalidTypeException, InvalidIdentifierException {
        if(isValidId(heroId)) {
            try {
                return getHero(heroId).getMinionType(minionId);
            }catch (InvalidMinionIDException e) {
                throw new InvalidTypeException(e.getMessage());
            }
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public int getMinionLevel(int heroId, int minionId) throws InvalidIdentifierException{
        if(isValidId(heroId)) {
            try {
                return getHero(heroId).getMinionLevel(minionId);
            }catch (InvalidMinionIDException e){
                throw new InvalidIdentifierException(e.getMessage());
            }
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public void increaseMinionLevel(int heroId, int minionId) throws InvalidIdentifierException {
        if(isValidId(heroId)) {
            try {
                getHero(heroId).increaseMinionLevel(minionId);
            }catch (InvalidMinionIDException e){
                throw new InvalidIdentifierException(e.getMessage());
            }
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    public void decreaseMinionLevel(int heroId, int minionId) throws InvalidIdentifierException {
        if(isValidId(heroId)) {
            try {
                getHero(heroId).decreaseMinionLevel(minionId);
            }catch (InvalidMinionIDException e){
                throw new InvalidIdentifierException(e.getMessage());
            }
        }else {
            throw new InvalidIdentifierException("Not a valid Hero ID");
        }
    }

    //*******************************
    //Battle methods
    //*******************************

    public void doBattle() throws IllegalGameStateException {
        try {
            boardManager.doBattle();
        }catch(NoMinionFoundException | InvalidPositionException e) {
            throw new IllegalGameStateException(e.getMessage());
        }
    }

    private void doHeroDamage() throws IllegalGameStateException {
        for(HeroBase hero : heroArrayList) {
            try {
                hero.decreaseHealth(boardManager.getNumberOfMinionsPerHero(hero.getId()));
            }catch (IllegalHeroValueException e){
                throw new IllegalGameStateException(e.getMessage());
            }
        }
    }

    public ArrayList<String> getBattleLog() {
        return null;
    }
}
