/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ch.zhaw.pm2.autochess;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class App extends GameApplication{
	    public String getGreeting() {
	        return "Hello world.";
	    }

	    public static void main(String[] args) {
	        System.out.println(new App().getGreeting());
	        launch(args);
	    }

	@Override
	protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("SMAC");
	}
}
