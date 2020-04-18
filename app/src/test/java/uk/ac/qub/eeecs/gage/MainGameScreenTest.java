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
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameScreens.CustomBoardScreen;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class MainGameScreenTest {

    @Mock
    private Game game = mock(Game.class);
    @Mock
    private AssetManager assetManager;
    @Mock
    private Input input = mock(Input.class);
    @Mock
    private ElapsedTime elapsedTime = mock(ElapsedTime.class);

    @Before
    public void setup() {
        when(game.getAssetManager()).thenReturn(assetManager);
        when(game.getScreenWidth()).thenReturn(1920);
        when(game.getScreenHeight()).thenReturn(1080);
        ScreenManager screenManager = new ScreenManager(game);
        when(game.getScreenManager()).thenReturn(screenManager);
        when(game.getInput()).thenReturn(input);

    }

    @Test
    public void MainGameScreen_Construct_Test() {

    }
}
