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
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * UtilityCard Tests
 * Created Samuel Brown
 */
@RunWith(AndroidJUnit4.class)
    public class UtilityCardTest {
    private Context context;
    private DemoGame game;
    private MainGameScreen mainGameScreen;
    private UtilityCardStats testUtilityCardStats;
    private GameBoard gameBoard;

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
        mainGameScreen = new MainGameScreen(game);
        game.getScreenManager().addScreen(mainGameScreen);
        gameBoard = new GameBoard(game.getPlayer1(), game.getPlayer2(), game.getScreenManager().getCurrentScreen());
        testUtilityCardStats = new UtilityCardStats("Potion of Healing", 0, "Heal by 2 HP", 32, 2);

    }

    @Test
    public void UtilityCard_Constructor_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        assertTrue(utilityCard.position.x == 100);
        assertTrue(utilityCard.position.y == 100);
    }

    @Test
    public void UtilityCard_runCardAnimation_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        assertTrue(utilityCard.getWidth() != 0);
        assertTrue(utilityCard.getHeight() != 0);

        while (utilityCard.getFlipTimer() >= 0) {
            utilityCard.runCardAnimation();
        }

        assertTrue(utilityCard.getFlipTimer() == -1);
        assertTrue(utilityCard.getWidth() == 0);
        assertTrue(utilityCard.getHeight() == 0);
    }

    @Test
    public void UtilityCard_isAnimationInProgress_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        assertFalse(utilityCard.isAnimationInProgress());
    }

    @Test
    public void UtilityCard_setAnimationInProgress_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        utilityCard.setAnimationInProgress(true);
        assertTrue(utilityCard.isAnimationInProgress() == true);
    }

    @Test
    public void UtilityCard_isAnimationFinished_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        assertFalse(utilityCard.isAnimationFinished());
    }

    @Test
    public void UtilityCard_setAnimationFinished_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        utilityCard.setAnimationFinished(true);
        assertTrue(utilityCard.isAnimationFinished());
    }

    @Test
    public void UtilityCard_damageEnemyHP_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        int originalEnemyHP = gameBoard.getInactivePlayer().getmPlayerHealth();
        utilityCard.damageEnemyHP(gameBoard);
        assertTrue(gameBoard.getInactivePlayer().getmPlayerHealth() != originalEnemyHP);
    }

    @Test
    public void UtilityCard_healPlayerHP_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        //Testing
        int originalAllyHP = gameBoard.getActivePlayer().getmPlayerHealth();
        utilityCard.healPlayerHP(gameBoard);
        assertTrue(gameBoard.getActivePlayer().getmPlayerHealth() != originalAllyHP);
    }

    @Test
    public void UtilityCard_damageEnemyMobs_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.BOTTOM_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobHP = mobContainerTester.getContents().getHealthPoints();
        utilityCard.damageEnemyMobs(gameBoard);
        assertTrue(mobContainerTester.getContents().getHealthPoints() < originalMobHP);
     }

    @Test
    public void UtilityCard_healAllyMobs_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobHP = mobContainerTester.getContents().getHealthPoints();
        utilityCard.healAllyMobs(gameBoard);
        assertTrue(mobContainerTester.getContents().getHealthPoints() > originalMobHP);
    }

    @Test
    public void UtilityCard_weakenEnemyMobs_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.BOTTOM_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobAttack = mobContainerTester.getContents().getAttackDamage();
        utilityCard.weakenEnemyMobs(gameBoard);
        assertTrue(mobContainerTester.getContents().getAttackDamage() < originalMobAttack);
    }

    @Test
    public void UtilityCard_strengthenAllyMobs_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobAttack = mobContainerTester.getContents().getAttackDamage();
        utilityCard.strengthenAllyMobs(gameBoard);
        assertTrue(mobContainerTester.getContents().getAttackDamage() > originalMobAttack);
    }

    @Test
    public void UtilityCard_runUtilityEffectCardID_32_36_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        utilityCard.setCardID(32);
        int originalAllyHP = gameBoard.getActivePlayer().getmPlayerHealth();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(gameBoard.getActivePlayer().getmPlayerHealth() != originalAllyHP);
        utilityCard.setCardID(32);
        originalAllyHP = gameBoard.getActivePlayer().getmPlayerHealth();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(gameBoard.getActivePlayer().getmPlayerHealth() != originalAllyHP);
    }

    @Test
    public void UtilityCard_runUtilityEffectCardID_33_34_35_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);
        utilityCard.setCardID(33);
        int originalEnemyHP = gameBoard.getInactivePlayer().getmPlayerHealth();
        //Testing
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(gameBoard.getInactivePlayer().getmPlayerHealth() != originalEnemyHP);

        //Setup
        utilityCard.setCardID(34);
        originalEnemyHP = gameBoard.getInactivePlayer().getmPlayerHealth();
        //Testing
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(gameBoard.getInactivePlayer().getmPlayerHealth() != originalEnemyHP);

        //Setup
        utilityCard.setCardID(35);
        originalEnemyHP = gameBoard.getInactivePlayer().getmPlayerHealth();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(gameBoard.getInactivePlayer().getmPlayerHealth() != originalEnemyHP);
    }

    @Test
    public void UtilityCard_runUtilityEffectCardID_37_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.BOTTOM_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);
        utilityCard.setCardID(37);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobHP = mobContainerTester.getContents().getHealthPoints();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(mobContainerTester.getContents().getHealthPoints() < originalMobHP);
    }

    @Test
    public void UtilityCard_runUtilityEffectCardID_38_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);
        utilityCard.setCardID(38);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobHP = mobContainerTester.getContents().getHealthPoints();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(mobContainerTester.getContents().getHealthPoints() > originalMobHP);
    }

    @Test
    public void UtilityCard_runUtilityEffectCardID_39_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.BOTTOM_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);
        utilityCard.setCardID(39);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobAttack = mobContainerTester.getContents().getAttackDamage();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(mobContainerTester.getContents().getAttackDamage() < originalMobAttack);
    }

    @Test
    public void UtilityCard_runUtilityEffectCardID_40_Test() {
        //Setup
        UtilityCard utilityCard = new UtilityCard(100, 100, mainGameScreen, testUtilityCardStats);

        CharacterCardStats characterCardStatsTester = new CharacterCardStats("Dragon", 2, "Its a dragon", 5, 10, 20);
        CharacterCard characterCardTester = new CharacterCard(100, 100, mainGameScreen, characterCardStatsTester);
        Mob mobTester = new Mob(100, 100, mainGameScreen, characterCardTester);

        MobContainer mobContainerTester = new MobContainer(100,100, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        gameBoard.getFieldContainers().add(mobContainerTester);
        mobContainerTester.placeCard(mobTester);
        utilityCard.setCardID(40);

        //Testing
        assertTrue(mobContainerTester.getContents() != null);
        assertFalse(gameBoard.isPlayer1Turn());
        int originalMobAttack = mobContainerTester.getContents().getAttackDamage();
        utilityCard.runUtilityEffect(gameBoard);
        assertTrue(mobContainerTester.getContents().getAttackDamage() > originalMobAttack);
    }
}
