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
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * Popup class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class PopupTest {

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
    public void test_popupConstructor(){

        PopUpObject testPopup = new PopUpObject(600, 400, game.getAssetManager().getBitmap("PopupSign"),
                mainScreen,50, "Test Popup");

        assertTrue(testPopup.position.x == 600);
        assertTrue(testPopup.position.y == 400);
        assertEquals(testPopup.getBitmap(), game.getAssetManager().getBitmap("PopupSign"));
        assertEquals(testPopup.getGameScreen(), mainScreen);
    }

    @Test
    public void test_movingPopupConstructor(){

        PopUpObject testPopup = new PopUpObject(400, 600, mainScreen, 30,"Test Moving Popup",
                5, true);

        assertTrue(testPopup.position.x == 400);
        assertTrue(testPopup.position.y == 600);
        assertEquals(testPopup.getGameScreen(), mainScreen);
    }

    @Test
    public void test_setPositionValid(){

        PopUpObject testPopup = new PopUpObject(600, 400, game.getAssetManager().getBitmap("PopupSign"),
                mainScreen,50, "Test Popup");

        testPopup.setPosition(300, 1000);
        assertTrue(testPopup.position.x == 300);
        assertTrue(testPopup.position.y == 1000);
        assertTrue(testPopup.getBound().x == 300);
        assertTrue(testPopup.getBound().y == 1000);
    }

    @Test
    public void test_setPositionInvalid(){

        PopUpObject testPopup = new PopUpObject(600, 400, game.getAssetManager().getBitmap("PopupSign"),
                mainScreen,50, "Test Popup");

        testPopup.setPosition(300, 1000);
        assertFalse(testPopup.position.x == 600);
        assertFalse(testPopup.position.y == 400);
        assertFalse(testPopup.position.x == 600);
        assertFalse(testPopup.position.y == 400);
    }

}
