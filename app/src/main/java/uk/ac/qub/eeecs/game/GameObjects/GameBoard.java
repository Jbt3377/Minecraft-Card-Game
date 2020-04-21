package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardCollection;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Interaction;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PlayerHand;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;

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

    private Mob[] player1MobsOnBoardArray;
    private Mob[] player2MobsOnBoardArray;

    // GameScreen to which the board belongs to
    private GameScreen gameScreen;
    private ArrayList<MobContainer> fieldContainers;

    private MobContainer utilityCardContainer;

    // Boolean Flag to track which player's turn it is
    private boolean isPlayer1Turn;

    private List<TouchEvent> input;


    private ArrayList<Card> cardCollection;

    //////////////
    //Constructor
    /////////////

    public GameBoard(Human player1, Ai player2, GameScreen gameScreen) {

        this.player1 = player1;
        this.player2 = player2;
        this.gameScreen = gameScreen;

        commonConstructorSetup();

    }

    public GameBoard(Human player1, Human player2, GameScreen gameScreen) {

        this.player1 = player1;
        this.player2 = player2;
        this.gameScreen = gameScreen;

        commonConstructorSetup();

    }

    private void commonConstructorSetup() {

        cardCollection = CardCollection.getAllCardCollection(gameScreen, gameScreen.getGame().getAssetManager().getAllCardStats());

        this.fieldContainers = new ArrayList<>();

        this.player1Deck = gameScreen.getGame().getmDeckManager().constructDeck(player1.getmSelectedDeckName());
        this.player2Deck = gameScreen.getGame().getmDeckManager().constructDeck(player2.getmSelectedDeckName());

        setupContainers();

        // Initialise Hands
        player1Hand = new PlayerHand(this.player1Deck, true, gameScreen);
        player2Hand = new PlayerHand(this.player2Deck, false, gameScreen);

        //Initialise both player's Mob ArrayList
        player1MobsOnBoard = new ArrayList<>();
        player2MobsOnBoard = new ArrayList<>();

        player1MobsOnBoardArray = new Mob[7];
        player2MobsOnBoardArray = new Mob[7];

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
        int anEighthOfScreenWidth = screenWidth / 8;
        int anEighthOfScreenHeight = screenHeight / 8;

        // TODO: Adjust positions of containers (raise board up slightly higher)

        fieldContainers.add(new MobContainer(screenWidth / 4, screenHeight / 2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth, screenHeight / 2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth * 2, screenHeight / 2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth * 3, screenHeight / 2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth * 4, screenHeight / 2 + anEighthOfScreenHeight, MobContainer.ContainerType.TOP_PLAYER, gameScreen));

        fieldContainers.add(new MobContainer(screenWidth / 4, screenHeight / 2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth, screenHeight / 2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth * 2, screenHeight / 2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth * 3, screenHeight / 2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));
        fieldContainers.add(new MobContainer(screenWidth / 4 + anEighthOfScreenWidth * 4, screenHeight / 2 - anEighthOfScreenHeight, MobContainer.ContainerType.BOTTOM_PLAYER, gameScreen));

        this.utilityCardContainer = new MobContainer(screenWidth / 10, screenHeight / 2, MobContainer.ContainerType.UTILITY_CARD, gameScreen);
        fieldContainers.add(utilityCardContainer);

    }

    ////////////////////////
    // Update & Draw Methods
    ////////////////////////


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        // Draw Containers
        for (MobContainer container : fieldContainers)
            container.draw(elapsedTime, graphics2D, layerViewport, screenViewport);


        // Draw Mobs
        for (MobContainer mb : fieldContainers) {
            if (!mb.isEmpty()) {
                mb.getContents().draw(elapsedTime, graphics2D, layerViewport, screenViewport);
            }
        }

        // Draw Hands
        player1Hand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        player2Hand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);

    }


    //////////////////////////////////
    // General Getter & Setter Methods
    //////////////////////////////////

    public final Player getActivePlayer() {
        if (isPlayer1Turn)
            return player1;
        else
            return player2;
    }

    public final Player getInactivePlayer() {
        if (!isPlayer1Turn)
            return player1;
        else
            return player2;
    }

    public final PlayerHand getActivePlayerHand() {
        if (isPlayer1Turn)
            return player1Hand;
        else
            return player2Hand;
    }

    public final PlayerHand getInactivePlayerHand() {
        if (!isPlayer1Turn)
            return player1Hand;
        else
            return player2Hand;
    }

    public final ArrayList<Mob> getActivePlayersMobsOnBoard() {
        if (isPlayer1Turn)
            return player1MobsOnBoard;
        else
            return player2MobsOnBoard;
    }

    public final ArrayList<Mob> getInactivePlayersMobsOnBoard() {
        if (!isPlayer1Turn)
            return player1MobsOnBoard;
        else
            return player2MobsOnBoard;
    }

    public void setInactivePlayersMobsOnBoard(ArrayList<Mob> updatedPlayersMobsOnBoard) {
        if (isPlayer1Turn)
            player1MobsOnBoard = updatedPlayersMobsOnBoard;
        else
            player2MobsOnBoard = updatedPlayersMobsOnBoard;
    }

    public final Mob[] getActivePlayersMobsOnBoardArray() {
        if (isPlayer1Turn)
            return player1MobsOnBoardArray;
        else
            return player2MobsOnBoardArray;
    }

    public final Mob[] getInactivePlayersMobsOnBoardArray() {
        if (!isPlayer1Turn)
            return player1MobsOnBoardArray;
        else
            return player2MobsOnBoardArray;
    }

    public void decreaseInactivePlayerHP(int damageInflicted) {

        int mScreenWidth = gameScreen.getGame().getScreenWidth();
        int mScreenHeight = gameScreen.getGame().getScreenHeight();

        int currentHP = getInactivePlayer().getmPlayerHealth();
        getInactivePlayer().setmPlayerHealth(currentHP -= damageInflicted);

        float popupXPos = mScreenWidth * 0.85f, popupYPos;

        if (isPlayer1Turn)
            popupYPos = mScreenHeight * 0.35f;
        else
            popupYPos = mScreenHeight * 0.65f;

        new PopUpObject(popupXPos, popupYPos, getGameScreen(), 30,
                "-" + damageInflicted, 5, true);
    }

    public void update() {

        Input touchInputs = gameScreen.getGame().getInput();
        input = touchInputs.getTouchEvents();

        for (TouchEvent t : input) {
            float x_cor = t.x;
            float y_cor = this.getGameScreen().getGame().getScreenHeight() - t.y;

            for (Card card : player1Hand.getPlayerHand()) {
                if (card != null) {
                    if (t.type == TouchEvent.TOUCH_DOWN && card.getBoundingBox().contains(x_cor, y_cor)) {
                        processCardMagnification(card, gameScreen.getGame(), player1Hand);
                    } else if (t.type == TouchEvent.TOUCH_UP) {
                        processCardMagnificationRelease(gameScreen.getGame());
                    }
                }
            }

            for (Card card : player2Hand.getPlayerHand()) {
                if (card != null) {
                    if (t.type == TouchEvent.TOUCH_DOWN && card.getBoundingBox().contains(x_cor, y_cor)) {
                        processCardMagnification(card, gameScreen.getGame(), player2Hand);
                    } else if (t.type == TouchEvent.TOUCH_UP) {
                        processCardMagnificationRelease(gameScreen.getGame());
                    }
                }
            }

            for (MobContainer mobContainer : getFieldContainers()) {
                if (mobContainer.getContents() != null) {
                    if (t.type == TouchEvent.TOUCH_DOWN && mobContainer.getBound().contains(x_cor, y_cor)) {
                        processMobMagnification(gameScreen.getGame(), mobContainer);
                    } else if (t.type == TouchEvent.TOUCH_UP) {
                        processMobMagnificationRelease(gameScreen.getGame());
                    }
                }
            }
        }

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

    public MobContainer getUtilityCardContainer() {
        return utilityCardContainer;
    }

    public void setUtilityCardContainer(MobContainer utilityCardContainer) {
        this.utilityCardContainer = utilityCardContainer;
    }

    public void processCardMagnification(Draggable dObj, Game game, PlayerHand playerHand) {
        Card card;
        if (game.isMagnificationToggled()) {
            int index = playerHand.getPlayerHand().indexOf(dObj);
            card = playerHand.getPlayerHand().get(index);

            game.setDrawCard(true);
            game.setMagnifiedCard(card, game.getScreenManager().getCurrentScreen(), card.getCardStats());
            game.getAudioManager().play(game.getAssetManager().getSound("zoom-in"));
        }
    }

    public void processCardMagnificationRelease(Game game) {
        if (game.isMagnificationToggled()) {
            if (game.drawCard) {
                game.getAudioManager().play(game.getAssetManager().getSound("zoom-out"));
            }
            game.setDrawCard(false);
        }
    }

    public void processMobMagnification(Game game, MobContainer mobContainer) {

        Card card = null;
        if (game.isMagnificationToggled()) {
            for (Card mobCard : cardCollection) {
                if (mobContainer.getContents().getName() == mobCard.getCardName()) {
                    card = mobCard;
                }
            }

            game.setDrawCard(true);
            game.setMagnifiedCard(card, game.getScreenManager().getCurrentScreen(), card.getCardStats());
            game.getAudioManager().play(game.getAssetManager().getSound("zoom-in"));
        }
    }

    public void processMobMagnificationRelease(Game game) {
        if (game.isMagnificationToggled()) {
            if (game.drawCard) {
                game.getAudioManager().play(game.getAssetManager().getSound("zoom-out"));
            }
            game.setDrawCard(false);
            ;
        }
    }
}