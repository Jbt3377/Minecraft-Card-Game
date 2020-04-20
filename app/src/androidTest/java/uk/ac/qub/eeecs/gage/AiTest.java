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
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Ai class tests
 *
 * Created by Josh Beatty
 */
@RunWith(AndroidJUnit4.class)
public class AiTest {

    private MainGameScreen mainScreen;
    private Ai testAi;

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
        System.out.println("HEre------------------------------");
        game.getScreenManager().addScreen(mainScreen);

        testAi = new Ai("Steve's Arsenal");

    }

    @Test
    public void test_MobConstructor() {
        // Inherited Attributes
        assertEquals(testAi.getmSelectedDeckName(), "Steve's Arsenal");
        assertNull(testAi.getSelectedCard());
        assertNull(testAi.getSelectedMob());
        assertNull(testAi.getTargetedMob());
        assertEquals(testAi.getmPlayerHealth(), 60);
        assertEquals(testAi.getmPlayerMana(), 10);

        // Ai Attributes
        assertFalse(testAi.isAiFinishedAttacks());
        assertFalse(testAi.isAiFinishedMoves());
        assertEquals(testAi.getAnimationTimer(), -1);
        assertEquals(testAi.getSelectedMobToAttackIndex(), 0);
        assertEquals(testAi.getSelectedAiContainerIndex(), 0);
        assertEquals(testAi.getSelectedAiCardToMoveIndex(), 0);
        assertEquals(testAi.getTargetedMobIndex(), 0);
    }

    @Test
    public void test_setPlayerHealth() {
        testAi.setmPlayerHealth(50);
        assertEquals(testAi.getmPlayerHealth(),50);
    }

    @Test
    public void test_setPlayerMana() {
        testAi.setmPlayerMana(4);
        assertEquals(testAi.getmPlayerMana(),4);
    }

    @Test
    public void test_setSelectedDeckName() {
        testAi.setmSelectedDeckName("Bane of Herobrine");
        assertEquals(testAi.getmSelectedDeckName(),"Bane of Herobrine");
    }

    @Test
    public void test_setSelectedCard() {
        CharacterCardStats dogCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
        CharacterCard dogCard = new CharacterCard(500, 600, mainScreen, dogCardStats);

        testAi.setSelectedCard(dogCard);
        assertEquals(testAi.getSelectedCard(),dogCard);
    }

    @Test
    public void test_setSelectedMob() {
        CharacterCardStats dogCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
        CharacterCard dogCard = new CharacterCard(500, 600, mainScreen, dogCardStats);
        Mob testMob = new Mob(500, 600, mainScreen, dogCard);

        testAi.setSelectedMob(testMob);
        assertEquals(testAi.getSelectedMob(),testMob);
    }

    @Test
    public void test_setTargetedMob() {
        CharacterCardStats catCardStat = new CharacterCardStats("Cat", 1, "Meow", 16, 1, 2);
        CharacterCard catCard = new CharacterCard(500, 600, mainScreen, catCardStat);
        Mob testMob = new Mob(500, 600, mainScreen, catCard);

        testAi.setTargetedMob(testMob);
        assertEquals(testAi.getTargetedMob(),testMob);
    }

    @Test
    public void test_setSelectedMobNull() {
        CharacterCardStats catCardStat = new CharacterCardStats("Cat", 1, "Meow", 16, 1, 2);
        CharacterCard catCard = new CharacterCard(500, 600, mainScreen, catCardStat);
        Mob testMob = new Mob(500, 600, mainScreen, catCard);

        testAi.setSelectedMob(testMob);
        testAi.setSelectedMobNull();
        assertNull(testAi.getSelectedMob());
    }

    @Test
    public void test_setTargetedMobNull() {
        CharacterCardStats catCardStat = new CharacterCardStats("Cat", 1, "Meow", 16, 1, 2);
        CharacterCard catCard = new CharacterCard(500, 600, mainScreen, catCardStat);
        Mob testMob = new Mob(500, 600, mainScreen, catCard);

        testAi.setTargetedMob(testMob);
        testAi.setTargetedMobNull();
        assertNull(testAi.getTargetedMob());
    }

    @Test
    public void test_setSelectedAiContainerIndex() {
        testAi.setSelectedAiContainerIndex(3);
        assertEquals(testAi.getSelectedAiContainerIndex(),3);
    }

    @Test
    public void test_setSelectedAiCardToMoveIndex() {
        testAi.setSelectedAiCardToMoveIndex(4);
        assertEquals(testAi.getSelectedAiCardToMoveIndex(),4);
    }

    @Test
    public void test_setAiFinishedMoves() {
        testAi.setAiFinishedMoves(true);
        assertTrue(testAi.isAiFinishedMoves());
    }

    @Test
    public void test_setAnimationTimer() {
        testAi.setAnimationTimer(20);
        assertEquals(testAi.getAnimationTimer(), 20);
    }

    @Test
    public void test_setSelectedMobToAttackIndex() {
        testAi.setSelectedMobToAttackIndex(2);
        assertEquals(testAi.getSelectedMobToAttackIndex(), 2);
    }

    @Test
    public void test_setTargetedMobIndex() {
        testAi.setTargetedMobIndex(4);
        assertEquals(testAi.getTargetedMobIndex(), 4);
    }


    @Test
    public void test_resetAiProperties() {
        testAi.resetAiProperties();

        assertFalse(testAi.isAiFinishedAttacks());
        assertFalse(testAi.isAiFinishedMoves());
        assertEquals(testAi.getAnimationTimer(), -1);
        assertEquals(testAi.getSelectedMobToAttackIndex(), 0);
        assertEquals(testAi.getSelectedAiContainerIndex(), 0);
        assertEquals(testAi.getSelectedAiCardToMoveIndex(), 0);
    }



}