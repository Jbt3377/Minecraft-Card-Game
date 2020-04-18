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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * Mob class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class MobTest {

    private DemoGame game;
    private MainGameScreen mainScreen;
    private Mob testMob;

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
    public void test_MobConstructor() {

        String name = "Cow";
        int manaCost = 3;
        String desc = "Moo";
        int id = 0;
        int hp = 10;
        int attack = 2;

        CharacterCardStats testCharacterCardStats = new CharacterCardStats(name, manaCost, desc, id, hp, attack);
        CharacterCard testCharacterCard = new CharacterCard(500, 600, mainScreen, testCharacterCardStats);
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertTrue((testMob.getBound().x == 500) && (testMob.getBound().y == 600));
        assertFalse((testMob.getBound().x == 1000) && (testMob.getBound().y == 400));
        assertEquals(testMob.getHealthPoints(), 10);
        assertEquals(testMob.getAttackDamage(), 2);
        assertEquals(testMob.getName(), "Cow");
        assertFalse(testMob.hasBeenUsed());
        assertEquals(testMob.getAttackSound(), game.getAssetManager().getSound("cow_attack"));
        assertEquals(testMob.getDamagedSound(), game.getAssetManager().getSound("cow_damaged"));
        assertEquals(testMob.getDeathSound(), game.getAssetManager().getSound("cow_death"));
        assertEquals(testMob.getMobID(), 0);
        assertFalse(testMob.isSelectedToAttack());
    }

    @Test
    public void test_setHealthPoints() {
        testMob.setHealthPoints(14);
        assertEquals(testMob.getHealthPoints(),14);
    }

    @Test
    public void test_setAttackDamage() {
        testMob.setAttackDamage(6);
        assertEquals(testMob.getAttackDamage(),6);
    }

    @Test
    public void test_setHasBeenUsed() {
        testMob.setHasBeenUsed(true);
        assertTrue(testMob.hasBeenUsed());
    }

    @Test
    public void test_setSelectedToAttack() {
        testMob.setSelectedToAttack(true);
        assertTrue(testMob.isSelectedToAttack());
    }

    @Test
    public void test_setEquipCard() {
        EquipCardStats testEquipCardStats = new EquipCardStats("Trident",3,"+3 Atk",3, 3);
        EquipCard testEquipCard = new EquipCard(500, 600, mainScreen, testEquipCardStats);
        testMob.setEquipCard(testEquipCard);
        assertEquals(testMob.getEquipCard(), testEquipCard);
    }

    @Test
    public void test_attackTarget() {

        CharacterCardStats testDogCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
        CharacterCard testDogCard = new CharacterCard(500, 600, mainScreen, testDogCardStats);
        Mob testDog = new Mob(500, 600, mainScreen, testDogCard);

        int hpBeforeAttack = testMob.getHealthPoints();
        testDog.attackTarget(testMob);

        assertEquals(testMob.getHealthPoints(), hpBeforeAttack-1);
        assertTrue(testDog.hasBeenUsed());
    }
}