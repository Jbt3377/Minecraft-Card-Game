package uk.ac.qub.eeecs.gage;

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
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameScreens.ChangeDeckScreen;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangeDeckScreenTest {

    @Mock
    private Game game = mock(Game.class);
    @Mock
    private AssetManager assetManager;
    @Mock
    private Input input = mock(Input.class);
    @Mock
    private ElapsedTime elapsedTime = mock(ElapsedTime.class);
    @Mock
    private DeckManager deckManager = mock(DeckManager.class);
    @Mock
    private AudioManager audioManager = mock(AudioManager.class);



    @Before
    public void setup() {
        when(game.getAssetManager()).thenReturn(assetManager);
        when(game.getScreenWidth()).thenReturn(1920);
        when(game.getScreenHeight()).thenReturn(1080);
        ScreenManager screenManager = new ScreenManager(game);
        when(game.getScreenManager()).thenReturn(screenManager);
        when(game.getInput()).thenReturn(input);
        when(game.getmDeckManager()).thenReturn(deckManager);
        when(game.getAudioManager()).thenReturn(audioManager);
    }

    @Test
    public void ChangeDeckScreen_deckButton1Pushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getDeckButton1().position.x;
        float buttonPositionY = changeDeckScreen.getDeckButton1().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);

        changeDeckScreen.update(elapsedTime);
        assertEquals("Steve's Arsenal", game.getPlayer1().getmSelectedDeckName());
    }

    @Test
    public void ChangeDeckScreen_deckButton2Pushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getDeckButton2().position.x;
        float buttonPositionY = changeDeckScreen.getDeckButton2().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);

        changeDeckScreen.update(elapsedTime);
        assertEquals("The Village", game.getPlayer1().getmSelectedDeckName());
    }

    @Test
    public void ChangeDeckScreen_deckButton3Pushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getDeckButton3().position.x;
        float buttonPositionY = changeDeckScreen.getDeckButton3().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);

        changeDeckScreen.update(elapsedTime);
        assertEquals("Bane of Herobrine", game.getPlayer1().getmSelectedDeckName());
    }

    @Test
    public void ChangeDeckScreen_deckButton4Pushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getDeckButton4().position.x;
        float buttonPositionY = changeDeckScreen.getDeckButton4().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);

        changeDeckScreen.update(elapsedTime);
        assertEquals("Old McDonald's Farm", game.getPlayer1().getmSelectedDeckName());
    }

    @Test
    public void ChangeDeckScreen_deckButton5Pushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getDeckButton5().position.x;
        float buttonPositionY = changeDeckScreen.getDeckButton5().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);

        changeDeckScreen.update(elapsedTime);
        assertEquals("Hefty Bois", game.getPlayer1().getmSelectedDeckName());
    }

    @Test
    public void ChangeDeckScreen_deckButton6Pushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getDeckButton6().position.x;
        float buttonPositionY = changeDeckScreen.getDeckButton6().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);

        changeDeckScreen.update(elapsedTime);
        assertEquals("The End", game.getPlayer1().getmSelectedDeckName());
    }

    @Test
    public void ChangeDeckScreen_customDeckButtonPushed_Test() {
        ChangeDeckScreen changeDeckScreen = new ChangeDeckScreen("customBoardScreen", game);
        float buttonPositionX = changeDeckScreen.getCustomDeckButton().position.x;
        float buttonPositionY = changeDeckScreen.getCustomDeckButton().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);
        Human player1 = new Human("");

        when(input.getTouchEvents()).thenReturn((touchEvents));
        when(game.getPlayer1()).thenReturn(player1);
        when(deckManager.isCustomDeckAdded()).thenReturn(true);

        changeDeckScreen.update(elapsedTime);
        assertEquals("Custom Deck", game.getPlayer1().getmSelectedDeckName());
    }

    //Method to allow calling of touch events for tests
    private List setupTouchEvents(float touchX, float touchY) {
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = TouchEvent.TOUCH_UP;
        touchEvent.x = touchX;
        touchEvent.y = touchY;
        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
        touchEvents.add(touchEvent);
        return touchEvents;

    }
}

