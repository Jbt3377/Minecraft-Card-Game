package uk.ac.qub.eeecs.game.GameObjects;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PlayerHand;

public class GameBoard {

    ////////////
    //Properties
    ////////////

    // Two players 'sit down' at the board
    private Player player1;
    private Player player2;

    // Two deck objects, one from each player
    private Deck player1Deck;
    private Deck player2Deck;

    private PlayerHand player1Hand;
    private PlayerHand player2Hand;

    private ArrayList<Mob> player1MobsOnBoard;
    private ArrayList<Mob> player2MobsOnBoard;

    // GameScreen to which the board belongs to
    private GameScreen gameScreen;
    private ArrayList<MobContainer> fieldContainers;

    //Boolean to tell which player's turn it is
    private boolean isPlayer1Turn;


    //////////////
    //Constructor
    /////////////

    public GameBoard(Human human, Ai ai, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.fieldContainers = new ArrayList<>();

        this.player1 = human;
        this.player2 = ai;

        this.player1Deck = gameScreen.getGame().getmDeckManager().constructDeck(player1.getmSelectedDeckName());
        this.player2Deck = gameScreen.getGame().getmDeckManager().constructDeck(player2.getmSelectedDeckName());

        setupContainers();

        // Initialise Hands
        player1Hand = new PlayerHand(this.player1, this.player1Deck, gameScreen);
        player2Hand = new PlayerHand(this.player2, this.player2Deck, gameScreen);

        //Initialise both player's Mob ArrayList
        player1MobsOnBoard = new ArrayList<>();
        player2MobsOnBoard = new ArrayList<>();

    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Method creates & sets positions for containers on game board
     */
    private void setupContainers() {

        int screenWidth = gameScreen.getGame().getScreenWidth();
        int screenHeight = gameScreen.getGame().getScreenHeight();
        int anEighthOfScreenWidth = screenWidth/8;
        int anEighthOfScreenHeight = screenHeight/8;

        // TODO: Adjust positions of containers (raise board up slightly higher)

        fieldContainers.add(new MobContainer(screenWidth/4, screenHeight/2  + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*2, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*3, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*4, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));

        fieldContainers.add(new MobContainer(screenWidth/4, screenHeight/2  - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*2, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*3, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*4, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));


    }

    ////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Draw Containers
        for(MobContainer container: fieldContainers)
            container.draw(elapsedTime, graphics2D, layerViewport, screenViewport);


        // Draw Hands
        player1Hand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        player2Hand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

        // Draw Mobs
        for(MobContainer mb : fieldContainers){
            if(!mb.isEmpty()) {
                mb.getContainedMob().draw(elapsedTime, graphics2D, layerViewport, screenViewport);
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // General Getter & Setter Methods
    ////////////////////////////////////////////////////////////////////////////

    public final Player getActivePlayer(){
        if(isPlayer1Turn)
            return player1;
        else
            return player2;
    }

    public final PlayerHand getActivePlayerHand(){
        if(isPlayer1Turn)
            return player1Hand;
        else
            return player2Hand;
    }

    public final ArrayList<Mob> getActivePlayersMobsOnBoard(){
        if(isPlayer1Turn)
            return player1MobsOnBoard;
        else
            return player2MobsOnBoard;
    }

    public final ArrayList<Mob> getInactivePlayersMobsOnBoard(){
        if(!isPlayer1Turn)
            return player1MobsOnBoard;
        else
            return player2MobsOnBoard;
    }

    public void setInactivePlayersMobsOnBoard(ArrayList<Mob> updatedPlayersMobsOnBoard){
        if(isPlayer1Turn)
            player1MobsOnBoard = updatedPlayersMobsOnBoard;
        else
            player2MobsOnBoard = updatedPlayersMobsOnBoard;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Getter & Setter Methods
    ////////////////////////////////////////////////////////////////////////////

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Human player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Ai player2) {
        this.player2 = player2;
    }

    public Deck getPlayer1Deck() {
        return player1Deck;
    }

    public void setPlayer1Deck(Deck player1Deck) {
        this.player1Deck = player1Deck;
    }

    public Deck getPlayer2Deck() {
        return player2Deck;
    }

    public void setPlayer2Deck(Deck player2Deck) {
        this.player2Deck = player2Deck;
    }

    public PlayerHand getPlayer1Hand() {
        return player1Hand;
    }

    public PlayerHand getPlayer2Hand() {
        return player2Hand;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public ArrayList<MobContainer> getFieldContainers() {
        return fieldContainers;
    }

    public void setFieldContainers(ArrayList<MobContainer> fieldContainers) {
        this.fieldContainers = fieldContainers;
    }

    public ArrayList<Mob> getPlayer1MobsOnBoard() {
        return player1MobsOnBoard;
    }

    public void setPlayer1MobsOnBoard(ArrayList<Mob> player1MobsOnBoard) {
        this.player1MobsOnBoard = player1MobsOnBoard;
    }

    public ArrayList<Mob> getPlayer2MobsOnBoard() {
        return player2MobsOnBoard;
    }

    public void setPlayer2MobsOnBoard(ArrayList<Mob> player2MobsOnBoard) {
        this.player2MobsOnBoard = player2MobsOnBoard;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setIsPlayer1Turn(boolean player1Turn) {
        isPlayer1Turn = player1Turn;
    }
}