/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ch.zhaw.pm2.autochess;

import ch.zhaw.pm2.autochess.Game.Game;
import ch.zhaw.pm2.autochess.Game.exceptions.IllegalGameStateException;
import ch.zhaw.pm2.autochess.Game.exceptions.InvalidIdentifierException;
import ch.zhaw.pm2.autochess.Game.exceptions.InvalidTypeException;
import ch.zhaw.pm2.autochess.Hero.HeroBase;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        Game game = new Game();
        try {
            game.addHero(HeroBase.HeroType.ALIEN);
            game.addHero(HeroBase.HeroType.SPACE_MARINE);

            game.buyMinion(0, MinionBase.MinionType.RANGER);
            game.placeMinionOnBoard(0,0, 2,3);

            game.buyMinion(1, MinionBase.MinionType.TANK);
            game.placeMinionOnBoard(1,1, 6,3);

            System.out.println(" ");
            game.printBoard();
            System.out.println(" ");

            game.doBattle();

            System.out.println(" ");
            game.printBoard();
            System.out.println(" ");

        } catch (InvalidTypeException | InvalidIdentifierException | IllegalGameStateException | InvalidMinionIDException e) {
            System.out.println(e.getMessage());
        }
    }
}
