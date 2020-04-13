package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;

public class Mob extends Sprite {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private int healthPoints, attackDamage;
    private int id;
    private String name;
    private Sound damagedSound, attackSound, deathSound;
    private boolean hasBeenUsed;
    private static int nextID = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public Mob(float x_cor, float y_cor, GameScreen gameScreen, CharacterCard characterCard) {

        // Creates sprite
        super(x_cor, y_cor, gameScreen.getGame().getScreenWidth() * 0.104f, gameScreen.getGame().getScreenHeight() * 0.185f,
                null, gameScreen);

        this.id = Mob.nextID;
        Mob.nextID++;
        this.name = characterCard.getCardName();
        this.healthPoints = characterCard.getmHP();
        this.attackDamage = characterCard.getmAttackDmg();
        this.mBitmap = CardBitmapFactory.returnMobBitmap(this,gameScreen);
        this.hasBeenUsed = false;

        //setEffectVolume(gameScreen);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Function used to set/update volume level for mob sound effects
     * @param gameScreen - Required to reference set volume level
     */
    private void setEffectVolume(GameScreen gameScreen){
        damagedSound.setVolume(gameScreen.getGame().getVolume());
        attackSound.setVolume(gameScreen.getGame().getVolume());
        deathSound.setVolume(gameScreen.getGame().getVolume());
    }

    /**
     * Function to set position of mob
     * @param x - x axis pos
     * @param y - y axis pos
     */
//    public void setMobPosition(float x, float y){
//        this.setPosition(x,y);
//        this.mobBitmapDrawRect.set(
//                (int) mBound.getLeft(),
//                (int) mBound.getBottom(),
//                (int) mBound.getRight(),
//                (int) position.y);
//    }


    /**
     * Process of attacking an opponent card
     */
    public void attackTarget(Mob targetedMob) {

        // Enemy Mob takes Damage
        targetedMob.decreaseHP(this.attackDamage);
        ////attackSound.play();

        // Enemy Mob - Death/Damaged Sounds
        ////if(targetedMob.getHealthPoints() <= 0)
        ////    targetedMob.getDeathSound().play();

        // Mob has attacked, can no longer be used this turn
        this.hasBeenUsed = true;
    }


    /**
     * Process of attacking the opponents health
     */
    public void attackPlayer(Player targetedPlayer) {

        // TODO: Process of attacking player

    }


    /**
     * Modifies the HP of the Mob
     * @param damageInflicted - Pos/Neg integer input
     */
    private void decreaseHP(int damageInflicted){
        System.out.println(">>>>> HP Before Attack: " + this.healthPoints);
        this.healthPoints -= damageInflicted;
        System.out.println(">>>>> HP After Attack: " + this.healthPoints);
        // (damageInflicted > 0 ? "+" : "-")

        new PopUpObject(position.x+70, position.y-190, getGameScreen(), 30,
                "-" + damageInflicted, 5, true);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Draw Method
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D,layerViewport, screenViewport);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getHealthPoints(){ return healthPoints; }

    public void setHealthPoints(int healthPoints){ this.healthPoints = healthPoints; }

    public int getAttackDamage(){ return attackDamage; }

    public void setAttackDamage(int attackDamage){ this.attackDamage = attackDamage; }

    public Sound getDamagedSound(){ return damagedSound; }

    public void setDamagedSound(Sound damagedSound){ this.damagedSound = damagedSound; }

    public Sound getAttackSound(){ return attackSound; }

    public void setAttackSound(Sound attackSound){ this.attackSound = attackSound; }

    public Sound getDeathSound(){ return deathSound; }

    public void setDeathSound(Sound deathSound){ this.deathSound = deathSound; }

    public String getName(){
        return this.name;
    }

    public int getId() { return id; }

    public boolean hasBeenUsed() {
        return hasBeenUsed;
    }

    public void setHasBeenUsed(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }

    public void updateMobBitmap(){
        this.mBitmap = CardBitmapFactory.returnMobBitmap(this, getGameScreen());
    }


}
