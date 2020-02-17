package uk.ac.qub.eeecs.game.GameObjects;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.Button;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.DeckClasses.Deck;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

public class GameBoard {

    ////////////
    //Properties
    ////////////

    //Two players 'sit down' at the board
    private Human humanPlayer;
    private Ai aiPlayer;

    //Two deck objects, one from each player
    private Deck humanDeck;
    private Deck aiDeck;

    //Two hands for each player, the cards the currently have in their hand.
    private ArrayList<Card> humanHand;
    private ArrayList<Card> aiHand;


    //Two display boxes to display each players lifePoints
    private GameObject playerLifePointsDisplayBox;
    private GameObject aiLifePointsDisplayBox;

    //Two display boxes to display each players manaPoints7
    private GameObject playerManaPointsDisplayBox;
    private GameObject aiManaPointsDisplayBox;

    //Button to end the turn
    private Button endTurnButton;

    // GameScreen to which the board belongs to
    private GameScreen gameScreen;

    private ArrayList<MobContainer> fieldContainers;

    //TODO: Want to add containers to this objects properties. Places where cards are placed etc.
    /*
    Five containers on the field for human cards in play
    Five containers on the field for Ai cards in play
    Seven containers for players hand, 5 for character cards, 2 for special cards
    Seven containers for Ai hand, 5 for character cards, 2 for special cards
    One container to place the players deck
    One container to place Ai deck
    One container for special cards to be placed
     */

    //////////////
    //Constructor
    /////////////

    public GameBoard(Human human, Ai ai, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.fieldContainers = new ArrayList<>();

        this.humanPlayer = human;
        this.aiPlayer = ai;

        this.humanDeck = humanPlayer.getmSelectedDeck();
        this.aiDeck = ai.getmSelectedDeck();

        humanHand = new ArrayList<Card>();
        aiHand = new ArrayList<Card>();

        setupContainers();

        //fillHumanHand();

        //fillAiHand();

        // Initialise hands



    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////

    private void setupContainers() {

        int screenWidth = gameScreen.getGame().getScreenWidth();
        int screenHeight = gameScreen.getGame().getScreenHeight();
        int anEighthOfScreenWidth = screenWidth/8;
        int anEighthOfScreenHeight = screenHeight/8;

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

    private void fillHumanHand(){

        int currentNumOfCards = humanHand.size();

        if(currentNumOfCards == 0){

            // Add 5 Character Cards
            for(int i = 0; i<5; i++) {
                CharacterCardStats nextCharCardStats = (CharacterCardStats)humanDeck.popNextCharacterCardStat();
                CharacterCard nextCharCard = new CharacterCard(200, 200, gameScreen, i, nextCharCardStats);
                humanHand.add(nextCharCard);
            }

            // Add 2 Special Cards
            for(int i=5; i<7; i++){

                CardStats nextSpecialCardStats = humanDeck.popNextSpecialCardStat();

                if (nextSpecialCardStats instanceof EquipCardStats) {
                    EquipCard nextSpecialCard = new EquipCard(200, 200, gameScreen, i, (EquipCardStats) nextSpecialCardStats);
                    humanHand.add(nextSpecialCard);
                }else if(nextSpecialCardStats instanceof UtilityCardStats) {
                    UtilityCard nextSpecialCard = new UtilityCard(200, 200, gameScreen, i, (UtilityCardStats) nextSpecialCardStats);
                    humanHand.add(nextSpecialCard);
                }
            }
        }



    }

    ////////////////////////////////////////////////////////////////////////////
    // Update & Draw Methods
    ////////////////////////////////////////////////////////////////////////////


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

        for(MobContainer container: fieldContainers){
            container.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }

        // Draw Player Hand Cards
        for(Card cardInHumanHand: humanHand){
            cardInHumanHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }

    }

    public void update(List<TouchEvent> input){

        // Only update containers if a touch event occurred
        if(!input.isEmpty()) {

            for (MobContainer container : fieldContainers) {
                container.checkForNewContents(input, humanPlayer.getSelectedCard());
            }

        }

    }



}