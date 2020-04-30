package ch.zhaw.pm2.autochess;

import java.util.ArrayList;

public class BoardManager {

    private Minion[][] board = new Minion[20][20];
    private ArrayList<Minion> minionList = new ArrayList<>();

    public BoardManager() {
    }

    public Minion[][] getBoard() {return board;}

    public ArrayList<Minion> getMinionList() {
        return minionList;
    }

    public void addMinion(Minion.MINION_TYP minion_typ, int heroId) throws IllegalArgumentException{

    }

    public void minionDoMove() {

    }


    public void doBattle() {
        boolean battleRunning = true;
        while(battleRunning) {
            battleRunning = false;
            //at least one minion on each side -> return false if only one team left
            //determine move order (agility)
            //loop over all minions in ordered list
            //call move and attack one after the other
        }
    }


    public void resetMinion() {}

    @Override
    public String toString() {
        return null;
    }

}
