package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.EquipCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class EquipCard extends Card {




    //Properties
    boolean animationInProgress = false;
    int equipFlipTimer = 30;
    boolean animationFinished = false;


    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
    }

    public EquipCard(float x, float y, GameScreen gameScreen, EquipCardStats cardStats, int changeSize) {
        super(x, y, gameScreen, cardStats, changeSize);
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);



    }

    public void equipCardAnimation(){
        if(equipFlipTimer == 0){
            setWidth(0);
            setHeight(0);
            this.setAnimationFinished(true);
        }
        else{
            setWidth(DEFAULT_CARD_WIDTH - (scale * (FLIP_TIME - equipFlipTimer)));
            setHeight(DEFAULT_CARD_HEIGHT - (scale * (FLIP_TIME - equipFlipTimer)));
        }

        equipFlipTimer--;
    }








    public boolean isAnimationInProgress() {
        return animationInProgress;
    }

    public void setAnimationInProgress(boolean animationInProgress) {
        this.animationInProgress = animationInProgress;
    }

    public int getEquipFlipTimer() {
        return equipFlipTimer;
    }

    public void setEquipFlipTimer(int equipFlipTimer) {
        this.equipFlipTimer = equipFlipTimer;
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public void setAnimationFinished(boolean animationFinished) {
        this.animationFinished = animationFinished;
    }

}
