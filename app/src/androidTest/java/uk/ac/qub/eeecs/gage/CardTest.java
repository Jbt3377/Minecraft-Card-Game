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
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * Card class tests
 * <p>
 * Created by Matthew McCleave
 */
@RunWith(AndroidJUnit4.class)
public class CardTest {

    private Context context;
    private DemoGame game;
    private MainGameScreen mainScreen;
    private CharacterCardStats testCharacterCardStats;

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

        testCharacterCardStats = new CharacterCardStats("Cow", 3, "Moo", 0, 10, 2);
    }

    @Test
    public void Mob_Constructor_Test() {
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);

        //Testing
        assertTrue(card.position.x == 50);
        assertTrue(card.position.y == 100);
        assertTrue(card.getCardName().equals("Cow"));
        assertTrue(card.getManaCost() == 3);
        assertTrue(card.getCardDescription().equals("Moo"));
        assertTrue(card.getCardID() == 0);

    }

    @Test
    public void Card_setCardName_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setCardName("TestText");
        assertTrue(card.getCardName().equals("TestText"));

    }

    @Test
    public void Card_cardMoveXAnimation_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.cardMoveXAnimation(70.0f);
        assertTrue(card.position.x == 70.0f);
    }

    @Test
    public void Card_cardMoveYAnimation_test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.cardMoveYAnimation(120.0f);
        assertTrue(card.position.y == 120.0f);
    }

    @Test
    public void Card_readyToTurnToMob_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertTrue(card.readyToTurnToMob(50,100));
        assertFalse(card.readyToTurnToMob(500,500));
    }

    @Test
    public void Card_setManaCost_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setManaCost(100);
        assertTrue(card.getManaCost() == 100);
    }

    @Test
    public void Card_setCardDescription_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setCardDescription("TestText");
        assertTrue(card.getCardDescription().equals("TestText"));
    }

    @Test
    public void Card_setCardID_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setCardID(100);
        assertTrue(card.getCardID() == 100);
    }

    @Test
    public void Card_setNewPosition_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setNewPosition(300,600);
        assertTrue(card.position.x == 300);
        assertTrue(card.position.y == 600);
    }

    @Test
    public void Card_getBoundingBox_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertEquals(card.getBoundingBox(),card.getBound());
    }

    @Test
    public void Card_getHasBeenSelected_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertFalse(card.getHasBeenSelected());
    }

    @Test
    public void Card_setHasBeenSelected_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setHasBeenSelected(true);
        assertTrue(card.getHasBeenSelected());
    }

    @Test
    public void Card_getCurrentXPosition_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertTrue(card.getCurrentXPosition() == 50.0f);
    }

    @Test
    public void Card_getCurrentYPosition_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertTrue(card.getCurrentYPosition() == 100.0f);
    }

    @Test
    public void Card_getOriginalXPos_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertTrue(card.getOriginalXPos() == 0);
    }

    @Test
    public void Card_getOriginalYPos_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertTrue(card.getOriginalYPos() == 0);
    }

    @Test
    public void Card_setOriginalXPos_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setOriginalXPos(500.0f);
        assertTrue(card.getOriginalXPos() == 500.0f);
    }

    @Test
    public void Card_setOriginalYPos_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setOriginalYPos(700.0f);
        assertTrue(card.getOriginalYPos() == 700.0f);
    }

    @Test
    public void Card_getFlipTimer_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        assertTrue(card.getFlipTimer() == 0);
    }

    @Test
    public void Card_setFlipTime_Test(){
        //Setup
        Card card = new Card(50, 100, mainScreen, testCharacterCardStats);
        //Testing
        card.setFlipTimer(15);
        assertTrue(card.getFlipTimer() == 15);
    }
}
