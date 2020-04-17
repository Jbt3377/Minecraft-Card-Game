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
import uk.ac.qub.eeecs.game.GameScreens.OptionsScreen;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class OptionsScreenTest {
    private Context context;
    private DemoGame game;
    private OptionsScreen optionsScreen;

    @Before
    public void setup() {

        context = InstrumentationRegistry.getTargetContext();
        game = new DemoGame();
        game.mFileIO = new FileIO(context);
        game.mAssetManager = new AssetManager(game);
        game.mScreenManager = new ScreenManager(game);
        game.mAudioManager = new AudioManager(game);
        game.mAssetManager.loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
      optionsScreen = new OptionsScreen("OptionsScreen" , game);
        game.getScreenManager().addScreen(optionsScreen);

    }
    @Test
    //button is linked to a counter which will reset to 0
    //this case the counter starts at 1 in options screen
    public void VolumeButton_Test(){

        int pt1 = optionsScreen.getVolumecounter();
        assertTrue(pt1 == 1);

    }
    @Test
    //button will then be pressed and go to 2
    public void VolumeButton_Test2(){

        int pt1 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt2 = optionsScreen.getVolumecounter();
        assertTrue(pt2 == 2);
    }
    @Test
   // button will then be pressed and go to 2
    public void VolumeButton_Test3(){
        int pt1 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt2 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt3 = optionsScreen.getVolumecounter();
        assertTrue(pt3 == 3);
    }
    @Test
    //counter will be reset
    public void VolumeButton_Test4(){
        int pt1 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt2 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt3 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt0 = optionsScreen.getVolumecounter();
        assertTrue(pt0 == 0);
    }
    @Test
    //testing the boundary of reset
    public void VolumeButton_fail(){

        int pt1 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt2 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt3 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt0 = optionsScreen.getVolumecounter();
      //this is to prove that the counter resets with the logic
        assertFalse(pt0 == 4);
    }
    @Test
    public void VolumeButton_test_volume(){


    }

}
