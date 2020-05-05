package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Board.BoardManager;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Minion.MinionBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private ArrayList<HeroBase> heroArrayList = new ArrayList<>();
    private BoardManager boardManager;

    public Game() throws IllegalArgumentException{
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
    }

    public MinionBase[][] getBoard() {
        return boardManager.getBoard();
    }

    //*******************************
    //Hero methods
    //*******************************

    private HeroBase getHero(int heroId) throws IllegalArgumentException{
        //todo: create isValidId method like in HeroBase. Call in each method to throw proper exception
        HeroBase foundHero = null;
        for(HeroBase hero : heroArrayList) {
            if(hero.getId() == heroId) {
                foundHero = hero;
            }
        }
        if(foundHero.equals(null)) {
            throw new IllegalArgumentException("No hero-ID matches the given parameter");
        }
        return foundHero;
    }

    public boolean checkHeroWinner() {
        return false;
    }

    public void addHero(HeroBase.HeroType heroType) {
        try {
            heroArrayList.add(HeroBase.HeroType.getHeroFromType(heroType));
        } catch (IllegalArgumentException e) {

        }
    }

    public void doHeroAbility(int heroId) {

    }

    //***********************
    //Minion methods
    //***********************

    public void buyMinion(int heroId, MinionBase.MinionType minionType) throws IllegalArgumentException{
        try {
            HeroBase hero = getHero(heroId);
            hero.buyMinion(minionType);
        } catch (IllegalArgumentException e){

        }
    }

    public void sellMinion(int heroId, int minionId) throws IllegalArgumentException {
        try {
            HeroBase hero = getHero(heroId);
            hero.sellMinion(minionId);
        } catch(IllegalArgumentException e) {

        }
    }

    public void placeMinionOnBoard(int heroId, int minionId, int xPos, int yPos) throws IllegalArgumentException {
        //todo: getMinion and pass to BoardManager board with pos
    }

    public void removeMinionFromBoard(int xPos, int yPos) {
        //todo: BoardManager: find minion at x,y and remove from board
    }

    public int getMinionPos(int heroId, int minionId) {
        //todo: BoardManager: find minion on board and return pos
        return 0;
    }

    public Set<Integer> getAllMinionIds(int heroId) throws IllegalArgumentException{
        Set<Integer> minionIdList = new HashSet<>();
        try {
            minionIdList = getHero(heroId).getAllMinionIds();
        } catch (IllegalArgumentException e) {

        }
        return minionIdList;
    }

    public MinionBase.MinionType getMinionType(int heroId, int minionId) throws IllegalArgumentException{
        return null;
    }

    public int getMinionLevel(int heroId, int minionId) throws IllegalArgumentException{
        return 1;
    }

    public void increaseMinionLevel(int heroId, int minionId) {

    }

    public void decreaseMinionLevel(int heroId, int minionId) {

    }

    //*******************************
    //Battle methods
    //*******************************

    public void doBattle() {

    }

    public ArrayList<String> getBattleLog() {
        return null;
    }
}
