package uk.ac.qub.eeecs.gage;

import android.content.Context;
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
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * Popup class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class MobContainerTest {

    private DemoGame game;
    private MainGameScreen mainScreen;

    @Before
    public void setup() {

        Context context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        game.mScreenManager = new ScreenManager(game);
        game.mAudioManager = new AudioManager(game);
        game.mAssetManager.loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
        game.mAssetManager.loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");
        game.mAssetManager.customLoadCard("txt/assets/AllCardStats.JSON");
        game.getAssetManager().loadAssets("txt/assets/MobSounds.JSON");
        game.mDeckManager = new DeckManager(game.mAssetManager.getAllCardStats());
        mainScreen = new MainGameScreen(game);
        game.getScreenManager().addScreen(mainScreen);

    }

    @Test
    public void test_mobContainerConstructor_BottomPlayer(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.BOTTOM_PLAYER,
                mainScreen);

        assertTrue(testMobContainer.isEmpty());
        assertNull(testMobContainer.getContents());
        assertEquals(testMobContainer.getContType(), MobContainer.ContainerType.BOTTOM_PLAYER);
        assertTrue(testMobContainer.getX_location() == 600);
        assertTrue(testMobContainer.getY_location() == 500);
        assertEquals(testMobContainer.getBitmap(), game.getAssetManager().getBitmap("ItemFrame"));
    }

}
