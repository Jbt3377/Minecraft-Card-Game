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
    private int[][] handPositionsIndex;
    private GameScreen gameScreen;

    //////////////
    // Constructor
    //////////////

    public PlayerHand(Player player, Deck playersDeck, Boolean isPlayer1, GameScreen gameScreen){

        this.player = player;
        this.playersDeck = playersDeck;
        this.playerHand = new ArrayList<>();
        this.handPositionsIndex = new int[7][2];
        this.gameScreen = gameScreen;

        assignHandPositions(isPlayer1);
        replenishHand();
    }

    //////////
    // Methods
    //////////

    /**
     * Method will identify if the Hand belongs to the Human or Ai and will set the card positions
     * on screen accordingly.
     */
    private void assignHandPositions(Boolean isPlayer1){

        if(isPlayer1) {
            int xPosForCards = 130;     // Start X Pos for Human Cards
            int yPosForCards = 400;     // Start Y Pos for Human Cards

            for(int i=0; i<7; i++){
                handPositionsIndex[i][0] = yPosForCards;
                handPositionsIndex[i][1] = xPosForCards;
                yPosForCards+=200;
            }
        }else{

            int xPosForCards = 1000;    // Start X Pos for Ai Cards
            int yPosForCards = 800;     // Start Y Pos for Ai Cards

            for(int i=0; i<7; i++){
                handPositionsIndex[i][0] = yPosForCards;
                handPositionsIndex[i][1] = xPosForCards;
                yPosForCards+=200;
            }
        }

    }


    /**
     * Method will replenish a player's hand.
     * Adds up to 5 Character Cards and 2 Special Cards, while the deck has cards to draw.
     */
    public void replenishHand(){

        // Count variable used to keep track of which card is being created. Used to look up the
        // corresponding position co-ordinates.
        int currentCard = 0;

        // Determine how many cards of each type
        int currentNumOfCharacterCards = 0;
        int currentNumOfSpecialCards = 0;

        for(Card card: playerHand){
            if(card instanceof CharacterCard)
                currentNumOfCharacterCards++;
            else
                currentNumOfSpecialCards++;
        }

        int xPos = 0; int yPos = 1;

        // Replenish Character Cards
        for(int i = currentNumOfCharacterCards; i<5; i++) {

            try{
                // Pop next Character Card Stat from Stack
                CharacterCardStats nextCharCardStats = (CharacterCardStats)playersDeck.popNextCharacterCardStat();
                CharacterCard nextCharCard = new CharacterCard(handPositionsIndex[currentCard][xPos], handPositionsIndex[currentCard][yPos], gameScreen, nextCharCardStats);
                playerHand.add(nextCharCard);
            }catch(Exception e){
                e.printStackTrace();
            }

            currentCard++;
        }


        // Replenish Special Cards
        for(int i=currentNumOfSpecialCards; i<2; i++) {

            try{
                // Pop next Special Card Stat from Stack
                CardStats nextSpecialCardStats = playersDeck.popNextSpecialCardStat();

                // Generate appropriate card type and add to hand
                if (nextSpecialCardStats instanceof EquipCardStats) {
                    EquipCard nextSpecialCard = new EquipCard(handPositionsIndex[currentCard][xPos], handPositionsIndex[currentCard][yPos], gameScreen, (EquipCardStats) nextSpecialCardStats);
                    playerHand.add(nextSpecialCard);
                }else if(nextSpecialCardStats instanceof UtilityCardStats) {
                    UtilityCard nextSpecialCard = new UtilityCard(handPositionsIndex[currentCard][xPos], handPositionsIndex[currentCard][yPos], gameScreen, (UtilityCardStats) nextSpecialCardStats);
                    playerHand.add(nextSpecialCard);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            currentCard++;
        }
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport){

        for(Card cardInHand: playerHand){
            if(cardInHand != null) {
                cardInHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
            }
        }
    }


    public void update(List<TouchEvent> input){

        for (Card cardInHand : playerHand) {
            if(cardInHand != null) {
                Interaction.processDragEvents(input, cardInHand, gameScreen.getGame());
            }
        }

        // TODO: Will add the snap back functionality for when cards released in an invalid region

    }


    ////////////////////
    // Getters & Setters
    ////////////////////

    public ArrayList<Card> getPlayerHand() { return playerHand; }



}
