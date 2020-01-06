package uk.ac.qub.eeecs.game.GameScreens;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class CompanyLogoScreen extends GameScreen {

    private PushButton mBackground;

    public CompanyLogoScreen(Game game) {
        super("CompanyLogoScreen", game);

        //TODO: Add music to the splash screen.



        int screenHeight = game.getScreenHeight();
        int screenWidth = game.getScreenWidth();



        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("SplashScreenBackground", "img/SpaceBackground.png");


        mBackground = new PushButton(screenWidth/2, screenHeight/2, screenWidth,screenHeight, "SplashScreenBackground", this);

    }

    public void backgroundTriggered(){
        if(mBackground.isPushTriggered()){
            mGame.getScreenManager().removeAllScreens();
            mGame.getScreenManager().addScreen(new StartScreen(mGame));
        }
    }



    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();

        if(touchEvents.size() > 0){
            mBackground.update(elapsedTime);
            backgroundTriggered();
        }

        if(elapsedTime.totalTime >= 3.00){
            mBackground.update(elapsedTime);
            mGame.getScreenManager().removeAllScreens();
            mGame.getScreenManager().addScreen(new StartScreen(mGame));
        }


    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        mBackground.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
