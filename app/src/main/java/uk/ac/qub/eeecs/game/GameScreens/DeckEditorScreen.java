package uk.ac.qub.eeecs.game.GameScreens;


import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;


public class DeckEditorScreen extends GameScreen {

    //Define a viewport for the cards
    private LayerViewport cardsLayerViewport;
    private ScreenViewport screenViewport;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////


    public DeckEditorScreen(String screenName, Game game) {
        super("DeckEditor", game);
        setupViewPorts();
        setupBoardGameObjects();
    }


    private void setupViewPorts() {
        mDefaultScreenViewport.set( 0, 0, mGame.getScreenWidth(), mGame.getScreenHeight());

        //Setup boardLayerViewport
        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();
        cardsLayerViewport = new LayerViewport(screenWidth/2,screenHeight/2,screenWidth/2,screenHeight/2);
        screenViewport = new ScreenViewport(0,0,(int) screenWidth,(int)screenHeight);
    }

    //Setting up objects for the gamescreen
    private void setupBoardGameObjects() {



    }



        @Override
    public void update(ElapsedTime elapsedTime) {


    }

    /**
     * Draw the menu screen
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {


    }



}