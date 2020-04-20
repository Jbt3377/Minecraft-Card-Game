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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PlayerHand;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * PlayerHand class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class PlayerHandTest {

    private MainGameScreen mainScreen;
    private DeckManager deckManager;

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

        deckManager = new DeckManager(game.getAssetManager().getAllCardStats());
    }

    // Helper Test Methods //

    public void helperTest_confirmHandFull(PlayerHand testPlayerHand){

        assertFalse(testPlayerHand.getPlayerHand().isEmpty());
        assertEquals(testPlayerHand.getPlayerHand().size(), 7);

    }

    public void helperTest_confirmCardTypeCount(PlayerHand testPlayerHand, int expectedCharacterCardCount,
                                   int expectedSpecialCardCount){

        int characterCardCount = 0;
        int specialCardCount = 0;
        for(Card currentCard: testPlayerHand.getPlayerHand())
            if(currentCard instanceof CharacterCard) characterCardCount++;
            else specialCardCount++;

        assertEquals(characterCardCount, expectedCharacterCardCount);
        assertEquals(specialCardCount, expectedSpecialCardCount);
    }

    // Tests //

    @Test
    public void test_playerHandConstructor(){
        Deck playerDeck = deckManager.constructDeck("Steve's Arsenal");
        PlayerHand testPlayerHand = new PlayerHand(playerDeck, true, mainScreen);

        helperTest_confirmHandFull(testPlayerHand);
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 2);
    }

    @Test
    public void test_replenishHandWhenEmpty(){

        Deck playerDeck = deckManager.constructDeck("Steve's Arsenal");
        PlayerHand testPlayerHand = new PlayerHand(playerDeck, true, mainScreen);

        // Remove all cards in hand
        testPlayerHand.getPlayerHand().clear();
        assertTrue(testPlayerHand.getPlayerHand().isEmpty());

        // Replenish Hand
        testPlayerHand.replenishHand();

        // Assert Hand Full - After Replenish
        helperTest_confirmHandFull(testPlayerHand);
        helperTest_confirmCardTypeCount(testPlayerHand,5, 2);
    }

    @Test
    public void test_replenishHandWhenFull(){

        Deck playerDeck = deckManager.constructDeck("Steve's Arsenal");
        PlayerHand testPlayerHand = new PlayerHand(playerDeck, true, mainScreen);

        // Assert Hand Full
        helperTest_confirmHandFull(testPlayerHand);
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 2);

        // Replenish Hand
        testPlayerHand.replenishHand();

        // Assert Hand Full
        helperTest_confirmHandFull(testPlayerHand);
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 2);
    }

    @Test
    public void test_replenishHandWhenMaxCharacterCards(){

        Deck playerDeck = deckManager.constructDeck("Steve's Arsenal");
        PlayerHand testPlayerHand = new PlayerHand(playerDeck, true, mainScreen);

        // Remove all cards in hand
        testPlayerHand.getPlayerHand().clear();
        assertTrue(testPlayerHand.getPlayerHand().isEmpty());

        // Add 5 (max) Character Cards
        for(int i=0; i<5; i++) {
            CharacterCardStats dogCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
            testPlayerHand.getPlayerHand().add(new CharacterCard(500, 600, mainScreen, dogCardStats));
        }

        // Confirm only 5 Character Cards in Hand
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 0);

        // Replenish Hand
        testPlayerHand.replenishHand();

        // Confirm replenish - 5x Character Cards, 2x Special Cards
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 2);
        helperTest_confirmHandFull(testPlayerHand);
    }

    @Test
    public void test_replenishHandWhenMaxSpecialCards(){

        Deck playerDeck = deckManager.constructDeck("Steve's Arsenal");
        PlayerHand testPlayerHand = new PlayerHand(playerDeck, true, mainScreen);

        // Remove all cards in hand
        testPlayerHand.getPlayerHand().clear();
        assertTrue(testPlayerHand.getPlayerHand().isEmpty());

        // Add 2 (max) Special Cards
        EquipCardStats tridentCardStats = new EquipCardStats("Trident",3,"+3 Atk",3, 3);
        testPlayerHand.getPlayerHand().add(new EquipCard(500, 600, mainScreen, tridentCardStats));
        UtilityCardStats healingCardStats = new UtilityCardStats("Potion of Healing",2,"Heal by 3 HP",32, 3);
        testPlayerHand.getPlayerHand().add(new UtilityCard(600, 600, mainScreen, healingCardStats));

        // Confirm only 2 Special Cards in Hand
        helperTest_confirmCardTypeCount(testPlayerHand, 0, 2);

        // Replenish Hand
        testPlayerHand.replenishHand();

        // Confirm replenish - 5x Character Cards, 2x Special Cards
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 2);
        helperTest_confirmHandFull(testPlayerHand);
    }

    @Test
    public void test_replenishHandHalfFull(){

        Deck playerDeck = deckManager.constructDeck("Steve's Arsenal");
        PlayerHand testPlayerHand = new PlayerHand(playerDeck, true, mainScreen);

        // Remove all cards in hand
        testPlayerHand.getPlayerHand().clear();
        assertTrue(testPlayerHand.getPlayerHand().isEmpty());

        // Add 2 Character Cards
        CharacterCardStats dogCardStats = new CharacterCardStats("Dog", 1, "Woof", 12, 1, 1);
        testPlayerHand.getPlayerHand().add(new CharacterCard(500, 600, mainScreen, dogCardStats));
        testPlayerHand.getPlayerHand().add(new CharacterCard(500, 600, mainScreen, dogCardStats));

        // Add 1 Special Cards
        EquipCardStats tridentCardStats = new EquipCardStats("Trident",3,"+3 Atk",3, 3);
        testPlayerHand.getPlayerHand().add(new EquipCard(500, 600, mainScreen, tridentCardStats));

        // Confirm only 3 Cards in Hand
        helperTest_confirmCardTypeCount(testPlayerHand, 2, 1);

        // Replenish Hand
        testPlayerHand.replenishHand();

        // Confirm replenish - 5x Character Cards, 2x Special Cards
        helperTest_confirmCardTypeCount(testPlayerHand, 5, 2);
        helperTest_confirmHandFull(testPlayerHand);

    }

}
