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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * CharacterCard class tests
 * <p>
 * Created by Matthew McCleave
 */
@RunWith(AndroidJUnit4.class)
public class CharacterCardTest {
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
    public void CharacterCard_Constructor_Test(){
        //Setup
        CharacterCard characterCard = new CharacterCard(200, 400, mainScreen,testCharacterCardStats);
        //Testing
        assertTrue(characterCard.getmHP() == 10);
        assertTrue(characterCard.getmAttackDmg() == 2);
        assertTrue(characterCard.getmEquipedCard() == null);
    }

    @Test
    public void CharacterCard_setmHp_Test(){
        //Setup
        CharacterCard characterCard = new CharacterCard(200, 400, mainScreen,testCharacterCardStats);
        //Testing
        characterCard.setmHP(100);
        assertTrue(characterCard.getmHP() == 100);
    }

    @Test
    public void CharacterCard_setmAttackDmg_Test(){
        //Setup
        CharacterCard characterCard = new CharacterCard(200, 400, mainScreen,testCharacterCardStats);
        //Testing
        characterCard.setmAttackDmg(70);
        assertTrue(characterCard.getmAttackDmg() == 70);
    }

    @Test
    public void CharacterCard_setmEquipedCard_Test(){
        //Setup
        CharacterCard characterCard = new CharacterCard(200, 400, mainScreen,testCharacterCardStats);
        EquipCardStats equipCardStats = new EquipCardStats("Bow", 0, "+1 Attack to equipped Mob", 30 , 1);
        EquipCard equipCard = new EquipCard(100, 100, mainScreen, equipCardStats);
        //Testing
        characterCard.setmEquipedCard(equipCard);
        assertTrue(characterCard.getmEquipedCard() != null);
        assertTrue(characterCard.getmEquipedCard().getCardName().equals("Bow"));
    }
}
