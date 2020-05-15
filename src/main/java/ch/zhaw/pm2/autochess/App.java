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

    public static void main(String[] args) throws IllegalGameStateException, IllegalFundsReductionException, InvalidPositionException {
        System.out.println(new App().getGreeting());

            Game game = new Game(Config.HeroType.ALIEN, Config.HeroType.ENGINEER);

            game.buyMinion(1, Config.MinionType.RANGER);
            game.placeMinionOnBoard(1,0, new PositionVector(2,2));

            game.buyMinion(2, Config.MinionType.TANK);
            game.placeMinionOnBoard(2,1, new PositionVector(7,7));

            System.out.println(" ");
            game.printBoard();
            System.out.println(" ");

            game.doBattle();

            System.out.println(" ");
            game.printBoard();
            System.out.println(" ");
    }
}
