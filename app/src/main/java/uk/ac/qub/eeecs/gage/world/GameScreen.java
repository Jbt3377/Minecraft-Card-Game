package uk.ac.qub.eeecs.gage.world;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;


/**
 * Game screen class acting as a container for a coherent section of the game (a
 * level, configuration screen, etc.)
 *
 * @version 1.0
 */
public abstract class GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Name that is given to this game screen
     */
    protected final String mName;

    /**
     * List of popup objects to be drawn to game screen
     */
    public CopyOnWriteArrayList<PopUpObject> popUpObjects;

    /**
     * Return the name of this game screen
     *
     * @return Name of this game screen
     */
    public String getName() {
        return mName;
    }

    /**
     * Game to which game screen belongs
     */
    protected final Game mGame;

    /**
     * Return the game to which this game screen is attached
     *
     * @return Game to which screen is attached
     */
    public Game getGame() {
        return mGame;
    }

    /**
     * Define the default layer viewport for the game screen.
     */
    protected LayerViewport mDefaultLayerViewport;

    /**
     * Return the default layer viewport for this game screen
     */
    public LayerViewport getDefaultLayerViewport() {
        return mDefaultLayerViewport;
    }

    /**
     * Define the default screen viewport for the game screen.
     */
    protected ScreenViewport mDefaultScreenViewport;

    /**
     * Return the default screen viewport for this game screen
     */
    public ScreenViewport getDefaultScreenViewport() {
        return mDefaultScreenViewport;
    }


    protected float mScreenWidth, mScreenHeight;
    protected int fps;



    // List containing all card stat objects
    private ArrayList<CardStats> allCardStats;


    //All the cards that will be displayed
    private ArrayList<Card> cardCollection = new ArrayList<>();

    public ArrayList<Card> getCardCollection() {
        return cardCollection;
    }


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new game screen associated with the specified game instance
     *
     * @param game Game instance to which the game screen belongs
     */
    public GameScreen(String name, Game game) {
        mName = name;
        mGame = game;

        mScreenWidth = game.getScreenWidth();
        mScreenHeight = game.getScreenHeight();

        // Create the default layer and screen viewports
        mDefaultScreenViewport = new ScreenViewport();
        mDefaultLayerViewport = new LayerViewport();
        ViewportHelper.createDefaultLayerViewport(mDefaultLayerViewport);
        ViewportHelper.create3To2AspectRatioScreenViewport(
                game, mDefaultScreenViewport);

        popUpObjects = new CopyOnWriteArrayList<>();

        mGame.getAssetManager().loadAssets("txt/assets/MinecraftCardGameScreenAssets.JSON");
        allCardStats = mGame.getAssetManager().getAllCardStats();
        addCardsToCardCollection();

    }

    // /////////////////////////////////////////////////////////////////////////
    // Update and Draw
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the game screen. Invoked automatically from the game.
     * <p>
     * NOTE: If the update is multi-threaded control should not be returned from
     * the update call until all update processes have completed.
     *
     * @param elapsedTime Elapsed time information for the frame
     */
    public abstract void update(ElapsedTime elapsedTime);

    /**
     * Draw the game screen. Invoked automatically from the game.
     *
     * @param elapsedTime Elapsed time information for the frame
     * @param graphics2D  Graphics instance used to draw the screen
     */
    public abstract void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D);

    // /////////////////////////////////////////////////////////////////////////
    // Android Life Cycle
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Invoked automatically by the game whenever the app is paused.
     */
    public void pause() {
    }

    /**
     * Invoked automatically by the game whenever the app is resumed.
     */
    public void resume() {
    }

    /**
     * Invoked automatically by the game whenever the app is disposed.
     */
    public void dispose() {
    }


    protected void updatePopUps(ElapsedTime elapsedTime) {
        if (!popUpObjects.isEmpty()) {
            for(PopUpObject popup: popUpObjects)
                popup.update(elapsedTime);
        }
    }

    protected void drawPopUps(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        if (!popUpObjects.isEmpty()) {
            for(PopUpObject popup: popUpObjects)
                popup.draw(elapsedTime, graphics2D);
        }
    }

    //Adds all the cards that exist into cardCollection
    private void addCardsToCardCollection(){
        //Logical Adds the cards depending on the "type" of card
        for(CardStats x : allCardStats){
            //CharacterCardStats check
            if(x instanceof CharacterCardStats){
                CharacterCard card = new CharacterCard(getDefaultLayerViewport().x, getDefaultLayerViewport().y, this, (CharacterCardStats)x);
                cardCollection.add(card);
            }
            //UtilityCardStats check
            if(x instanceof UtilityCardStats){
                UtilityCard card = new UtilityCard(getDefaultLayerViewport().x, getDefaultLayerViewport().y, this, (UtilityCardStats)x);
                cardCollection.add(card);
            }
            //EquipCardStats check
            if(x instanceof EquipCardStats){
                EquipCard card = new EquipCard(getDefaultLayerViewport().x, getDefaultLayerViewport().y, this, (EquipCardStats)x);
                cardCollection.add(card);
            }
        }
    }

}