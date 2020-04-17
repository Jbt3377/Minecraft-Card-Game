package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * Card class tests
 * <p>
 * Created by Matthew McCleave
 */
@RunWith(AndroidJUnit4.class)
public class MobTest {

    private Context context;
    private DemoGame game;
    private MainGameScreen mainScreen;
    private Mob testMob;
    private CharacterCard testCharacterCard;

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

        String name = "Cow";
        int manaCost = 3;
        String desc = "Moo";
        int id = 0;
        int hp = 10;
        int attack = 2;

        CharacterCardStats testCharacterCardStats = new CharacterCardStats(name, manaCost, desc, id, hp, attack);
        testCharacterCard = new CharacterCard(500, 600, mainScreen, testCharacterCardStats);
    }

    @Test
    public void mobPosition_Valid() {
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertTrue((testMob.getBound().x == 500) && (testMob.getBound().y == 600));
    }

    @Test
    public void mobPosition_Invalid() {
        testMob = new Mob(1100, 400, mainScreen, testCharacterCard);

        assertFalse((testMob.getBound().x == 500) && (testMob.getBound().y == 600));
    }

    @Test
    public void testMobHealthPoints_Valid(){

        int cardStatsHP = testCharacterCard.getmHP();
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertTrue(testMob.getHealthPoints() == cardStatsHP);
    }

    @Test
    public void testMobHealthPoints_Invalid(){

        int cardStatsHP = testCharacterCard.getmHP();
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertFalse(testMob.getHealthPoints() == cardStatsHP);
    }

    @Test
    public void testMobAttackDamage_Valid(){

        int cardStatsAtkDmg = testCharacterCard.getmAttackDmg();
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertTrue(testMob.getAttackDamage() == cardStatsAtkDmg);
    }

    @Test
    public void testMobAttackDamage_Invalid(){

        int cardStatsAtkDmg = testCharacterCard.getmAttackDmg();
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertFalse(testMob.getAttackDamage() == cardStatsAtkDmg);
    }

    @Test
    public void testMobName_Valid(){

        String cardStatsName = testCharacterCard.getCardName();
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertTrue(testMob.getName().equals(cardStatsName));
    }

    @Test
    public void testMobName_Invalid(){

        String cardStatsName = testCharacterCard.getCardName();
        testMob = new Mob(500, 600, mainScreen, testCharacterCard);

        assertFalse(testMob.getName().equals(cardStatsName));
    }

}