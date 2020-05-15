/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Board.exceptions.InvalidPositionException;
import ch.zhaw.pm2.autochess.Game.Game;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalFundsReductionException;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) throws IllegalGameStateException {
        System.out.println(new App().getGreeting());

        try {
            Game game = new Game(Config.HeroType.ALIEN, Config.HeroType.ENGINEER);

            game.buyMinion(1, Config.MinionType.WARRIOR);
            game.buyMinion(1, Config.MinionType.WARRIOR);
            game.buyMinion(1, Config.MinionType.WARRIOR);
            game.buyMinion(1, Config.MinionType.WARRIOR);
            game.placeMinionOnBoard(1, 0, new PositionVector(0, 0));
            game.placeMinionOnBoard(1, 1, new PositionVector(1,0));
            game.placeMinionOnBoard(1, 2, new PositionVector(2, 0));
            game.placeMinionOnBoard(1, 3, new PositionVector(3, 0));

            game.buyMinion(2, Config.MinionType.WARRIOR);
            game.buyMinion(2, Config.MinionType.WARRIOR);
            game.buyMinion(2, Config.MinionType.WARRIOR);
            game.buyMinion(2, Config.MinionType.WARRIOR);
            game.placeMinionOnBoard(2, 4, new PositionVector(0, 7));
            game.placeMinionOnBoard(2, 5, new PositionVector(1,7));
            game.placeMinionOnBoard(2, 6, new PositionVector(2, 7));
            game.placeMinionOnBoard(2, 7, new PositionVector(3, 7));

            System.out.println(" ");
            game.printBoard();
            System.out.println(" ");

            game.doBattle();

        }catch (IllegalFundsReductionException | InvalidPositionException e) {
            e.printStackTrace();
        }
    }
}
