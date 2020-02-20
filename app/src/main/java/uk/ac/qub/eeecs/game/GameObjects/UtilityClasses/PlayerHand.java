package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
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
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;

public class PlayerHand {

    private Player player;
    private ArrayList<Card> playerHand;
    private Deck playersDeck;
    private int[][] handPositions;
    private GameScreen gameScreen;

    //////////////
    // Constructor
    //////////////

    public PlayerHand(Player player, Deck playersDeck, GameScreen gameScreen){

        this.player = player;
        this.playersDeck = playersDeck;
        this.playerHand = new ArrayList<>();
        this.handPositions = new int[7][2];
        this.gameScreen = gameScreen;

        assignPositions();

        setupHand();

    }

    //////////
    // Methods
    //////////

    /**
     * Method will identify if the Hand belongs to the Human or Ai and will set the card positions
     * on screen accordingly.
     */
    private void assignPositions(){

        if(this.player instanceof Human) {
            int xPosForCards = 130;
            int yPosForCards = 400;     // Start Y Pos for Cards

            for(int i=0; i<7; i++){
                handPositions[i][0] = yPosForCards;
                handPositions[i][1] = xPosForCards;
                yPosForCards+=200;
            }
        }else if(this.player instanceof Ai){

            int xPosForCards = 1000;
            int yPosForCards = 800;     // Start Y Pos for Cards

            for(int i=0; i<7; i++){
                handPositions[i][0] = yPosForCards;
                handPositions[i][1] = xPosForCards;
                yPosForCards+=200;
            }
        }

    }


    /**
     * Method will draw the initial 7 cards into the hand
     * TODO: Method will likely change/adapt with the addition of the replenish() method
     * TODO: Flip the Ai Cards
     */
    private void setupHand(){

        int xPos = 0; int yPos = 1;

        // Add 5 Character Cards
        for(int i = 0; i<5; i++) {

            // Pop next Character Card Stat from Stack
            CharacterCardStats nextCharCardStats = (CharacterCardStats)playersDeck.popNextCharacterCardStat();
            CharacterCard nextCharCard = new CharacterCard(handPositions[i][xPos], handPositions[i][yPos], gameScreen, nextCharCardStats);
            playerHand.add(nextCharCard);
        }


        // Add 2 Special Cards
        for(int i=5; i<7; i++) {

            // Pop next Special Card Stat from Stack
            CardStats nextSpecialCardStats = playersDeck.popNextSpecialCardStat();

            // Generate appropriate card type - add to hand
            if (nextSpecialCardStats instanceof EquipCardStats) {
                EquipCard nextSpecialCard = new EquipCard(handPositions[i][xPos], handPositions[i][yPos], gameScreen, (EquipCardStats) nextSpecialCardStats);
                playerHand.add(nextSpecialCard);
            }else if(nextSpecialCardStats instanceof UtilityCardStats) {
                UtilityCard nextSpecialCard = new UtilityCard(handPositions[i][xPos], handPositions[i][yPos], gameScreen, (UtilityCardStats) nextSpecialCardStats);
                playerHand.add(nextSpecialCard);
            }
        }
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport){

        for(Card cardInHand: playerHand){
            cardInHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
        }
    }


    public void update(List<TouchEvent> input){

        for (Card cardInHand : playerHand) {
            Interaction.processDragEvents(input, cardInHand, gameScreen.getGame());
        }

        // TODO: Will add the snap back functionality for when cards released in an invalid region

    }


    ////////////////////
    // Getters & Setters
    ////////////////////

    public ArrayList<Card> getPlayerHand() { return playerHand; }



}
