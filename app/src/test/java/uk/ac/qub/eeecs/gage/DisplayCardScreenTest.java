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
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameScreens.DisplayCardsScreen;


import static org.junit.Assert.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * DeckEditorScreen Tests
 * Created Andrew Brown
 */

public class DisplayCardScreenTest {


    @Mock
    private Game game = mock(Game.class);
    @Mock
    private AssetManager assetManager = mock(AssetManager.class);
    @Mock
    private Input input = mock(Input.class);
    @Mock
    private ElapsedTime elapsedTime = mock(ElapsedTime.class);


    @Before
    public void setup() {
        when(game.getAssetManager()).thenReturn(assetManager);
        when(game.getInput()).thenReturn(input);
        when(game.getScreenHeight()).thenReturn(1080);
        when(game.getScreenWidth()).thenReturn(1920);
    }


    @Test
    public void ScrollScreen_MoveDown(){
        DisplayCardsScreen displayCardsScreen = new DisplayCardsScreen("CardsDisplay", game);
        float posX = 0;
        float posY = -1000.0f;

        List touchEvents = createTouchEvent_TOUCH_DRAGGED(posX, posY);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        float originalValue = displayCardsScreen.getCardLayerViewport().y;
        displayCardsScreen.update(elapsedTime);
        float afterValue = displayCardsScreen.getCardLayerViewport().y;
        assertNotEquals(originalValue, afterValue);
    }
    @Test
    public void ScrollScreen_MoveUp(){
        DisplayCardsScreen displayCardsScreen = new DisplayCardsScreen("CardsDisplay", game);
        float posX = 0;
        float posY = 1000.0f;

        displayCardsScreen.getCardLayerViewport().y = 0;
        List touchEvents = createTouchEvent_TOUCH_DRAGGED(posX, posY);
        when(input.getTouchEvents()).thenReturn(touchEvents);

        float originalValue = displayCardsScreen.getCardLayerViewport().y;
        displayCardsScreen.update(elapsedTime);
        float afterValue = displayCardsScreen.getCardLayerViewport().y;
        assertNotEquals(originalValue, afterValue);
    }

    @Test
    public void ScrollScreen_NoMove(){
        DisplayCardsScreen displayCardsScreen = new DisplayCardsScreen("CardsDisplay", game);
        float originalValue = displayCardsScreen.getCardLayerViewport().y;
        displayCardsScreen.update(elapsedTime);
        float afterValue = displayCardsScreen.getCardLayerViewport().y;
        assertEquals(originalValue, afterValue);
    }

    @Test
    public void MovingBackgroundTest(){
        DisplayCardsScreen displayCardsScreen = new DisplayCardsScreen("CardsDisplay", game);
        float posX = 0;
        float posY = displayCardsScreen.getCardLayerViewport().y;

        List touchEvents = createTouchEvent_TOUCH_DRAGGED(posX, posY);

        when(input.getTouchEvents()).thenReturn(touchEvents);

        float originalValue = displayCardsScreen.getBackgroundLayerViewport().y;
        displayCardsScreen.update(elapsedTime);
        float afterValue = displayCardsScreen.getBackgroundLayerViewport().y;
        assertNotEquals(originalValue, afterValue);
    }


    @Test
    public void scrollLimiterTest_Down(){
        DisplayCardsScreen displayCardsScreen = new DisplayCardsScreen("CardsDisplay", game);
        float posYCardLayerViewport = -1000.0f;
        displayCardsScreen.getCardLayerViewport().y = posYCardLayerViewport;
        float originalValue = displayCardsScreen.getCardLayerViewport().y;

        assertEquals(originalValue, -1000.0f);
        displayCardsScreen.update(elapsedTime);
        float afterValue = displayCardsScreen.getCardLayerViewport().y;
        assertNotEquals(originalValue, afterValue);
    }

    @Test
    public void scrollLimiterTest_Up(){
        DisplayCardsScreen displayCardsScreen = new DisplayCardsScreen("CardsDisplay", game);
        float posYCardLayerViewport = 1000.0f;
        displayCardsScreen.getCardLayerViewport().y = posYCardLayerViewport;
        float originalValue = displayCardsScreen.getCardLayerViewport().y;

        assertEquals(originalValue, 1000.0f);
        displayCardsScreen.update(elapsedTime);
        float afterValue = displayCardsScreen.getCardLayerViewport().y;
        assertNotEquals(originalValue, afterValue);
    }



    //Methods for touch events in tests
    public List createTouchEvent_TOUCH_DRAGGED(float touchX, float touchY) {
        TouchEvent touchEvent = new TouchEvent();
        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
        touchEvent.x = touchX;
        touchEvent.y = touchY;

        List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
        touchEvents.add(touchEvent);
        return touchEvents;
    }
}
