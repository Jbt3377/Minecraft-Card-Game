package uk.ac.qub.eeecs.gage;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Bitmap;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;
import uk.ac.qub.eeecs.game.GameScreens.DeckEditorScreen;



import static org.junit.Assert.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * DeckEditorScreen Tests
 * Created Andrew Brown
 */
@RunWith(MockitoJUnitRunner.class)
public class DeckEditorScreenTest{



    @Mock
    private Game game = mock(Game.class);
    @Mock
    private DeckEditorScreen deckEditorScreen = mock(DeckEditorScreen.class);
    @Mock
    private AssetManager assetManager = mock(AssetManager.class);
    @Mock
    private Music music = mock(Music.class);
    @Mock
    private Input input = mock(Input.class);
    @Mock
    private Sound sound = mock(Sound.class);
    @Mock
    private Bitmap bitmap = mock(Bitmap.class);
    @Mock
    private ElapsedTime elapsedTime = mock(ElapsedTime.class);

    @Mock
    private CharacterCard cTest = mock(CharacterCard.class);

//    @Mock
//    private CharacterCardStats testCharacterCardStats  = mock(CharacterCardStats.class);







    @Before
    public void setup() {
//        when(game.getAssetManager().get)


        when(game.getAssetManager()).thenReturn(assetManager);
        when(game.getInput()).thenReturn(input);
        when(game.getScreenHeight()).thenReturn(1080);
        when(game.getScreenWidth()).thenReturn(1920);

        when(deckEditorScreen.getGame()).thenReturn(game);
        ScreenManager screenManager = new ScreenManager(game);
        when(game.getScreenManager()).thenReturn(screenManager);
        //when(game.getAssetManager().getSound())
        //game.getAssetManager().loadAndAddBitmap("CardBackground", "img/CardBackground.png");
        //when(game.getAssetManager().getSound()).thenReturn(sound);
        //when(game.getAssetManager().getBitmap("RightArrow")).thenReturn(bitmap);
        //when(game.getAssetManager().getBitmap(any(String.class))).thenReturn(bitmap);
//        when(game.getAssetManager().getBitmap("CardBackground")).thenReturn(bitmap);
//        when(game.getAssetManager().getBitmap("Cow")).thenReturn(bitmap);



//        when(game.assetManager.loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON"));
//        game.mAssetManager.loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");
//        game.mAssetManager.customLoadCard("txt/assets/AllCardStats.JSON");



        //when(assetManager.getSound("").thenReturn(sound));


//        Context context = InstrumentationRegistry.getTargetContext();
//        game = new DemoGame();
//        game.mFileIO = new FileIO(context);
//        game.mAssetManager = new AssetManager(game);
//        game.mScreenManager = new ScreenManager(game);
//        game.mAudioManager = new AudioManager(game);
//        game.mAssetManager.loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
//        game.mAssetManager.loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");
//        game.mAssetManager.customLoadCard("txt/assets/AllCardStats.JSON");
//        game.getAssetManager().loadAssets("txt/assets/MobSounds.JSON");
//        game.mDeckManager = new DeckManager(game.mAssetManager.getAllCardStats());
//        deckEditorScreen = new DeckEditorScreen("DeckEditor", game);
//
//        game.getScreenManager().addScreen(deckEditorScreen);
//
//
        //testCharacterCardStats = new CharacterCardStats("Cow", 3, "Moo", 0, 10, 2);
//

    }

//    @Test
//    public void

    @Test
    public void testRemoveCardFromDeck(){
        //Set up
        int testDeckSize = 20;
        int index = 5;
        for(int i = 0; i <testDeckSize; i++){
            deckEditorScreen.addCardToDeck(cTest);
        }

        //Test
        assertEquals(deckEditorScreen.getDeck().size(), testDeckSize, testDeckSize);
        deckEditorScreen.removeCardFromDeck(index);
        assertEquals(deckEditorScreen.getDeck().size(), testDeckSize-1, testDeckSize-1);
    }


    @Test
    public void testLeftButton(){
        deckEditorScreen.getLeftButton().buttonPushed(true);
        deckEditorScreen.leftButtonTrigger();
        assertFalse(deckEditorScreen.getLeftButton().isPushTriggered());
        assertTrue(deckEditorScreen.getNumDisplay()!=0);
        assertEquals(deckEditorScreen.getNumDisplay(), -10, -10);
    }
    @Test
    public void testRightButton(){
    DeckEditorScreen deckEditorScreen = new DeckEditorScreen("DeckEditor", game);
        float posX = deckEditorScreen.getRightButton().position.x;
        float posY = deckEditorScreen.getRightButton().position.y;
        List touchEvents = createTouchEvent(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < 20; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }


        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertNotEquals(originalNumber, deckEditorScreen.getNumDisplay());




//        deckEditorScreen.getRightButton().buttonPushed(true);
//        deckEditorScreen.rightButtonTrigger();
//        assertFalse(deckEditorScreen.getRightButton().isPushTriggered());
//        assertTrue(deckEditorScreen.getNumDisplay()!=0);
//        assertEquals(deckEditorScreen.getNumDisplay(), 10, 10);
    }


//
//    @Test
//    public void testGetAllCards(){
//        ArrayList<Card> testCollection = deckEditorScreen.getCardCollection();
//        assertTrue(testCollection.size() == deckEditorScreen.getCardCollection().size());
//    }

    @Test
    public void testAddCardToDeck() {
        deckEditorScreen.addCardToDeck(cTest);
        //assertEquals(deckEditorScreen.getDeck().get(0),cTest.getCardID(), 0);
    }

    @Test
    public void testClearDeck_NoDeck(){
        deckEditorScreen.clearDeck();
        assertEquals(deckEditorScreen.getDeck().size(),0.0f,0.0f);
    }

    @Test
    public void testClearDeck_FullDeck(){
        for(int i = 0; i <deckEditorScreen.getDeck().size(); i++){
            deckEditorScreen.addCardToDeck(cTest);
        }
        deckEditorScreen.clearDeck();
        assertEquals(deckEditorScreen.getDeck().size(),0.0f,0.0f);
    }




//
//        //game.getScreenManager().addScreen(game.getScreenManager().getScreen("StartScreen"));
//        deckEditorScreen.getReturnButton().buttonPushed(true);
//        assertTrue(deckEditorScreen.getReturnButton().isPushTriggered());
//
//
//
//        game.getScreenManager().removeScreen("DeckEditor");
//
//
////        game.getScreenManager().addScreen(deckEditorScreen);
//////        deckEditorScreen.getReturnButton().;
//        assertTrue(game.getScreenManager().getCurrentScreen().getName() != "DeckEditor");
//


    //Method for getting touch events
    public List createTouchEvent(float touchX, float touchY) {
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = TouchEvent.TOUCH_UP;
        touchEvent.x = touchX;
        touchEvent.y = touchY;

        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
        touchEvents.add(touchEvent);
        return touchEvents;
    }



}
