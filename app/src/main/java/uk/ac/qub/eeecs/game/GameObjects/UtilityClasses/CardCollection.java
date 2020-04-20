package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;


import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;

/*
 *
 * @version 1.0
 */
public class CardCollection {

    /**
     * Get All Card Collection
     *
     * Turns the card stats into a collection of cards to be used for display
     *
     * @param gameScreen
     * @param cardStatsCollection
     * @return
     */
    public static ArrayList<Card> getAllCardCollection(GameScreen gameScreen, ArrayList<CardStats> cardStatsCollection) {

        ArrayList<Card> AllCardCollection = new ArrayList<>();

        for(CardStats x : cardStatsCollection){
            //CharacterCardStats check
            if(x instanceof CharacterCardStats){
                CharacterCard card = new CharacterCard(0, 0, gameScreen, (CharacterCardStats)x);
                AllCardCollection.add(card);

            }
            //UtilityCardStats check
            if(x instanceof UtilityCardStats){
                UtilityCard card = new UtilityCard(0, 0, gameScreen, (UtilityCardStats)x);
                AllCardCollection.add(card);

            }
            //EquipCardStats check
            if(x instanceof EquipCardStats){
                EquipCard card = new EquipCard(0, 0, gameScreen, (EquipCardStats)x);
                AllCardCollection.add(card);
            }
        }
        return AllCardCollection;
    }
}




