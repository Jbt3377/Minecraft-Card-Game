package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.ArrayList;
import java.util.EmptyStackException;
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

    private ArrayList<Card> playerHand;
    private Deck playersDeck;
    private int[][] handPositionsIndex;
    private GameScreen gameScreen;

    //////////////
    // Constructor
    //////////////

    public PlayerHand(Deck playersDeck, Boolean isPlayer1, GameScreen gameScreen){

        this.playersDeck = playersDeck;
        this.playerHand = new ArrayList<>();
        this.handPositionsIndex = new int[7][2];
        this.gameScreen = gameScreen;

        // Determines which side of the screen cards in hand should be drawn to
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
            int xPosForCards = 130;     // Start X Pos for Player 1 Cards
            int yPosForCards = 400;     // Start Y Pos for Player 1 Cards

            for(int i=0; i<7; i++){
                handPositionsIndex[i][0] = yPosForCards;
                handPositionsIndex[i][1] = xPosForCards;
                yPosForCards+=200;
            }
        }else{

            int xPosForCards = 1000;    // Start X Pos for Player 2 Cards
            int yPosForCards = 800;     // Start Y Pos for Player 2 Cards

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

        int currentCardCount = 0;
        int currentNumOfCharacterCards = 0;
        int xPos = 0; int yPos = 1;

        // Stock take on current hand contents and push cards left
        for(Card card: playerHand){
            float newXPos = handPositionsIndex[currentCardCount][xPos];
            float newYPos = handPositionsIndex[currentCardCount][yPos];
            card.setPosition(newXPos, newYPos);

            if(card instanceof CharacterCard)
                currentNumOfCharacterCards++;

            currentCardCount++;
        }


        // Replenish Character Cards
        for(int i = currentNumOfCharacterCards; i<5; i++) {

            try{
                // Pop next Character Card Stat from Deck
                CharacterCardStats nextCharCardStats = (CharacterCardStats)playersDeck.popNextCharacterCardStat();

                // Inflate Card Object using popped Card Stats & add to Hand
                CharacterCard nextCharCard = new CharacterCard(handPositionsIndex[currentCardCount][xPos], handPositionsIndex[currentCardCount][yPos], gameScreen, nextCharCardStats);
                playerHand.add(nextCharCard);
                currentCardCount++;

            }catch(Exception e){
                System.out.println("No more Character Card Stats to draw!");
            }
        }

        // Replenish Special Cards
        for(int i=currentCardCount; i<7; i++) {

            try{
                // Pop next Special Card Stat from Stack
                CardStats nextSpecialCardStats = playersDeck.popNextSpecialCardStat();

                // Inflate Card Object using popped Card Stats & add to Hand
                if (nextSpecialCardStats instanceof EquipCardStats) {
                    EquipCard nextSpecialCard = new EquipCard(handPositionsIndex[currentCardCount][xPos], handPositionsIndex[currentCardCount][yPos], gameScreen, (EquipCardStats) nextSpecialCardStats);
                    playerHand.add(nextSpecialCard);
                }else if(nextSpecialCardStats instanceof UtilityCardStats) {
                    UtilityCard nextSpecialCard = new UtilityCard(handPositionsIndex[currentCardCount][xPos], handPositionsIndex[currentCardCount][yPos], gameScreen, (UtilityCardStats) nextSpecialCardStats);
                    playerHand.add(nextSpecialCard);
                }
                currentCardCount++;

            }catch(Exception e){
                System.out.println("No more Special Card Stats to draw!");
            }
        }
    }


    /**
     * Method draws each Card in Hand in Player's hand positions
     */
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport){

        for(Card cardInHand: playerHand){
            if(cardInHand != null) {
                cardInHand.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
            }
        }
    }

    /**
     * Method used to detect and process each card position in hand
     */
    public void update(List<TouchEvent> input){

        for (Card cardInHand : playerHand) {
            if(cardInHand != null) {
                Interaction.processDragEvents(input, cardInHand, gameScreen.getGame());
            }
        }
    }


    ////////////////////
    // Getters & Setters
    ////////////////////

    public ArrayList<Card> getPlayerHand() { return playerHand; }



}
