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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * EquipCard class tests
 * <p>
 * Created by Matthew McCleave
 */
@RunWith(AndroidJUnit4.class)
public class EquipCardTest {
    private Context context;
    private DemoGame game;
    private MainGameScreen mainScreen;
    private EquipCardStats testEquipCardStats;


    @Before
    public void setup() {

        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        game.mScreenManager = new ScreenManager(game);
        game.mAudioManager = new AudioManager(game);
        game.mAssetManager.loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
        game.mAssetManager.loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");
        game.mAssetManager.customLoadCard("txt/assets/AllCardStats.JSON");
        game.mDeckManager = new DeckManager(game.mAssetManager.getAllCardStats());
        mainScreen = new MainGameScreen(game);
        game.getScreenManager().addScreen(mainScreen);
        testEquipCardStats = new EquipCardStats("Bow", 0, "+1 Attack to equipped Mob", 30);
    }

    @Test
    public void EquipCard_Constructor_Test() {
        //Setup
        EquipCard equipCard = new EquipCard(150, 100, mainScreen, testEquipCardStats);
        //Testing
        assertTrue(equipCard.position.x == 150);
        assertTrue(equipCard.position.y == 100);
    }

    @Test
    public void EquipCard_runCardAnimation_Test() {
        //Setup
        EquipCard equipCard = new EquipCard(150, 100, mainScreen, testEquipCardStats);
        //Testing
        assertTrue(equipCard.getWidth() != 0);
        assertTrue(equipCard.getHeight() != 0);

        while (equipCard.getFlipTimer() >= 0) {
            equipCard.runCardAnimation();
        }

        assertTrue(equipCard.getFlipTimer() == -1);
        assertTrue(equipCard.getWidth() == 0);
        assertTrue(equipCard.getHeight() == 0);
    }

    @Test
    public void EquipCard_isAnimationInProgress_Test() {
        //Setup
        EquipCard equipCard = new EquipCard(150, 100, mainScreen, testEquipCardStats);
        //Testing
        assertFalse(equipCard.isAnimationInProgress());
    }

    @Test
    public void EquipCard_setAnimationInProgress_Test() {
        //Setup
        EquipCard equipCard = new EquipCard(150, 100, mainScreen, testEquipCardStats);
        //Testing
        equipCard.setAnimationInProgress(true);
        assertTrue(equipCard.isAnimationInProgress() == true);
    }

    @Test
    public void EquipCard_isAnimationFinished_Test() {
        //Setup
        EquipCard equipCard = new EquipCard(150, 100, mainScreen, testEquipCardStats);
        //Testing
        assertFalse(equipCard.isAnimationFinished());
    }

    @Test
    public void EquipCard_setAnimationFinished_Test() {
        //Setup
        EquipCard equipCard = new EquipCard(150, 100, mainScreen, testEquipCardStats);
        //Testing
        equipCard.setAnimationFinished(true);
        assertTrue(equipCard.isAnimationFinished());
    }
}
