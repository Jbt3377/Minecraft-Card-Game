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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Mob class tests
 *
 * Created by Josh Beatty
 */
@RunWith(AndroidJUnit4.class)
public class HumanTest {

    private MainGameScreen mainScreen;
    private Human testHuman;

    @Before
    public void setup() {

        Context context = InstrumentationRegistry.getTargetContext();
        DemoGame game = new DemoGame();
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

        testHuman = new Human("Steve's Arsenal");

    }

    @Test
    public void test_MobConstructor() {
        assertEquals(testHuman.getmSelectedDeckName(), "Steve's Arsenal");
        assertNull(testHuman.getSelectedCard());
        assertNull(testHuman.getSelectedMob());
        assertNull(testHuman.getTargetedMob());
        assertEquals(testHuman.getmPlayerHealth(), 60);
        assertEquals(testHuman.getmPlayerMana(), 10);
    }

    @Test
    public void test_setPlayerHealth() {
        testHuman.setmPlayerHealth(50);
        assertEquals(testHuman.getmPlayerHealth(),50);
    }

    @Test
    public void test_setPlayerMana() {
        testHuman.setmPlayerMana(4);
        assertEquals(testHuman.getmPlayerMana(),4);
    }

    @Test
    public void test_setSelectedDeckName() {
        testHuman.setmSelectedDeckName("Bane of Herobrine");
        assertEquals(testHuman.getmSelectedDeckName(),"Bane of Herobrine");
    }

    @Test
    public void test_setSelectedCard() {
        CharacterCardStats testCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
        CharacterCard testCard = new CharacterCard(500, 600, mainScreen, testCardStats);

        testHuman.setSelectedCard(testCard);
        assertEquals(testHuman.getSelectedCard(),testCard);
    }

    @Test
    public void test_setSelectedMob() {
        CharacterCardStats testCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
        CharacterCard testCard = new CharacterCard(500, 600, mainScreen, testCardStats);
        Mob testMob = new Mob(500, 600, mainScreen, testCard);

        testHuman.setSelectedMob(testMob);
        assertEquals(testHuman.getSelectedMob(),testMob);
    }

    @Test
    public void test_setTargetedMob() {
        CharacterCardStats testCardStats = new CharacterCardStats("Cat", 1, "Meow", 16, 1, 2);
        CharacterCard testCard = new CharacterCard(500, 600, mainScreen, testCardStats);
        Mob testMob = new Mob(500, 600, mainScreen, testCard);

        testHuman.setTargetedMob(testMob);
        assertEquals(testHuman.getTargetedMob(),testMob);
    }

    @Test
    public void test_setSelectedMobNull() {
        CharacterCardStats testCardStats = new CharacterCardStats("Cat", 1, "Meow", 16, 1, 2);
        CharacterCard testCard = new CharacterCard(500, 600, mainScreen, testCardStats);
        Mob testMob = new Mob(500, 600, mainScreen, testCard);

        testHuman.setSelectedMob(testMob);
        testHuman.setSelectedMobNull();
        assertNull(testHuman.getSelectedMob());
    }

    @Test
    public void test_setTargetedMobNull() {
        CharacterCardStats testCardStats = new CharacterCardStats("Cat", 1, "Meow", 16, 1, 2);
        CharacterCard testCard = new CharacterCard(500, 600, mainScreen, testCardStats);
        Mob testMob = new Mob(500, 600, mainScreen, testCard);

        testHuman.setTargetedMob(testMob);
        testHuman.setTargetedMobNull();
        assertNull(testHuman.getTargetedMob());
    }

}
