package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertArrayEquals;

/**
 * Deck class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class DeckManagerTest {

    private DeckManager testDeckManager;

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

        testDeckManager = new DeckManager(game.getAssetManager().getAllCardStats());
    }

    @Test
    public void test_deckManagerConstructor() {
        assertFalse(testDeckManager.isCustomDeckAdded());
    }

    @Test
    public void test_constructDeck() {
        String testDeckName = "Steve's Arsenal";
        Deck testDeck = testDeckManager.constructDeck(testDeckName);

        // List of IDs of each Card Stat. Each should exist in constructed Deck.
        int[] predefinedDeck0 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,27,29,32,36};
        ArrayList<Integer> idsToCheck = new ArrayList<>();

        // Pop all contents of the constructed deck
        CardStats nextPop = testDeck.popNextCharacterCardStat();
        while(nextPop != null) {
            idsToCheck.add(nextPop.getId());
            nextPop = testDeck.popNextCharacterCardStat();
        }

        nextPop = testDeck.popNextSpecialCardStat();
        while(nextPop != null) {
            idsToCheck.add(nextPop.getId());
            nextPop = testDeck.popNextSpecialCardStat();
        }

        // Build an array of all Card Stat IDs of the constructed Deck
        int[] constructedDeckContents = new int[idsToCheck.size()];
        for (int i=0; i < constructedDeckContents.length; i++)
            constructedDeckContents[i] = idsToCheck.get(i);

        Arrays.sort(constructedDeckContents);
        Arrays.sort(predefinedDeck0);

        // Assert the same IDs exist in the constructed deck as requested
        assertArrayEquals(constructedDeckContents, predefinedDeck0);
    }


    //TODO: Test Methods for; test_addDeck(), test_setupCustomDeck(), test_isCustomDeckAdded()


}
