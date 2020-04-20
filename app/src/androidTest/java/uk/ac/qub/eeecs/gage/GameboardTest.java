package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.DeckManager;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PlayerHand;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;

import static org.junit.Assert.*;

/**
 * GameBoard Tests
 * Created Samuel Brown
 */
@RunWith(AndroidJUnit4.class)
public class GameboardTest {
    private Context context;
    private DemoGame game;
    private MainGameScreen mainGameScreen;
    private GameBoard gameBoard;


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
        mainGameScreen = new MainGameScreen(game);
        game.getScreenManager().addScreen(mainGameScreen);

    }

    @Test
    public void GameBoard_getActivePlayer_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        gameBoard.setIsPlayer1Turn(true);
        assertEquals(player1, gameBoard.getActivePlayer());
    }

    @Test
    public void GameBoard_getInactivePlayer_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");
        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        gameBoard.setIsPlayer1Turn(true);
        assertEquals(player2, gameBoard.getInactivePlayer());
    }

    @Test
    public void GameBoard_getActivePlayerHand_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(gameBoard.getPlayer1Hand(), gameBoard.getActivePlayerHand());
    }

    @Test
    public void GameBoard_getInActivePlayerHand_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(gameBoard.getPlayer2Hand(), gameBoard.getInactivePlayerHand());
    }

    @Test
    public void GameBoard_getActivePlayerMobsOnBoard_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        for (int i = 0; i < gameBoard.getFieldContainers().size(); i++) {
            for (int j = 0; j < gameBoard.getActivePlayersMobsOnBoard().size(); j++) {
                assertTrue(gameBoard.getFieldContainers().get(i).getContents() == gameBoard.getActivePlayersMobsOnBoard().get(j));
            }
        }
    }

    @Test
    public void GameBoard_getInActivePlayerMobsOnBoard_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        for (int i = 0; i < gameBoard.getFieldContainers().size(); i++) {
            for (int j = 0; j < gameBoard.getInactivePlayersMobsOnBoard().size(); j++) {
                assertTrue(gameBoard.getFieldContainers().get(i).getContents() == gameBoard.getInactivePlayersMobsOnBoard().get(j));
            }
        }
    }

    @Test
    public void GameBoard_getActivePlayerMobsOnBoardArray_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        Mob[] testMobArray = gameBoard.getActivePlayersMobsOnBoardArray();
        for (int i = 0; i < gameBoard.getFieldContainers().size(); i++) {
            for (int j = 0; j < gameBoard.getActivePlayersMobsOnBoardArray().length; j++) {
                assertTrue(gameBoard.getFieldContainers().get(i).getContents() == testMobArray[j]);
            }
        }
    }

    @Test
    public void GameBoard_getInactivePlayersMobsOnBoardArray_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        Mob[] testMobArray = gameBoard.getInactivePlayersMobsOnBoardArray();
        for (int i = 0; i < gameBoard.getFieldContainers().size(); i++) {
            for (int j = 0; j < gameBoard.getInactivePlayersMobsOnBoardArray().length; j++) {
                assertTrue(gameBoard.getFieldContainers().get(i).getContents() == testMobArray[j]);
            }
        }
    }

    @Test
    public void GameBoard_decreaseInactivePlayerHP_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        int originalInactivePlayerHP = gameBoard.getInactivePlayer().getmPlayerHealth();
        gameBoard.decreaseInactivePlayerHP(10);
        assertTrue(gameBoard.getInactivePlayer().getmPlayerHealth() < originalInactivePlayerHP);
    }

    @Test
    public void GameBoard_getPlayer1_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(player1, gameBoard.getPlayer1());

    }

    @Test
    public void GameBoard_getPlayer2_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(player2, gameBoard.getPlayer2());

    }

    @Test
    public void GameBoard_getPlayer1Hand_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(gameBoard.getActivePlayerHand(), gameBoard.getPlayer1Hand());
    }

    @Test
    public void GameBoard_getPlayer2Hand_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(gameBoard.getInactivePlayerHand(), gameBoard.getPlayer2Hand());
    }

    @Test
    public void GameBoard_getGameScreen_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(mainGameScreen, gameBoard.getGameScreen());
    }

    @Test
    public void GameBoard_getFieldContainers_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        MobContainer testMobContainer = new MobContainer(0,0, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        ArrayList<MobContainer> testMobContainers = new ArrayList<>();
        testMobContainers.add(testMobContainer);
        gameBoard.setFieldContainers(testMobContainers);

        gameBoard.setIsPlayer1Turn(true);
        assertEquals(testMobContainers, gameBoard.getFieldContainers());
    }

    @Test
    public void GameBoard_getPlayer1MobsOnBoard_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        for (int i = 0; i < gameBoard.getFieldContainers().size(); i++) {
            for (int j = 0; j < gameBoard.getPlayer1MobsOnBoard().size(); j++) {
                assertTrue(gameBoard.getFieldContainers().get(i).getContents() == gameBoard.getPlayer1MobsOnBoard().get(j));
            }
        }
    }

    @Test
    public void GameBoard_getPlayer2MobsOnBoard_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);

        for (int i = 0; i < gameBoard.getFieldContainers().size(); i++) {
            for (int j = 0; j < gameBoard.getPlayer2MobsOnBoard().size(); j++) {
                assertTrue(gameBoard.getFieldContainers().get(i).getContents() == gameBoard.getPlayer2MobsOnBoard().get(j));
            }
        }
    }

    @Test
    public void GameBoard_getUtilityCardContainer_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        MobContainer testMobContainer = new MobContainer(0,0, MobContainer.ContainerType.UTILITY_CARD, mainGameScreen);
        gameBoard.setUtilityCardContainer(testMobContainer);

        assertEquals(testMobContainer, gameBoard.getUtilityCardContainer());
    }

    @Test
    public void GameBoard_proccessCardMagnification_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        game.setMagnificationToggled(true);
        Card testCard = gameBoard.getPlayer1Hand().getPlayerHand().get(1);
        gameBoard.processCardMagnification(testCard, game, gameBoard.getPlayer1Hand());
        assertTrue(game.drawCard);
        assertEquals(testCard.getCardName(), game.getMagnifiedCard().getCardName());
        assertEquals(testCard.getCardID(), game.getMagnifiedCard().getCardID());
    }

    @Test
    public void GameBoard_proccessCardMagnificationRelease_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        game.setMagnificationToggled(true);
        Card testCard = gameBoard.getPlayer1Hand().getPlayerHand().get(1);
        gameBoard.processCardMagnification(testCard, game, gameBoard.getPlayer1Hand());
        gameBoard.processCardMagnificationRelease(game);
        assertFalse(game.drawCard);
    }

    @Test
    public void GameBoard_proccessMobMagnification_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        game.setMagnificationToggled(true);

        Card testCard = gameBoard.getPlayer1Hand().getPlayerHand().get(1);
        MobContainer testMobContainer = new MobContainer(0,0, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        Mob mobTester = new Mob(100, 100, gameBoard.getGameScreen(), (CharacterCard) testCard);
        testMobContainer.placeCard(mobTester);

        gameBoard.processMobMagnification(game, testMobContainer);

        assertTrue(game.drawCard);
        assertEquals(testMobContainer.getContents().getName(), game.getMagnifiedCard().getCardName());
    }

    @Test
    public void GameBoard_proccessMobMagnificationRelease_Test() {
        Human player1 = new Human("Steve's Arsenal");
        Human player2 = new Human("Steve's Arsenal");

        GameBoard gameBoard = new GameBoard(player1, player2, mainGameScreen);
        game.setMagnificationToggled(true);

        Card testCard = gameBoard.getPlayer1Hand().getPlayerHand().get(1);
        MobContainer testMobContainer = new MobContainer(0,0, MobContainer.ContainerType.TOP_PLAYER, mainGameScreen);
        Mob mobTester = new Mob(100, 100, gameBoard.getGameScreen(), (CharacterCard) testCard);
        testMobContainer.placeCard(mobTester);

        gameBoard.processMobMagnification(game, testMobContainer);
        gameBoard.processMobMagnificationRelease(game);
        assertFalse(game.drawCard);
    }



}
