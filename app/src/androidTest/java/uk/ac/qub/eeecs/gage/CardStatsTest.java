package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class CardStatsTest {

    private Context context;
    private DemoGame game;
    //private MainGameScreen mainScreen;

    @Before
    public void setup() {

        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        game.mScreenManager = new ScreenManager(game);
        //mainScreen = new MainGameScreen(game);
        //game.mScreenManager.addScreen(mainScreen);

    }

    @Test
    public void testConstructor_CharacterCardStats_variable(){
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        assertTrue(testCharacterCard.getName().equals("Cow"));
        assertTrue(testCharacterCard.getManacost() == 3);
        assertTrue(testCharacterCard.getDescText().equals("Moo"));
        assertTrue(testCharacterCard.getId() == 0);

    }
}
