package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;

public class MobContainer extends Sprite {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isEmpty;
    private Mob containedMob;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public MobContainer(float x, float y, GameScreen gameScreen){

        // Not ready for use: need to create graphics for field/slot
        super(x, y,
                gameScreen.getGame().getScreenWidth() * 0.104f,
                gameScreen.getGame().getScreenHeight() * 0.185f,
                gameScreen.getGame().getAssetManager().getBitmap("CharacterSlot"), gameScreen);
        this.isEmpty = true;
        this.containedMob = null;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method to occupy Container with placed Card. Creates Mob for Card.
     * @param placedCard - Card dragged and dropped into container
     * @return - Indicates if Card can be dropped inside the container
     */
    public boolean placeCard(Card placedCard){

        if (isEmpty) {
            containedMob = new Mob(placedCard.getmHealth(), placedCard.getmAttack(), mBound.x,
                    mBound.y, placedCard.getmCardPortrait(), mGameScreen);
            isEmpty = false;
            return true;
        } else
            return false;

    }

    /**
     * Method clears container of held mob
     */
    public void emptyContainer() {
        this.isEmpty = true;
        this.containedMob = null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isEmpty() {
        return isEmpty;
    }

    public Mob getContainedMob() {
        return containedMob;
    }

}
