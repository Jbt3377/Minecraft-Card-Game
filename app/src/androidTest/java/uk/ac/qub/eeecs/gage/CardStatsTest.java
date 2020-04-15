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
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)

//Tested by Matthew McCleave
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
        //Setup
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        //Testing
        assertTrue(testCharacterCard.getName().equals("Cow"));
        assertTrue(testCharacterCard.getManacost() == 3);
        assertTrue(testCharacterCard.getDescText().equals("Moo"));
        assertTrue(testCharacterCard.getId() == 0);
        assertTrue(testCharacterCard.getHp() == 10);
        assertTrue(testCharacterCard.getAttack() == 2);
    }

    @Test
    public void testConstructor_EquipCardStats_variable(){
        //Setup
        EquipCardStats testEquipCardStats = new EquipCardStats("Double Swords", 3, "+8 Attack to equipped Mob", 26);
        //Testing
        assertTrue(testEquipCardStats.getName().equals("Double Swords"));
        assertTrue(testEquipCardStats.getManacost() == 3);
        assertTrue(testEquipCardStats.getDescText().equals("+8 Attack to equipped Mob"));
        assertTrue(testEquipCardStats.getId() == 26);
    }

    @Test
    public void testConstructor_UtilityCardStats_variable(){
        //Setup
        UtilityCardStats testUtilityCardStats = new UtilityCardStats("Potion of Extra Healing", 4, "Heal all by 5 HP", 35);
        //Testing
        assertTrue(testUtilityCardStats.getName().equals("Potion of Extra Healing"));
        assertTrue(testUtilityCardStats.getManacost() == 4);
        assertTrue(testUtilityCardStats.getDescText().equals("Heal all by 5 HP"));
        assertTrue(testUtilityCardStats.getId() == 35);
    }



    @Test
    public void testSetName(){
        //Setup
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        String testName = "Test";
        //Testing
        testCharacterCard.setName(testName);
        assertTrue(testCharacterCard.getName().equals("Test"));
    }

    @Test
    public void testSetManaCost(){
        //Setup
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        int testMana = 20;
        //Testing
        testCharacterCard.setManacost(testMana);
        assertTrue(testCharacterCard.getManacost() == 20);
    }

    @Test
    public void testSetDescText(){
        //Setup
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        String testDesc = "Test";
        //Testing
        testCharacterCard.setDescText(testDesc);
        assertTrue(testCharacterCard.getDescText().equals("Test"));
    }

    @Test
    public void testSetHp(){
        //Setup
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        int testHp = 40;
        //Testing
        testCharacterCard.setHp(testHp);
        assertTrue(testCharacterCard.getHp() == 40);
    }

    @Test
    public void testSetAttack(){
        //Setup
        CharacterCardStats testCharacterCard = new CharacterCardStats("Cow",3, "Moo", 0, 10, 2);
        int testAttack = 30;
        //Testing
        testCharacterCard.setAttack(testAttack);
        assertTrue(testCharacterCard.getAttack() == 30);
    }

}
