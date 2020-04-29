package ch.zhaw.pm2.autochess;

import java.util.ArrayList;

public class Game {
    private ArrayList<HeroBase> heroArrayList = new ArrayList<>();
    private BoardManager boardManager;

    /**
     * Constructor for Game object. Initializes board and players. Sets currentGamePhase to
     * GAME_START.
     * @param numberOfPlayers int representing the number of players
     * @throws IllegalArgumentException thrown if number of players invalid
     */
    public Game(int numberOfPlayers) throws IllegalArgumentException{
        boardManager = new BoardManager();
        heroArrayList = new ArrayList<>();
    }

    /**
     * Getter for specific Hero
     * @param heroId int to match with hero ID attribute
     * @return Hero whose hero ID attribute matches the given integer
     * @throws IllegalArgumentException thown if no matching hero found
     */
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

    /**
     * Getter method for the Board object attribute
     * @return Board object
     */
    public Minion[][] getBoard() {
        return boardManager.getBoard();
    }

    public boolean checkWinner() {
        return false;
    }

    public void addHero(HeroBase.HERO_TYP hero_typ) {
        heroArrayList.add(HeroBase.HERO_TYP.getHeroFromType(hero_typ));
    }

    public void buyMinions(int heroId, ArrayList<BoardManager.MINION_TYP> minionTypList) {
        try {
            HeroBase hero = getHero(heroId);
            hero.decreaseAvailableFunds(calcMinionListCost(minionTypList));
            addMinions(minionTypList, heroId);
        } catch (IllegalArgumentException e) {
            //catch multiple exceptions depending on if thrown from hero or BoardManager (Custom Exceptions Needed)
            //reset funds and minions if caught noFundsException
        }
    }

    private int calcMinionListCost(ArrayList<BoardManager.MINION_TYP> minionTypList) {
        int totalCost = 0;
        for(BoardManager.MINION_TYP minion_typ : minionTypList) {
            totalCost += minion_typ.getPrice();
        }
        return totalCost;
    }

    private void addMinions(ArrayList<BoardManager.MINION_TYP> minionTypList, int heroId) throws IllegalArgumentException{
            for (BoardManager.MINION_TYP minion_typ : minionTypList) {
                boardManager.addMinion(minion_typ, heroId);
            }
    }




}
