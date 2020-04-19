package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Bitmap;
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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * CardBitmapFactoryTest class tests
 * <p>
 * Created by Matthew McCleave
 */
@RunWith(AndroidJUnit4.class)
public class CardBitmapFactoryTest {

    private Context context;
    private DemoGame game;
    private MainGameScreen mainScreen;

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


    }

    @Test
    public void CardBitmapFactory_returnCharacterCardBitmap_Test() {
        //Setup
        CharacterCardStats testCharacterCardStats1= new CharacterCardStats("Cow", 3, "Moo", 0, 10, 2);
        CharacterCardStats testCharacterCardStats2 = new CharacterCardStats("Dragon", 4, "Flame", 1, 20, 10);
        CharacterCardStats testCharacterCardStats3 = new CharacterCardStats("Creeper", 3, "Boom", 2, 12,3);
        CharacterCard card1 = new CharacterCard(50, 100, mainScreen, testCharacterCardStats1);
        CharacterCard card2 = new CharacterCard(500, 500, mainScreen, testCharacterCardStats2);
        CharacterCard card3 = new CharacterCard(250,250, mainScreen, testCharacterCardStats3);
        //Testing
        Bitmap card1Bitmap = card1.getBitmap();
        assertTrue(card1Bitmap != null);
        Bitmap card2Bitmap = card2.getBitmap();
        assertTrue(card2Bitmap != null);
        Bitmap card3Bitmap = card3.getBitmap();
        assertTrue(card3Bitmap != null);

    }

    @Test
    public void CardBitmapFactory_returnEquipCardBitmap_Test() {
        //Setup
        EquipCardStats equipCardStats1 = new EquipCardStats("Sword", 2, "+4 Attack to equipped Mob", 29,4);
        EquipCardStats equipCardStats2 = new EquipCardStats("Bow", 0, "+1 Attack to equipped Mob", 30,1);
        EquipCardStats equipCardStats3 = new EquipCardStats("Crossbow", 2, "+5 Attack to equipped Mob", 31,5);

        EquipCard card1 = new EquipCard(400,400,mainScreen,equipCardStats1);
        EquipCard card2 = new EquipCard(440,700,mainScreen,equipCardStats2);
        EquipCard card3 = new EquipCard(200,100,mainScreen,equipCardStats3);


        //Testing
        Bitmap card1Bitmap = card1.getBitmap();
        assertTrue(card1Bitmap != null);
        Bitmap card2Bitmap = card2.getBitmap();
        assertTrue(card2Bitmap != null);
        Bitmap card3Bitmap = card3.getBitmap();
        assertTrue(card3Bitmap != null);

    }

    @Test
    public void CardBitmapFactory_returnUtilityCardBitmap_Test() {
        //Setup
        UtilityCardStats utilityCardStats1 = new UtilityCardStats("Potion of Extra Harming", 2, "Damage by 4 HP", 35, 4);
        UtilityCardStats utilityCardStats2 = new UtilityCardStats("Potion of Extra Healing", 2, "Heal by 4 HP", 36, 4);
        UtilityCardStats utilityCardStats3 = new UtilityCardStats("Potion of Lesser Harming", 0, "Damage by 1 HP", 34, 1);

        UtilityCard card1 = new UtilityCard(100,100,mainScreen,utilityCardStats1);
        UtilityCard card2 = new UtilityCard(200,200,mainScreen,utilityCardStats2);
        UtilityCard card3 = new UtilityCard(300,300,mainScreen,utilityCardStats3);


        //Testing
        Bitmap card1Bitmap = card1.getBitmap();
        assertTrue(card1Bitmap != null);
        Bitmap card2Bitmap = card2.getBitmap();
        assertTrue(card2Bitmap != null);
        Bitmap card3Bitmap = card3.getBitmap();
        assertTrue(card3Bitmap != null);;

    }


    @Test
    public void CardBitmapFactory_returnMobBitmap_Test() {
        //Setup
        CharacterCardStats testCharacterCardStats1= new CharacterCardStats("Cow", 3, "Moo", 0, 10, 2);
        CharacterCardStats testCharacterCardStats2 = new CharacterCardStats("Dragon", 4, "Flame", 1, 20, 10);
        CharacterCardStats testCharacterCardStats3 = new CharacterCardStats("Creeper", 3, "Boom", 2, 12,3);
        CharacterCard card1 = new CharacterCard(50, 100, mainScreen, testCharacterCardStats1);
        CharacterCard card2 = new CharacterCard(500, 500, mainScreen, testCharacterCardStats2);
        CharacterCard card3 = new CharacterCard(250,250, mainScreen, testCharacterCardStats3);
        Mob mob1 = new Mob(100,200,mainScreen,card1);
        Mob mob2 = new Mob(200,300,mainScreen,card2);
        Mob mob3 = new Mob(300,400,mainScreen,card3);

        Bitmap mob1Bitmap = mob1.getBitmap();
        assertTrue(mob1Bitmap != null);

        Bitmap mob2Bitmap = mob2.getBitmap();
        assertTrue(mob2Bitmap != null);

        Bitmap mob3Bitmap = mob3.getBitmap();
        assertTrue(mob3Bitmap != null);

    }


}