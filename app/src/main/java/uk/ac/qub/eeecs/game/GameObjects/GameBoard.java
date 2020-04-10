package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PlayerHand;

public class GameBoard {

    ////////////
    //Properties
    ////////////

    // Two players 'sit down' at the board
    private Human humanPlayer;
    private Ai aiPlayer;

    // Two deck objects, one from each player
    private Deck humanDeck;
    private Deck aiDeck;

    private PlayerHand humanHand;
    private PlayerHand aiHand;

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

        this.humanPlayer = human;
        this.aiPlayer = ai;

        // stinky code
        this.humanDeck = gameScreen.getGame().getmDeckManager().constructDeck(humanPlayer.getmSelectedDeckName());
        this.aiDeck = gameScreen.getGame().getmDeckManager().constructDeck(aiPlayer.getmSelectedDeckName());

        setupContainers();

        // Initialise Hands
        humanHand = new PlayerHand(this.humanPlayer, this.humanDeck, gameScreen);
        aiHand = new PlayerHand(this.aiPlayer, this.aiDeck, gameScreen);

        //Initialise both player's Mob ArrayList
        player1MobsOnBoard = new ArrayList<Mob>();
        player2MobsOnBoard = new ArrayList<Mob>();

        //Test code - Testing how to create a Mob using a card from player's hand
        //mob1 = new Mob(480, 405,gameScreen, (CharacterCard) humanHand.getPlayerHand().get(0));

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

        fieldContainers.add(new MobContainer(screenWidth/4, screenHeight/2  + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*2, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*3, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*4, screenHeight/2 + anEighthOfScreenHeight, MobContainer.ContainerType.AI, gameScreen));

        fieldContainers.add(new MobContainer(screenWidth/4, screenHeight/2  - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*2, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*3, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth/4 + anEighthOfScreenWidth*4, screenHeight/2 - anEighthOfScreenHeight, MobContainer.ContainerType.HUMAN, gameScreen));


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
        humanHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        aiHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        for(MobContainer mb : fieldContainers){
            if(!mb.isEmpty()) {
                mb.getContainedMob().draw(elapsedTime, graphics2D, layerViewport, screenViewport);
            }
        }
    }

    public void update(List<TouchEvent> input){
        if(!input.isEmpty()) {

            // Update Containers
            for (MobContainer container : fieldContainers)
                container.checkForNewContents(input, humanPlayer.getSelectedCard());

            // Update Hands
            humanHand.update(input);
            aiHand.update(input);


        }

    }

    public Human getHumanPlayer() {
        return humanPlayer;
    }

    public void setHumanPlayer(Human humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    public Ai getAiPlayer() {
        return aiPlayer;
    }

    public void setAiPlayer(Ai aiPlayer) {
        this.aiPlayer = aiPlayer;
    }

    public Deck getHumanDeck() {
        return humanDeck;
    }

    public void setHumanDeck(Deck humanDeck) {
        this.humanDeck = humanDeck;
    }

    public Deck getAiDeck() {
        return aiDeck;
    }

    public void setAiDeck(Deck aiDeck) {
        this.aiDeck = aiDeck;
    }

    public PlayerHand getHumanHand() {
        return humanHand;
    }

    public void setHumanHand(PlayerHand humanHand) {
        this.humanHand = humanHand;
    }

    public PlayerHand getAiHand() {
        return aiHand;
    }

    public void setAiHand(PlayerHand aiHand) {
        this.aiHand = aiHand;
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