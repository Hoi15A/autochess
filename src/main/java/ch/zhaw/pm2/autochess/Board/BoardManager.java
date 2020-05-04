package ch.zhaw.pm2.autochess.Board;

import ch.zhaw.pm2.autochess.Minion.MinionBase;

import java.util.ArrayList;

public class BoardManager {

    private MinionBase[][] board = new MinionBase[20][20];
    private ArrayList<MinionBase> minionList = new ArrayList<>();

    public BoardManager() {
    }

    public MinionBase[][] getBoard() {return board;}

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
