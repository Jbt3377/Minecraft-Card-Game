//package uk.ac.qub.eeecs.gage;
//
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import uk.ac.qub.eeecs.gage.engine.AssetManager;
//import uk.ac.qub.eeecs.gage.engine.ScreenManager;
//import uk.ac.qub.eeecs.gage.engine.io.FileIO;
//import uk.ac.qub.eeecs.gage.world.GameObject;
//import uk.ac.qub.eeecs.game.DemoGame;
//import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//
//@RunWith(AndroidJUnit4.class)
//public class CheckGameObject {


//    private Context context;
//    private DemoGame game;
//    private MainGameScreen mainScreen;
//
//    @Before
//    public void setup() {
//
//        context = InstrumentationRegistry.getTargetContext();
//        game = new DemoGame();
//        game.mFileIO = new FileIO(context);
//        game.mAssetManager = new AssetManager(game);
//        game.mScreenManager = new ScreenManager(game);
//        mainScreen = new MainGameScreen(game);
//        game.mScreenManager.addScreen(mainScreen);
//    }
//
//
//    @Test
//    public void CheckGameObjectIsCorrectBitmap() {
//
//        //gets a game object pig
//        //GameObject gameobject = mainScreen.getPig();
//
//        Bitmap bitmap = gameobject.getBitmap();
//
//        //if bitmap IS EQUAL TO THE PIG BITMAP THEN GOOD pass
//        Bitmap storedbitmap = game.getAssetManager().getBitmap("PIG");
//
//        boolean istrue;
//
//        if (storedbitmap == bitmap) {
//            istrue = true;
//        } else {
//            istrue = false;
//        }
//
//
//        assertTrue(istrue);
//    }
//}
//
//
//
//
