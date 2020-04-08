package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class UtilityCard extends Card {
    public UtilityCard(float x, float y, GameScreen gameScreen, UtilityCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
    }
}
