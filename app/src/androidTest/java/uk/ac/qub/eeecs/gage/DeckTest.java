package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Stack;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Deck class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class DeckTest {

    private Deck testDeck;

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
        MainGameScreen mainScreen = new MainGameScreen(game);
        game.getScreenManager().addScreen(mainScreen);

        testDeck = new Deck();
    }

    @Test
    public void test_deckConstructor() {
        assertTrue(testDeck.getCharacterCardStatsStack().isEmpty());
        assertTrue(testDeck.getSpecialCardStatsStack().isEmpty());
    }

    @Test
    public void test_setCharacterCardStatsStack() {

        // Define Test Objects for Stack
        CharacterCardStats testCharStat1 = new CharacterCardStats("Dragon", 6, "Flame", 1, 20, 6);
        CharacterCardStats testCharStat2 = new CharacterCardStats("Creeper", 3, "Boom", 2, 12, 3);
        CharacterCardStats testCharStat3 = new CharacterCardStats("Pig", 1, "Oink", 4, 50, 30);

        // Push Objects to Test Stack
        Stack<CardStats> testCharCardStatsStack = new Stack<>();
        testCharCardStatsStack.push(testCharStat1);
        testCharCardStatsStack.push(testCharStat2);
        testCharCardStatsStack.push(testCharStat3);

        // Set Test Stack as the Test Deck's Stack
        testDeck.setCharacterCardStatsStack(testCharCardStatsStack);

        assertEquals(testDeck.getCharacterCardStatsStack(), testCharCardStatsStack);
    }

    @Test
    public void test_setSpecialCardStatsStack() {

        // Define Test Objects for Stack
        UtilityCardStats testUtilStat1 = new UtilityCardStats("Potion of Healing", 2, "Heal by 3 HP", 32, 3);
        UtilityCardStats testUtilStat2 = new UtilityCardStats("Potion of Lesser Damage", 1, "Damage by 1 HP", 34, 1);
        EquipCardStats testEquipStat1 = new EquipCardStats("Sword", 2, "+4 Attack", 29, 4);
        EquipCardStats testEquipStat2 = new EquipCardStats("Crossbow", 2, "+5 Attack", 31, 5);

        // Push Objects to Test Stack
        Stack<CardStats> testSpecialCardStatsStack = new Stack<>();
        testSpecialCardStatsStack.push(testUtilStat1);
        testSpecialCardStatsStack.push(testUtilStat2);
        testSpecialCardStatsStack.push(testEquipStat1);
        testSpecialCardStatsStack.push(testEquipStat2);

        // Set Test Stack as the Test Deck's Stack
        testDeck.setSpecialCardStatsStack(testSpecialCardStatsStack);

        assertEquals(testDeck.getSpecialCardStatsStack(), testSpecialCardStatsStack);
    }

    @Test
    public void test_popNextCharacterCardStat() {
        // Define Test Card Stat Object
        CharacterCardStats testCharStat1 = new CharacterCardStats("Dragon", 6, "Flame", 1, 20, 6);

        // Create and set test stack as Deck's stack
        Stack<CardStats> testCharCardStatsStack = new Stack<>();
        testCharCardStatsStack.push(testCharStat1);
        testDeck.setCharacterCardStatsStack(testCharCardStatsStack);

        // Before and after checks of popping a Character Card Stat
        assertFalse(testDeck.getCharacterCardStatsStack().isEmpty());
        assertEquals(testDeck.popNextCharacterCardStat(), testCharStat1);
        assertTrue(testDeck.getCharacterCardStatsStack().isEmpty());
    }

    @Test
    public void test_popNextSpecialCardStat() {
        // Define Test Card Stat Object
        UtilityCardStats testUtilStat1 = new UtilityCardStats("Potion of Healing", 2, "Heal by 3 HP", 32, 3);

        // Create and set test stack as Deck's stack
        Stack<CardStats> testSpecialCardStatsStack = new Stack<>();
        testSpecialCardStatsStack.push(testUtilStat1);
        testDeck.setSpecialCardStatsStack(testSpecialCardStatsStack);

        // Before and after checks of popping a Character Card Stat
        assertFalse(testDeck.getSpecialCardStatsStack().isEmpty());
        assertEquals(testDeck.popNextSpecialCardStat(), testUtilStat1);
        assertTrue(testDeck.getSpecialCardStatsStack().isEmpty());
    }

    
}