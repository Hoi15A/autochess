package ch.zhaw.pm2.autochess;

public class Minion {



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
