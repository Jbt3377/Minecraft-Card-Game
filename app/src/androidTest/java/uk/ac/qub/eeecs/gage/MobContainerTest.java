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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * Popup class tests
 *
 * Created by Josh
 */
@RunWith(AndroidJUnit4.class)
public class MobContainerTest {

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
    public void test_mobContainerConstructor_BottomPlayer(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.BOTTOM_PLAYER,
                mainScreen);

        assertTrue(testMobContainer.isEmpty());
        assertNull(testMobContainer.getContents());
        assertEquals(testMobContainer.getContType(), MobContainer.ContainerType.BOTTOM_PLAYER);
        assertTrue(testMobContainer.getX_location() == 600);
        assertTrue(testMobContainer.getY_location() == 500);
        assertEquals(testMobContainer.getBitmap(), game.getAssetManager().getBitmap("ItemFrame"));
    }

    @Test
    public void test_mobContainerConstructor_UtilityCard(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.UTILITY_CARD,
                mainScreen);

        assertTrue(testMobContainer.isEmpty());
        assertNull(testMobContainer.getContents());
        assertEquals(testMobContainer.getContType(), MobContainer.ContainerType.UTILITY_CARD);
        assertTrue(testMobContainer.getX_location() == 600);
        assertTrue(testMobContainer.getY_location() == 500);
        assertEquals(testMobContainer.getBitmap(), game.getAssetManager().getBitmap("ItemFrameUtility"));
    }

    @Test
    public void test_placeCard_Valid(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);

        CharacterCardStats cowCardStats = new CharacterCardStats("Cow", 3, "moo", 0, 10, 2);
        CharacterCard cowCard = new CharacterCard(500, 600, mainScreen, cowCardStats);
        Mob cowMob = new Mob(600, 500, mainScreen, cowCard);

        assertTrue(testMobContainer.isEmpty());
        assertTrue(testMobContainer.placeCard(cowMob));
        assertFalse(testMobContainer.isEmpty());
        assertEquals(testMobContainer.getContents(), cowMob);
    }

    @Test
    public void test_placeCard_Invalid(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);

        // Define First Card
        CharacterCardStats cowCardStats = new CharacterCardStats("Cow", 3, "moo", 0, 10, 2);
        CharacterCard cowCard = new CharacterCard(500, 600, mainScreen, cowCardStats);
        Mob cowMob = new Mob(600, 500, mainScreen, cowCard);

        // Place First Card in Container
        assertTrue(testMobContainer.placeCard(cowMob));

        // Define Second Card
        CharacterCardStats dolphinCardStats = new CharacterCardStats("Dolphin", 1, "Ree", 6, 5, 5);
        CharacterCard dolphinCard = new CharacterCard(500, 600, mainScreen, dolphinCardStats);

        // Assert - Cannot place second card in container
        assertFalse(testMobContainer.isEmpty());
        assertFalse(testMobContainer.placeCard(new Mob(600, 500, mainScreen, dolphinCard)));
        assertEquals(testMobContainer.getContents(), cowMob);
    }

    @Test
    public void test_emptyContainer_WhenFull(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);

        CharacterCardStats cowCardStats = new CharacterCardStats("Cow", 3, "moo", 0, 10, 2);
        CharacterCard cowCard = new CharacterCard(500, 600, mainScreen, cowCardStats);

        assertTrue(testMobContainer.placeCard(new Mob(600, 500, mainScreen, cowCard)));

        testMobContainer.emptyContainer();

        // Assert - Container Emptied
        assertTrue(testMobContainer.isEmpty());
        assertNull(testMobContainer.getContents());
    }

    @Test
    public void test_emptyContainer_WhenEmpty(){

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);

        assertTrue(testMobContainer.isEmpty());

        testMobContainer.emptyContainer();

        // Assert - Container still empty
        assertTrue(testMobContainer.isEmpty());
        assertNull(testMobContainer.getContents());
    }

    @Test
    public void test_checkCharacterEquipCanBeDropped_ValidCharacter(){

        // Test to check if a Character Card can be dropped in a Player Container //

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);
        testMobContainer.setWidth(100);
        testMobContainer.setHeight(100);

        CharacterCardStats cowCardStats = new CharacterCardStats("Cow", 3, "moo", 0, 10, 2);
        CharacterCard cowCard = new CharacterCard(600, 500, mainScreen, cowCardStats);

        // Assert - Character Card can be dropped into Empty Container
        assertTrue(testMobContainer.isEmpty());
        assertTrue(testMobContainer.checkCharacterEquipCanBeDropped(cowCard));
        assertTrue(testMobContainer.isEmpty());
    }

    @Test
    public void test_checkCharacterEquipCanBeDropped_ValidEquip(){

        // Test to check if an Equip Card can be dropped in an occupied Player Container //

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);
        testMobContainer.setWidth(100);
        testMobContainer.setHeight(100);

        // Place Mob in Container - Needed to allow placement of Equip Card
        CharacterCardStats cowCardStats = new CharacterCardStats("Cow", 3, "moo", 0, 10, 2);
        CharacterCard cowCard = new CharacterCard(500, 600, mainScreen, cowCardStats);
        testMobContainer.placeCard(new Mob(600, 500, mainScreen, cowCard));
        assertFalse(testMobContainer.isEmpty());

        // New Equip Card
        EquipCardStats bowCardStats = new EquipCardStats("Bow", 0, "+1 Attack to equipped Mob", 30, 1);
        EquipCard bowCard = new EquipCard(600, 500, mainScreen, bowCardStats);

        // Assert - Equip Card can be dropped into container
        assertTrue(testMobContainer.checkCharacterEquipCanBeDropped(bowCard));
    }

    @Test
    public void test_checkCharacterEquipCanBeDropped_Invalid(){

        // Test to check if a Utility Card can be dropped in a Player Container //

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.TOP_PLAYER,
                mainScreen);
        testMobContainer.setWidth(100);
        testMobContainer.setHeight(100);

        UtilityCardStats healingCardStats = new UtilityCardStats("Potion of Healing", 0, "Heal by 2 HP", 32, 2);
        UtilityCard healingCard = new UtilityCard(100, 100, mainScreen, healingCardStats);

        assertFalse(testMobContainer.checkCharacterEquipCanBeDropped(healingCard));
        assertTrue(testMobContainer.isEmpty());
    }

    @Test
    public void test_checkUtilityCanBeDropped_ValidUtility(){

        // Test to check if a Utility Card can be dropped in a Utility Container //

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.UTILITY_CARD,
                mainScreen);
        testMobContainer.setWidth(100);
        testMobContainer.setHeight(100);

        // Define Utility Card
        UtilityCardStats healingCardStats = new UtilityCardStats("Potion of Healing", 0, "Heal by 2 HP", 32, 2);
        UtilityCard healingCard = new UtilityCard(600, 500, mainScreen, healingCardStats);

        // Assert Utility Card can be added to Empty Utility Card Container
        assertTrue(testMobContainer.isEmpty());
        assertTrue(testMobContainer.checkUtilityCanBeDropped(healingCard));
        assertTrue(testMobContainer.isEmpty());
    }

    @Test
    public void test_checkUtilityCanBeDropped_Invalid(){

        // Test to check if a Character Card can be dropped in a Utility Container //

        MobContainer testMobContainer = new MobContainer(600, 500, MobContainer.ContainerType.UTILITY_CARD,
                mainScreen);
        testMobContainer.setWidth(100);
        testMobContainer.setHeight(100);

        // Define Character Card
        CharacterCardStats cowCardStats = new CharacterCardStats("Cow", 3, "moo", 0, 10, 2);
        CharacterCard cowCard = new CharacterCard(600, 500, mainScreen, cowCardStats);

        // Assert - Cannot drop Character Card in Utility Container
        assertFalse(testMobContainer.checkUtilityCanBeDropped(cowCard));
        assertTrue(testMobContainer.isEmpty());
    }

}
