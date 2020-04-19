package uk.ac.qub.eeecs.gage;
import android.content.Context;
import android.graphics.Typeface;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;
import uk.ac.qub.eeecs.game.GameScreens.OptionsScreen;
import android.graphics.Paint;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class FPStest {
    private Context context;
    private DemoGame game;
    private OptionsScreen optionsScreen;

    @Before
    public void setup() {

        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        game.mScreenManager = new ScreenManager(game);
        game.mAudioManager = new AudioManager(game);
        optionsScreen = new OptionsScreen("OptionsScreen",game);
        game.getScreenManager().addScreen(optionsScreen);

    }

    @Test
//this tests if the button works
    public void FpsTestButton(){
    optionsScreen.getFpsToggleButton().setToggled(true);
    boolean paused = optionsScreen.getFpsToggleButton().isToggledOn();
    assertTrue(paused);

    }

}
