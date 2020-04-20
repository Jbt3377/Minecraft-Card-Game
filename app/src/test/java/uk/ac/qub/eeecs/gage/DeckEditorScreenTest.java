package uk.ac.qub.eeecs.gage;

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
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
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
    private AssetManager assetManager = mock(AssetManager.class);
    @Mock
    private Input input = mock(Input.class);
    @Mock
    private Sound sound =  mock(Sound.class);
    @Mock
    private Bitmap bitmap = mock(Bitmap.class);
    @Mock
    private ElapsedTime elapsedTime = mock(ElapsedTime.class);
    @Mock
    private CharacterCard cTest = mock(CharacterCard.class);
    @Mock
    private AudioManager audioManager = mock(AudioManager.class);


    @Before
    public void setup() {
        when(game.getAssetManager()).thenReturn(assetManager);
        when(game.getInput()).thenReturn(input);
        when(game.getScreenHeight()).thenReturn(1080);
        when(game.getScreenWidth()).thenReturn(1920);
        ScreenManager screenManager = new ScreenManager(game);
        when(game.getScreenManager()).thenReturn(screenManager);
        when(assetManager.getBitmap(any(String.class))).thenReturn(bitmap);
        when(game.getAssetManager().getSound("zoom-in")).thenReturn(sound);
        when(game.getAudioManager()).thenReturn(audioManager);
    }

    @Test
    public void testRemoveCardFromDeck(){
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
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
    public void testAddCardToDeck(){
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);

        int testDeckSize = 0;

        for(int i = 0; i <testDeckSize; i++){
            deckEditorScreen.addCardToDeck(cTest);
        }


        assertEquals(deckEditorScreen.getDeck().size(), testDeckSize, testDeckSize);
        deckEditorScreen.addCardToDeck(cTest);
        assertEquals(deckEditorScreen.getDeck().size(), testDeckSize+1, testDeckSize+1);
    }

    @Test
    public void testRightButton_LargeCardCollection(){
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        int cardCollectionSize = 30; //COLLECTION SIZE

        float posX = deckEditorScreen.getRightButton().position.x;
        float posY = deckEditorScreen.getRightButton().position.y;
        List touchEvents = createTouchEvent_TOUCH_UP(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < cardCollectionSize; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }

        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertNotEquals(originalNumber, deckEditorScreen.getNumDisplay());
    }

    @Test
    public void testRightButton_SmallCardCollect() {
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        int cardCollectionSize = 1; //COLLECTION SIZE
        float posX = deckEditorScreen.getRightButton().position.x;
        float posY = deckEditorScreen.getRightButton().position.y;
        List touchEvents = createTouchEvent_TOUCH_UP(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < cardCollectionSize; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }

        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertEquals(originalNumber, deckEditorScreen.getNumDisplay());
    }

    @Test
    public void testRightButton_NoCardCollection() {
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        int cardCollectionSize = 0; //COLLECTION SIZE
        float posX = deckEditorScreen.getRightButton().position.x;
        float posY = deckEditorScreen.getRightButton().position.y;
        List touchEvents = createTouchEvent_TOUCH_UP(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < cardCollectionSize; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }

        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertEquals(originalNumber, deckEditorScreen.getNumDisplay());
    }

    @Test
    public void testLeftButton_LargeCardCollection(){
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        int cardCollectionSize = 30; //COLLECTION SIZE

        float posX = deckEditorScreen.getLeftButton().position.x;
        float posY = deckEditorScreen.getLeftButton().position.y;
        List touchEvents = createTouchEvent_TOUCH_UP(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < cardCollectionSize; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }

        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertEquals(originalNumber, deckEditorScreen.getNumDisplay());
    }

    @Test
    public void testLeftButton_SmallCardCollect() {
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        int cardCollectionSize = 1; //COLLECTION SIZE
        float posX = deckEditorScreen.getLeftButton().position.x;
        float posY = deckEditorScreen.getLeftButton().position.y;
        List touchEvents = createTouchEvent_TOUCH_UP(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < cardCollectionSize; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }

        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertEquals(originalNumber, deckEditorScreen.getNumDisplay()); //Not move
    }

    @Test
    public void testLeftButton_NoCardCollect() {
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        int cardCollectionSize = 0; //COLLECTION SIZE
        float posX = deckEditorScreen.getLeftButton().position.x;
        float posY = deckEditorScreen.getLeftButton().position.y;
        List touchEvents = createTouchEvent_TOUCH_UP(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        for (int i = 0; i < cardCollectionSize; i++) {
            deckEditorScreen.getCardCollection().add(cTest);
        }

        int originalNumber = deckEditorScreen.getNumDisplay();
        deckEditorScreen.update(elapsedTime);
        assertEquals(originalNumber, deckEditorScreen.getNumDisplay()); //Not move
    }

    @Test
    public void testClearDeck_NoDeck(){
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        deckEditorScreen.clearDeck();
        assertEquals(deckEditorScreen.getDeck().size(),0.0f,0.0f);
    }

    @Test
    public void testClearDeck_FullDeck(){
        DeckEditorScreen deckEditorScreen = new DeckEditorScreen(game);
        for(int i = 0; i <deckEditorScreen.getDeck().size(); i++){
            deckEditorScreen.addCardToDeck(cTest);
        }
        deckEditorScreen.clearDeck();
        assertEquals(deckEditorScreen.getDeck().size(),0.0f,0.0f);
    }


    //Method for getting touch events
    public List createTouchEvent_TOUCH_UP(float touchX, float touchY) {
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = TouchEvent.TOUCH_UP;
        touchEvent.x = touchX;
        touchEvent.y = touchY;

        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchEvent);
        return touchEvents;
    }

    //Method for getting touch events
    public List createTouchEvent_TOUCH_SINGLE_TAP(float touchX, float touchY) {
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = TouchEvent.TOUCH_SINGLE_TAP;
        touchEvent.x = touchX;
        touchEvent.y = touchY;

        List<TouchEvent> touchEvents = new ArrayList<>();
        touchEvents.add(touchEvent);
        return touchEvents;
    }




}
