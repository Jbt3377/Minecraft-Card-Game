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
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.TurnManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

/**
 * Turn Manager class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class TurnManagerTest {

    private DemoGame game;
    private MainGameScreen mainScreen;

    @Before
    public void setup() {

        Context context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
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

    }


    @Test
    public void test_turnManagerConstructorPVP(){

        GameBoard gameBoard = new GameBoard(new Human("Steve's Arsenal"),
                new Human("The End"), mainScreen);

        TurnManager testTurnManager = new TurnManager(gameBoard, mainScreen, game);

    }

    @Test
    public void test_turnManagerConstructorPVE(){

        GameBoard gameBoard = new GameBoard(new Human("Steve's Arsenal"),
                new Ai("The End"), mainScreen);

        TurnManager testTurnManager = new TurnManager(gameBoard, mainScreen, game);

    }
}
