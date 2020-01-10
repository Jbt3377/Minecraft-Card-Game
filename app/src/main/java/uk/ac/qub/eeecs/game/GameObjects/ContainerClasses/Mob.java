package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import android.graphics.Bitmap;
import android.graphics.Rect;

import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;

public class Mob extends Sprite {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private int healthPoints, attackDamage;
    private Bitmap mobBitmap;
    private Rect mobBitmapDrawRect = new Rect();
    private Sound damagedSound, attackSound, deathSound;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor
     * @param healthPoints - hp of mob
     * @param attackDamage - damage inflicted by mob
     * @param creatureBitmap - img of the mob
     */
    public Mob(int healthPoints, int attackDamage, float x, float y, Bitmap creatureBitmap, GameScreen gameScreen) {

        // Creates sprite
        super(x, y, gameScreen.getGame().getScreenWidth() * 0.104f, gameScreen.getGame().getScreenHeight() * 0.185f,
                gameScreen.getGame().getAssetManager().getBitmap("CreatureBorder"), gameScreen);

        this.healthPoints = healthPoints;
        this.attackDamage = attackDamage;
        this.mobBitmap = creatureBitmap;

        mobBitmapDrawRect.set(
                (int) mBound.getLeft(),
                (int) mBound.getBottom(),
                (int) mBound.getRight(),
                (int) position.y);


        setEffectVolume(gameScreen);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods - TODO: Implement Prep/Attack Phase
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
    public void setMobPosition(float x, float y){
        this.setPosition(x,y);
        this.mobBitmapDrawRect.set(
                (int) mBound.getLeft(),
                (int) mBound.getBottom(),
                (int) mBound.getRight(),
                (int) position.y);
    }


    /**
     * Process of attacking an opponent card
     * @param targetedMob
     */
    public void attackTarget(Mob targetedMob) {

        // Enemy Mob takes Damage
        targetedMob.changeHP(attackDamage);
        attackSound.play();

        // Enemy Mob - Death/Damaged Sounds
        if(targetedMob.getHealthPoints() <= 0)
            targetedMob.getDeathSound().play();

        // Attacking Mob takes Damage
        changeHP(-targetedMob.getAttackDamage());

        // Attacking Mob - Death Sound
        if(healthPoints <= 0)
            getDeathSound().play();

        return;
    }


    /**
     * Process of attacking the opponents health
     * @param targetedPlayer
     */
    public void attackTarget(Player targetedPlayer) {

        // TODO: Process of attacking opponent

        return;
    }


    /**
     * Modifies the HP of the Mob
     * @param hpVariant - Pos/Neg integer input
     */
    public void changeHP(int hpVariant){

        this.healthPoints += hpVariant;
        new PopUpObject(position.x, position.y, getGameScreen(), 30,
                (hpVariant > 0 ? "+" : "-") + hpVariant, 5, true);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Draw Method
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.drawBitmap(mobBitmap, null, mobBitmapDrawRect, null);
        super.draw(elapsedTime, graphics2D);
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

}
