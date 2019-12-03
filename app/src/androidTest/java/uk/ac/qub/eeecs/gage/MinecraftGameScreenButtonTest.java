package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.MinecraftCardGame.MinecraftCardGameScreen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class MinecraftGameScreenButtonTest {

    private Context context;
    private DemoGame game;
    private MinecraftCardGameScreen mainScreen;

    @Before
    public void setup(){

        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        game.mAudioManager = new AudioManager(game);
        game.mScreenManager = new ScreenManager(game);
        mainScreen = new MinecraftCardGameScreen(game);
        game.mScreenManager.addScreen(mainScreen);


    }


    @Test
    public void optionsButtonPushedTrue() {
        mainScreen.getRulesScreenButtonScreenButton().buttonPushed(true);
        mainScreen.update(new ElapsedTime());
        assertTrue(game.getScreenManager().getCurrentScreen().getName() == "Rules");
    }
}


