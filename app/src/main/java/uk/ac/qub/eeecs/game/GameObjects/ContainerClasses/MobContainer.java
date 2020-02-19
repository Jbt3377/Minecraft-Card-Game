package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;


public class MobContainer extends Sprite implements Container {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isEmpty;
    private Mob containedMob;
    public enum ContainerType{ HUMAN, AI }
    private ContainerType contType;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public MobContainer(float x, float y, ContainerType contType, GameScreen gameScreen){

        // Not ready for use: need to create graphics for field/slot
        super(x, y,
                gameScreen.getGame().getScreenWidth() * 0.104f * 1.17f,
                gameScreen.getGame().getScreenHeight() * 0.185f * 1.17f,
                gameScreen.getGame().getAssetManager().getBitmap("ItemFrame"), gameScreen);
        this.isEmpty = true;
        this.containedMob = null;
        this.contType = contType;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method to occupy Container with placed Card. Creates Mob for Card.
     * @param placedCard - Card dragged and dropped into container
     * @return - Indicates if Card can be dropped inside the container
     */
    public boolean placeCard(CharacterCard placedCard){

        if (isEmpty) {
            containedMob = new Mob(placedCard.getmHP(), placedCard.getmAttackDmg(), mBound.x,
                    mBound.y, placedCard.getmCardPortrait(), mGameScreen);
            isEmpty = false;

            // ToDo: Overlay Mob bitmap on container

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


    public void checkForNewContents(List<TouchEvent> input, Card selectedCard){

        if(this.contType == ContainerType.HUMAN){
            for(TouchEvent event: input){

                float x_cor = event.x;
                float y_cor = convertYAxisToLayerView(event.y);

                if(mBound.contains(x_cor,y_cor) && event.type == TouchEvent.TOUCH_UP){

                    // if empty
                    // remove card
                    // create mob

                    //placeCard(selectedCard);

                }
            }
        }else{
            return;
        }

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isEmpty() {
        return isEmpty;
    }

    public Mob getContents() {
        return containedMob;
    }

    public ContainerType getContType() { return contType; }

}
