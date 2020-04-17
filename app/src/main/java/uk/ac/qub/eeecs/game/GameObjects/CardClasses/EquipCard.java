package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class EquipCard extends Card {

    int effect_intensity;
    public int getEffect_intensity() {
        return effect_intensity;
    }


    /**
     * Public constructor for EquipCard object.
     *
     * @param x          - X Co-ordinate for the card for drawing on screen.
     * @param y          - Y Co-ordinate for the card for drawing on screen.
     * @param gameScreen - The GameScreen in which the card will be drawn.
     * @param cardStats  - The CardStats(name, hp, etc) for this card.
     */
    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mBitmap = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
        this.effect_intensity = cardStats.getEffect_intensity();
    }

    /**
     * Overloaded constructor for Card object, used during magnification process.
     *
     * @param x          - X Co-ordinate for the card for drawing on screen.
     * @param y          - Y Co-ordinate for the card for drawing on screen.
     * @param gameScreen - The GameScreen in which the card will be drawn.
     * @param cardStats  - The CardStats(name, hp, etc) for this card.
     * @param changeSize  - The scaling size for the card being magnified.
     * @param effect_intensity - The amount that the mob's attack will be increased by.
     */


    /**
     * This method is used to change for a short animation when the EquipCard is used.
     * The card will alter height and width while the flip timer is not zero
     * When the flip timer reaches zero, the cards height and width is set to zero,
     * And the animation is finished
     *
     * Created by Matthew McCleave
     */
    public void runCardAnimation(){
        if(flipTimer == 0){
            setWidth(0);
            setHeight(0);
            this.setAnimationFinished(true);
        }
        else{
            setWidth(DEFAULT_CARD_WIDTH - (scale * (FLIP_TIME - flipTimer)));
            setHeight(DEFAULT_CARD_HEIGHT - (scale * (FLIP_TIME - flipTimer)));
        }

        flipTimer--;
    }

    public boolean isAnimationInProgress() {
        return animationInProgress;
    }

    public void setAnimationInProgress(boolean animationInProgress) {
        this.animationInProgress = animationInProgress;
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public void setAnimationFinished(boolean animationFinished) {
        this.animationFinished = animationFinished;
    }

}
