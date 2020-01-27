package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;

public class CardContainer extends Sprite implements Container {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Properties
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isEmpty;
    private Card containedCard;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public CardContainer(float x, float y, GameScreen gameScreen){

        // Not ready for use: need to create graphics for field/slot
        super(x, y,
                gameScreen.getGame().getScreenWidth() * 0.104f,
                gameScreen.getGame().getScreenHeight() * 0.185f,
                gameScreen.getGame().getAssetManager().getBitmap("CharacterSlot"), gameScreen);
        this.isEmpty = true;
        this.containedCard = null;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void emptyContainer() {
        this.isEmpty = true;
        this.containedCard = null;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Card getContents() {
        return containedCard;
    }



}
