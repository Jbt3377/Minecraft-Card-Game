package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;

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

            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (!game.isCardsSelected()) && t.type == TouchEvent.TOUCH_DOWN) {
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


    public static void processMobSelection(List<TouchEvent> touchEvents, Game game, GameBoard gameBoard){

        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            // Mob Selection Checks
            if (t.type == TouchEvent.TOUCH_DOWN && gameBoard.getActivePlayer() instanceof Human) {

                // Step 1: Detect a selected mob
                for (Mob mob : gameBoard.getActivePlayersMobsOnBoard()) {

                    // Check if player mob clicked which hasn't been used yet
                    if (mob.getBound().contains(x_cor, y_cor) && !mob.hasBeenUsed()) {

                        int clickedMobID = mob.getId();
                        Mob currentlySelectedMob = ((Human) gameBoard.getActivePlayer()).getSelectedMob();

                        // Check for same mob selected twice, if so deselect
                        if ((currentlySelectedMob != null) && (clickedMobID == currentlySelectedMob.getId())) {
                            ((Human) gameBoard.getActivePlayer()).setSelectedMob(null);
                            System.out.println("========== Mob Deselected =========");
                            break;
                        }
                        // Otherwise, set selected mob as currently selected mob
                        else {
                            ((Human) gameBoard.getActivePlayer()).setSelectedMob(mob);
                            System.out.println("========== Mob Selected =========");
                            break;
                        }
                    }
                }

                // Step 2: Detect a targeted mob
                Mob currentlySelectedMob = ((Human) gameBoard.getActivePlayer()).getSelectedMob();
                Mob currentlyTargetedMob = ((Human) gameBoard.getActivePlayer()).getTargetedMob();
                if(currentlySelectedMob != null && currentlyTargetedMob == null){

                    // Check each opponent mob and determine if one was clicked
                    for(Mob mob: gameBoard.getInactivePlayersMobsOnBoard()){

                        if(mob.getBound().contains(x_cor, y_cor)){

                            // Targeted mob identified, update accordingly
                            ((Human) gameBoard.getActivePlayer()).setTargetedMob(mob);
                            System.out.println("========== Mob Targeted =========");
                            break;
                        }
                    }

                }


            }
        }

    }


    public static void moveCardToContainer(List<TouchEvent> touchEvents, Draggable dObj, Game game, GameBoard gameBoard) {
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;


        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (!game.isCardsSelected()) && t.type == TouchEvent.TOUCH_DOWN) {
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
                        int index = gameBoard.getActivePlayerHand().getPlayerHand().indexOf(dObj);
                        Card card = gameBoard.getActivePlayerHand().getPlayerHand().get(index);

                        if (gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost() >= 0) {
                            System.out.println("A card has been dropped in this container");
                            Mob mob = new Mob(mb.getX_location(), mb.getY_location(), gameBoard.getGameScreen(), (CharacterCard) dObj);
                            gameBoard.getActivePlayersMobsOnBoard().add(mob);
                            mb.placeCard(mob);
                            game.setCardsSelected(false);
                            dObj.setHasBeenSelected(false);

                            gameBoard.getActivePlayerHand().getPlayerHand().remove(index);
                            System.out.println("Index of lifted card: " + index);
                            gameBoard.getActivePlayer().setmPlayerMana(gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost());
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

    public static void moveAiCardToContainer(GameBoard gameBoard){
        ArrayList<MobContainer> aiMobContainers = new ArrayList<>();
        for(MobContainer mobContainer : gameBoard.getFieldContainers()){
            if(mobContainer.getContType() == MobContainer.ContainerType.TOP_PLAYER){
                aiMobContainers.add(mobContainer);
            }
        }


        for (MobContainer mc: aiMobContainers) {
            for(int i = 0; i < gameBoard.getPlayer2Hand().getPlayerHand().size(); i++){
                Card card = gameBoard.getPlayer2Hand().getPlayerHand().get(i);

                if(mc.isEmpty() && card instanceof CharacterCard && (gameBoard.getPlayer2().getmPlayerMana()- card.getManaCost() >=0)){
                    Mob mob = new Mob(mc.getX_location(),mc.getY_location(),gameBoard.getGameScreen(),(CharacterCard) card);
                    mc.placeCard(mob);
                    gameBoard.getPlayer2MobsOnBoard().add(mob);
                    gameBoard.getPlayer2().setmPlayerMana(gameBoard.getPlayer2().getmPlayerMana() - card.getManaCost());
                    gameBoard.getPlayer2Hand().getPlayerHand().remove(i);
                }
            }
        }



    }
}





