package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;

public class EquipCard extends Card {
    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats) {
        super(x, y, gameScreen, cardStats.getId(), cardStats.getName(), cardStats.getDescText());



    }
}
