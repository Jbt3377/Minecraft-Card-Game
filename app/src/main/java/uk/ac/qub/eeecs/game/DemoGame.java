package uk.ac.qub.eeecs.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameScreens.CompanyLogoScreen;
import uk.ac.qub.eeecs.game.GameScreens.StartScreen;

/**
 * Sample demo game that is create within the MainActivity class
 *
 * @version 1.0
 */
public class DemoGame extends Game {

    /**
     * Create a new demo game
     */
    public DemoGame() {
        super();
        final String DEFAULT_DECK_NAME = "Steve's Arsenal";
        player1 = new Human(DEFAULT_DECK_NAME);
        player2 = new Human(DEFAULT_DECK_NAME);
        ai = new Ai("Bane of Herobrine");

        this.isPlayer2Human = false;
        this.isPlayer1First = true;
    }

    /*
     * (non-Javadoc)
     *
     * @see uk.ac.qub.eeecs.gage.Game#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Go with a default 20 UPS/FPS
        setTargetFramesPerSecond(30);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Call the Game's onCreateView to get the view to be returned.
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Create and add a stub game screen to the screen manager. We don't
        // want to do this within the onCreate method as the menu screen
        // will layout the buttons based on the size of the view.


        //StartScreen stubMenuScreen = new StartScreen(this);
        //mScreenManager.addScreen(stubMenuScreen);
        this.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
        this.getAssetManager().loadAssets("txt/assets/MobSounds.JSON");
        CompanyLogoScreen logoSplashScreen = new CompanyLogoScreen(this);
        mScreenManager.addScreen(logoSplashScreen);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        // If we are already at the menu screen then exit
        if (mScreenManager.getCurrentScreen().getName().equals("StartScreen")) {
            return false;
        }

        // Stop any playing music
        if(mAudioManager.isMusicPlaying())
            mAudioManager.stopMusic();

        // Go back to the menu screen
        getScreenManager().removeScreen(mScreenManager.getCurrentScreen().getName());
        StartScreen startScreen = new StartScreen(this);
        getScreenManager().addScreen(startScreen);
        return true;
    }
}