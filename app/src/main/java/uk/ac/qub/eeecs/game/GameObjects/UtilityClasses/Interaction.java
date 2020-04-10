package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import android.text.method.Touch;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;

public abstract class Interaction {

    public static void processDragEvents(List<TouchEvent> touchEvents, Draggable dObj, Game game) {


        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;

        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;


            if (t.type == TouchEvent.TOUCH_SINGLE_TAP && dObj.getBoundingBox().contains(x_cor, y_cor)) {
                if (game.isMagnificationToggled()) {
                    //TODO - Implement zoom functionality
                }
            }

            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (game.isCardsSelected() == false) && t.type == TouchEvent.TOUCH_DOWN) {
                game.setCardsSelected(true);
                dObj.setHasBeenSelected(true);
                touchOffsetX = x_cor - dObj.getCurrentXPosition();
                touchOffsetY = y_cor - dObj.getCurrentYPosition();

            }


            if (t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected() && !game.isMagnificationToggled()) {
                dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);

            }

            if (t.type == TouchEvent.TOUCH_UP) {
                game.setCardsSelected(false);
                dObj.setHasBeenSelected(false);
            }

        }
    }

    public static void moveCardToContainer(List<TouchEvent> touchEvents, Draggable dObj, Game game, GameBoard gameBoard) {
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;


        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (game.isCardsSelected() == false) && t.type == TouchEvent.TOUCH_DOWN) {
                game.setCardsSelected(true);
                dObj.setHasBeenSelected(true);
                touchOffsetX = x_cor - dObj.getCurrentXPosition();
                touchOffsetY = y_cor - dObj.getCurrentYPosition();
                dObj.setOriginalXPos(dObj.getCurrentXPosition());
                dObj.setOriginalYPos(dObj.getCurrentYPosition());
            }

            if (t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected()) {
                dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);
            }

            if(t.type == TouchEvent.TOUCH_UP && dObj.getHasBeenSelected()){
                for(MobContainer mb : gameBoard.getFieldContainers()) {
                    if (mb.checkForNewContents(touchEvents, dObj)) {
                        int index = gameBoard.getHumanHand().getPlayerHand().indexOf(dObj);
                        Card card = gameBoard.getHumanHand().getPlayerHand().get(index);

                        if (gameBoard.getHumanPlayer().getmPlayerMana() - card.getManaCost() >= 0) {
                            System.out.println("A card has been dropped in this container");
                            Mob mob = new Mob(mb.getX_location(), mb.getY_location(), gameBoard.getGameScreen(), (CharacterCard) dObj);
                            gameBoard.getPlayer1MobsOnBoard().add(mob);
                            mb.placeCard(mob);
                            game.setCardsSelected(false);
                            dObj.setHasBeenSelected(false);

                            gameBoard.getHumanHand().getPlayerHand().remove(index);
                            System.out.println("Index of lifted card: " + index);
                            gameBoard.getHumanPlayer().setmPlayerMana(gameBoard.getHumanPlayer().getmPlayerMana() - card.getManaCost());
                        }
                    }
                }
                System.out.println("No card was dropped into a container");
                dObj.setNewPosition(dObj.getOriginalXPos(), dObj.getOriginalYPos());
                game.setCardsSelected(false);
                dObj.setHasBeenSelected(false);
            }
        }
    }
}





