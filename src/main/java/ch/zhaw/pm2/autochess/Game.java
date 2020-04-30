package ch.zhaw.pm2.autochess;

import java.util.ArrayList;

public class Game {
    private ArrayList<HeroBase> heroArrayList = new ArrayList<>();
    private BoardManager boardManager;

    public Game() throws IllegalArgumentException{
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
    }

    public HeroBase getHero(int heroId) throws IllegalArgumentException{
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

    public Minion[][] getBoard() {
        return boardManager.getBoard();
    }

    public boolean checkWinner() {
        return false;
    }

    public void addHero(HeroBase.HERO_TYP hero_typ) {
        heroArrayList.add(HeroBase.HERO_TYP.getHeroFromType(hero_typ));
    }


    public void buyMinions(int heroId, ArrayList<Minion.MINION_TYP> minionTypList) throws IllegalArgumentException{
        if(minionTypList.size() > 10) {
            throw new IllegalArgumentException("Too many minions");
        }
        try {
            HeroBase hero = getHero(heroId);
            hero.decreaseAvailableFunds(calcMinionListCost(minionTypList));
            addMinions(minionTypList, heroId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No hero found || not enough funds || couldn't add minions");
        }
    }

    private int calcMinionListCost(ArrayList<Minion.MINION_TYP> minionTypList) {
        int totalCost = 0;
        for(Minion.MINION_TYP minion_typ : minionTypList) {
            totalCost += minion_typ.getPrice();
        }
        return totalCost;
    }

    private void addMinions(ArrayList<Minion.MINION_TYP> minionTypList, int heroId) throws IllegalArgumentException{
        for (Minion.MINION_TYP minion_typ : minionTypList) {
            boardManager.addMinion(minion_typ, heroId);
        }
    }

    public ArrayList<Minion> getMinionList() {
        return null;
    }

    public void placeMinion(Minion minion, int xPos, int yPos) {

    }

    public void doBattle() {

    }



}
