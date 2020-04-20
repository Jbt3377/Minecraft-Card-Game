package uk.ac.qub.eeecs.gage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.Music;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;
import uk.ac.qub.eeecs.game.GameScreens.CustomBoardScreen;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static junit.framework.Assert.assertEquals;
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
    @Mock
    private DeckManager deckManager;
    @Mock
    private Player player1 = mock(Player.class);
    @Mock
    private Player player2 = mock(Ai.class);
    @Mock
    private GameBoard gameBoard = mock(GameBoard.class);



    @Before
    public void setup() {

        when(game.getAssetManager()).thenReturn(assetManager);
        assetManager.loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
        assetManager.loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");
        assetManager.customLoadCard("txt/assets/AllCardStats.JSON");

        when(game.getScreenWidth()).thenReturn(1920);
        when(game.getScreenHeight()).thenReturn(1080);
        ScreenManager screenManager = new ScreenManager(game);
        when(game.getScreenManager()).thenReturn(screenManager);
        when(game.getInput()).thenReturn(input);
//        deckManager = new DeckManager(assetManager.getAllCardStats());
//        when(game.getmDeckManager()).thenReturn(deckManager);
//        when(player1.getmSelectedDeckName()).thenReturn("Steve's Arsenal");
//        when(player2.getmSelectedDeckName()).thenReturn("Steve's Arsenal");

    }

//    @Test
//    public void MainGameScreen_Construct_Test() {
//        MainGameScreen mainGameScreen = new MainGameScreen(game);
//        when(mainGameScreen.getGame()).thenReturn(game);
//        when(gameBoard.c)
//        assertEquals(game, mainGameScreen.getGame());
//    }


}
