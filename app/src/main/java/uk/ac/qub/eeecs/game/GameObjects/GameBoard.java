package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;
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
    private Human player1;
    private Ai player2;

    // Two deck objects, one from each player
    private Deck player1Deck;
    private Deck player2Deck;

    private PlayerHand player1Hand;
    private PlayerHand player2Hand;

    private ArrayList<Mob> player1MobsOnBoard;
    private ArrayList<Mob> player2MobsOnBoard;

    //Two display boxes to display each players lifePoints
    private GameObject playerLifePointsDisplayBox;
    private GameObject aiLifePointsDisplayBox;

    //Two display boxes to display each players manaPoints7
    private GameObject playerManaPointsDisplayBox;
    private GameObject aiManaPointsDisplayBox;


    // GameScreen to which the board belongs to
    private GameScreen gameScreen;

    private ArrayList<MobContainer> fieldContainers;

    //private Mob mob1;

    //////////////
    //Constructor
    /////////////

    public GameBoard(Human human, Ai ai, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.fieldContainers = new ArrayList<>();

        this.player1 = human;
        this.player2 = ai;

        // stinky code
        this.player1Deck = gameScreen.getGame().getmDeckManager().constructDeck(player1.getmSelectedDeckName());
        this.player2Deck = gameScreen.getGame().getmDeckManager().constructDeck(player2.getmSelectedDeckName());

        setupContainers();

        // Initialise Hands
        player1Hand = new PlayerHand(this.player1, this.player1Deck, gameScreen);
        player2Hand = new PlayerHand(this.player2, this.player2Deck, gameScreen);

        //Initialise both player's Mob ArrayList
        player1MobsOnBoard = new ArrayList<Mob>();
        player2MobsOnBoard = new ArrayList<Mob>();

        //Test code - Testing how to create a Mob using a card from player's hand
        //mob1 = new Mob(480, 405,gameScreen, (CharacterCard) player1Hand.getPlayerHand().get(0));

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
        for(MobContainer mb : fieldContainers){
            if(!mb.isEmpty()) {
                mb.getContainedMob().draw(elapsedTime, graphics2D, layerViewport, screenViewport);
            }
        }

        // Draw Selected Mob
        if(player1.getSelectedMob() != null){
            // Get selected mob co-ordinates
            // Draw a highlight outline
        }
    }

    public void update(List<TouchEvent> input){
        if(!input.isEmpty()) {

            // Update Containers
            for (MobContainer container : fieldContainers)
                container.checkForNewContents(input, player1.getSelectedCard());

            // Update Hands
            player1Hand.update(input);
            player2Hand.update(input);


        }

    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Human player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() { return player2; }

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

    public void setPlayer1Hand(PlayerHand player1Hand) {
        this.player1Hand = player1Hand;
    }

    public PlayerHand getPlayer2Hand() {
        return player2Hand;
    }

    public void setPlayer2Hand(PlayerHand player2Hand) {
        this.player2Hand = player2Hand;
    }

    public GameScreen getGameScreen() { return gameScreen; }

    public void setGameScreen(GameScreen gameScreen) { this.gameScreen = gameScreen; }

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
}