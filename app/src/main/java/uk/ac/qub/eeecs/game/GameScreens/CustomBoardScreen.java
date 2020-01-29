package uk.ac.qub.eeecs.game.GameScreens;

import android.graphics.Paint;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;

public class CustomBoardScreen extends GameScreen {

    private PushButton mBackButton;
    private GameObject background;
    private LayerViewport boardLayerViewport;
    private String boardBackgroundText;


    public CustomBoardScreen(String screenName, Game game) {
        super("customBoardScreen", game);

        int screenWidth = mGame.getScreenWidth();
        int screenHeight = mGame.getScreenHeight();
        
        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());
        boardLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);

        //Loading Assets
        mGame.getAssetManager().loadAndAddFont("MinecrafterFont", "font/Minecrafter.ttf");
        mGame.getAssetManager().loadAndAddBitmap("CustomiseScreenBackground","img/CustomiseScreenBackground.png");
        mGame.getAssetManager().loadAssets("txt/assets/CustomiseBackgroundScreenAssets.JSON");

        background =  new GameObject(screenWidth/2, screenHeight/2, screenWidth, screenHeight, getGame().getAssetManager().getBitmap("CustomiseScreenBackground"), this);


    }



    public void update(ElapsedTime elapsedTime) {

    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        int width = mGame.getScreenWidth();
        int height = mGame.getScreenHeight();

        background.draw(elapsedTime, graphics2D,
                boardLayerViewport,
                mDefaultScreenViewport);


    }
}
