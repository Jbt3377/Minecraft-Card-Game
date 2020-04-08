package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class EquipCard extends Card {
    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);



    }
}
