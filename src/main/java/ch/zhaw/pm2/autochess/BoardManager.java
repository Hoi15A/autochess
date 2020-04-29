package ch.zhaw.pm2.autochess;

import java.util.ArrayList;

public class BoardManager {

    private Minion[][] board;
    private ArrayList<Minion> minionList = new ArrayList<>();

    public BoardManager() {
        board = new Minion[20][20];
    }

    public Minion[][] getBoard() {return board;}

    public void addMinion(MINION_TYP minion_typ, int heroId) throws IllegalArgumentException{

    }

    public enum MINION_TYP {
        WARRIOR(10), RANGER(20), TANK(15);

        private int price;

        private MINION_TYP(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
        public static Minion getMinionFromType(MINION_TYP minion_typ) throws IllegalArgumentException{
            return new Minion();
        }
    }
}
