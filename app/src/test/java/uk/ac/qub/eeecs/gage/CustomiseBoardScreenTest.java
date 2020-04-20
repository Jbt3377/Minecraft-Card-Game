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
import uk.ac.qub.eeecs.game.GameScreens.CustomBoardScreen;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

/**
 * CustomBoardScreen Tests
 * Created Samuel Brown
 */
@RunWith(MockitoJUnitRunner.class)
    public class CustomiseBoardScreenTest {

    @Mock
    private Game game = mock(Game.class);
    @Mock
    private AssetManager assetManager;
    @Mock
    private Input input = mock(Input.class);
    @Mock
    private ElapsedTime elapsedTime = mock(ElapsedTime.class);
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
        when(game.getAudioManager()).thenReturn(audioManager);

    }


    @Test
    public void CustomBoardScreen_rightArrowPushed_Test() {
        CustomBoardScreen customBoardScreen = new CustomBoardScreen(game);
        float buttonPositionX = customBoardScreen.getRightBoardChange().position.x;
        float buttonPositionY = customBoardScreen.getRightBoardChange().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);

        when(input.getTouchEvents()).thenReturn((touchEvents));

        customBoardScreen.update(elapsedTime);
        assertNotEquals(0, customBoardScreen.getBoardCounter());

    }

    @Test
    public void CustomBoardScreen_leftArrowPushed_Test() {
        CustomBoardScreen customBoardScreen = new CustomBoardScreen(game);
        float buttonPositionX = customBoardScreen.getLeftBoardChange().position.x;
        float buttonPositionY = customBoardScreen.getLeftBoardChange().position.y;
        List touchEvents = setupTouchEvents(buttonPositionX, buttonPositionY);

        when(input.getTouchEvents()).thenReturn((touchEvents));
        customBoardScreen.update(elapsedTime);
        assertNotEquals(0, customBoardScreen.getBoardCounter());
    }


    @Test
    public void CustomBoardScreen_boardSetter_toSpruceGameBoard_Test() {
        CustomBoardScreen customBoardScreen = new CustomBoardScreen(game);
        String testText = "Spruce Game Board";
        int testBoardCounter = 0;
        customBoardScreen.boardSetter(testBoardCounter);
        assertTrue(customBoardScreen.getBoardText().equals(testText));

    }

    @Test
    public void CustomBoardScreen_boardSetter_toStoneGameBoard_Test() {
        CustomBoardScreen customBoardScreen = new CustomBoardScreen(game);
        String testText = "Stone Game Board";
        int testBoardCounter = 1;
        customBoardScreen.boardSetter(testBoardCounter);
        assertTrue(customBoardScreen.getBoardText().equals(testText));
    }

    @Test
    public void CustomBoardScreen_boardSetter_toQuartzGameBoard_Test() {
        CustomBoardScreen customBoardScreen = new CustomBoardScreen(game);
        String testText = "Quartz Game Board";
        int testBoardCounter = 2;
        customBoardScreen.boardSetter(testBoardCounter);
        assertTrue(customBoardScreen.getBoardText().equals(testText));
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
