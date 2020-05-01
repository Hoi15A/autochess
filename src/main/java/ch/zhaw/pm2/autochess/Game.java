package ch.zhaw.pm2.autochess;

import java.util.ArrayList;
import java.util.Set;

public class Game {
    private ArrayList<HeroBase> heroArrayList = new ArrayList<>();
    private BoardManager boardManager;

    public Game() throws IllegalArgumentException{
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
    }

    public Minion[][] getBoard() {
        return boardManager.getBoard();
    }

    //*******************************
    //Hero methods
    //*******************************

    private HeroBase getHero(int heroId) throws IllegalArgumentException{
        HeroBase foundHero = null;
        for(HeroBase hero : heroArrayList) {
            if(hero.getHeroId() == heroId) {
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

    public void addHero(HeroBase.HERO_TYP hero_typ) {
        heroArrayList.add(HeroBase.HERO_TYP.getHeroFromType(hero_typ));
    }

    public void doHeroAbility(int heroId) {

    }

    //***********************
    //Minion methods
    //***********************

    public void buyMinion(int heroId, Minion.MINION_TYP minionTyp) throws IllegalArgumentException{
        try {
            HeroBase hero = getHero(heroId);
            hero.buyMinion(minionTyp);
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

    public Set<Integer> getAllMinionIds(int heroId) throws IllegalArgumentException{
        return null;
    }

    public Minion.MINION_TYP getMinionTyp(int minionId) throws IllegalArgumentException{
        return null;
    }

    public int getMinionLevel(int minionId) throws IllegalArgumentException{
        return 1;
    }

    //*******************************
    //Battle methods
    //*******************************

    public void doBattle() {
        //combine minionLists
        //call boardManager doBattle with list of
    }

    public ArrayList<String> getBattleLog() {
        return null;
    }
}
