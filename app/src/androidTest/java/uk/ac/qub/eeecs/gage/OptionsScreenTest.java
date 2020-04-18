package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Typeface;
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
import android.graphics.Paint;

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

        float val = optionsScreen.getMusic2();
        int pt1 = optionsScreen.getVolumecounter();
        assertTrue(pt1 == 1);

        assertTrue(val == 0.0 );

    }
    @Test
    //button will then be pressed and go to 2
    public void VolumeButton_Test2(){


        int pt1 = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt2 = optionsScreen.getVolumecounter();

        float val = optionsScreen.getMusic2();

        assertTrue(pt2 == 2);
        assertTrue(val == 0.67f );

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

        float val = optionsScreen.getMusic3();

        assertTrue(pt3 == 3);
        assertTrue(val == 1f );
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
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt1_ = optionsScreen.getVolumecounter();

        float val = optionsScreen.getMusic4();



        assertTrue(pt0 == 0);
        assertTrue(val == 0f );
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
    public void VolumeButton_test_volume_boundary(){
//this changes as expexcted due to how gage sets up default values
        //button after reset volume
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
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt1_ = optionsScreen.getVolumecounter();

        float val = optionsScreen.getMusic2();

        assertTrue(val==0.67f);

    }
@Test
    public void VolumeButton_test_volume2(){

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
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt1_ = optionsScreen.getVolumecounter();
        optionsScreen.getVolumeButton().buttonPushed(true);
        optionsScreen.volumebuttontriggred();
        int pt2_ = optionsScreen.getVolumecounter();

        float val = optionsScreen.getMusic3();

        assertTrue(val == 1);
    }
//all volume methods and algorithm run as expected

@Test
//testing the paints to make sure they are as expected and being set up correctly
    public void PaintTypeTest_True(){

         Typeface painttype = optionsScreen.getTitlePaint().getTypeface();

         assertTrue(painttype == game.getAssetManager().getFont("MinecrafterFont"));

    }
    @Test
//testing the paints to make sure they are as expected and being set up correctly
    public void PaintTypeTest_false(){

        Typeface painttype = optionsScreen.getTitlePaint().getTypeface();

        assertFalse(painttype == game.getAssetManager().getFont("MinecraftRegFont"));

    }
    @Test
    //testing paint method created to create paints easily (more user/programmer friendly)
    public void PaintSetupMethod_Test(){

        //proof that the method works compares it to a paint that is already set up with the same values
        float size = optionsScreen.getScreenHeight();
        Paint testpaint = optionsScreen.createAPaint("Center" ,"White","MinecrafterFont",size/16);

        assertTrue(testpaint.getColor() == optionsScreen.getTitlePaint().getColor() );


    }




}
