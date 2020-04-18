package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.UtilityCardStats;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.CardContainer;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class UtilityCard extends Card {

    int effect_intensity;

    //Public constructors
    public UtilityCard(float x, float y, GameScreen gameScreen, UtilityCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mBitmap = CardBitmapFactory.returnBitmap(this,gameScreen);
        this.flipTimer = 30;
        this.effect_intensity = cardStats.getEffect_intensity();
    }


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

    public void runUtilityEffect(GameBoard gameBoard){
        switch(this.getCardID()) {
            case 32 :
            case 36 :
                healPlayerHP(gameBoard);
                break;
            case 33 :
            case 34 :
            case 35 :
                damageEnemyHP(gameBoard);
                break;
            case 37:
                damageEnemyMobs(gameBoard);
                break;
            case 38:
                healAllyMobs(gameBoard);
                break;
            case 39 :
                weakenEnemyMobs(gameBoard);
                break;
            case 40:
                strengthenAllyMobs(gameBoard);
        }
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public void setAnimationFinished(boolean animationFinished) {
        this.animationFinished = animationFinished;
    }


    public boolean isAnimationInProgress() {
        return animationInProgress;
    }

    public void setAnimationInProgress(boolean animationInProgress) {
        this.animationInProgress = animationInProgress;
    }

    public void damageEnemyHP(GameBoard gameBoard) {
        mGameScreen.getGame().getAudioManager().play(mGameScreen.getGame().getAssetManager().getSound("potion"));
        gameBoard.getInactivePlayer().setmPlayerHealth(gameBoard.getInactivePlayer().getmPlayerHealth() - this.effect_intensity);
    }

    public void healPlayerHP(GameBoard gameBoard) {
        mGameScreen.getGame().getAudioManager().play(mGameScreen.getGame().getAssetManager().getSound("potion"));
        gameBoard.getActivePlayer().setmPlayerHealth(gameBoard.getActivePlayer().getmPlayerHealth() + this.effect_intensity);
    }

    public void damageEnemyMobs(GameBoard gameBoard) {
        MobContainer.ContainerType contTypeOfEnemy;
        mGameScreen.getGame().getAudioManager().play(mGameScreen.getGame().getAssetManager().getSound("splash_potion"));
        if (gameBoard.isPlayer1Turn()) {
            contTypeOfEnemy = MobContainer.ContainerType.TOP_PLAYER;
        } else {
            contTypeOfEnemy = MobContainer.ContainerType.BOTTOM_PLAYER;
        }
        for (MobContainer mobContainer : gameBoard.getFieldContainers()) {
            if (mobContainer.getContents() != null) {
                if (mobContainer.getContType() == contTypeOfEnemy) {
                    mobContainer.getContents().setHealthPoints(mobContainer.getContents().getHealthPoints() - this.effect_intensity);
                    mobContainer.getContents().updateMobBitmap();
                    if( mobContainer.getContents().getHealthPoints() <= 0){
                        gameBoard.getInactivePlayersMobsOnBoard().remove( mobContainer.getContents());

                        mobContainer.emptyContainer();
                    }
                }
            }
        }
    }

    public void healAllyMobs(GameBoard gameBoard) {
        MobContainer.ContainerType contTypeOfPlayer;
        mGameScreen.getGame().getAudioManager().play(mGameScreen.getGame().getAssetManager().getSound("splash_potion"));
        if (gameBoard.isPlayer1Turn()) {
            contTypeOfPlayer = MobContainer.ContainerType.BOTTOM_PLAYER;
        } else {
            contTypeOfPlayer = MobContainer.ContainerType.TOP_PLAYER;
        }
        for (MobContainer mobContainer : gameBoard.getFieldContainers()) {
            if (mobContainer.getContents() != null) {
                if (mobContainer.getContType() == contTypeOfPlayer) {
                    mobContainer.getContents().setHealthPoints(mobContainer.getContents().getHealthPoints() + this.effect_intensity);
                    mobContainer.getContents().updateMobBitmap();

                }
            }
        }
    }

    public void weakenEnemyMobs(GameBoard gameBoard) {
        MobContainer.ContainerType contTypeOfEnemy;
        int baseAttack;
        mGameScreen.getGame().getAudioManager().play(mGameScreen.getGame().getAssetManager().getSound("splash_potion"));
        if (gameBoard.isPlayer1Turn()) {
            contTypeOfEnemy = MobContainer.ContainerType.TOP_PLAYER;
        } else {
            contTypeOfEnemy = MobContainer.ContainerType.BOTTOM_PLAYER;
        }
        for (MobContainer mobContainer : gameBoard.getFieldContainers()) {
            if (mobContainer.getContents() != null) {
                if (mobContainer.getContType() == contTypeOfEnemy) {
                    baseAttack = mobContainer.getContents().getAttackDamage() - this.effect_intensity;
                    if (baseAttack < 0) {
                        baseAttack = 0;
                    }
                    mobContainer.getContents().setAttackDamage(baseAttack);
                    mobContainer.getContents().updateMobBitmap();
                }
            }
        }
    }

    public void strengthenAllyMobs(GameBoard gameBoard) {
        MobContainer.ContainerType contTypeOfPlayer;
        mGameScreen.getGame().getAudioManager().play(mGameScreen.getGame().getAssetManager().getSound("splash_potion"));
        if (gameBoard.isPlayer1Turn()) {
            contTypeOfPlayer = MobContainer.ContainerType.BOTTOM_PLAYER;
        } else {
            contTypeOfPlayer = MobContainer.ContainerType.TOP_PLAYER;
        }
        for (MobContainer mobContainer : gameBoard.getFieldContainers()) {
            if (mobContainer.getContents() != null) {
                if (mobContainer.getContType() == contTypeOfPlayer) {
                    mobContainer.getContents().setAttackDamage(mobContainer.getContents().getAttackDamage() + this.effect_intensity);
                    mobContainer.getContents().updateMobBitmap();

                }
            }
        }
    }

}
