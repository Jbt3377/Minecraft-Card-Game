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


    public static void processTouchEvents(List<TouchEvent> touchEvents, Game game, GameBoard gameBoard){

        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            // Mob Selection Checks
            if (t.type == TouchEvent.TOUCH_DOWN && gameBoard.getPlayer1() instanceof Human) {

                // Step 1: Detect a selected mob
                for (Mob mob : gameBoard.getPlayer1MobsOnBoard()) {
                    if (mob.getBound().contains(x_cor, y_cor)) {

                        int clickedMobID = mob.getId();
                        Mob currentlySelectedMob = ((Human) gameBoard.getPlayer1()).getSelectedMob();

                        // Check for same mob selected twice, if so deselect
                        if ((currentlySelectedMob != null) && (clickedMobID == currentlySelectedMob.getId())) {
                            ((Human) gameBoard.getPlayer1()).setSelectedMob(null);
                            System.out.println("========== Mob Deselected =========");
                            break;
                        }
                        // Otherwise, set selected mob as currently selected mob
                        else {
                            ((Human) gameBoard.getPlayer1()).setSelectedMob(mob);
                            System.out.println("========== Mob Selected =========");
                            break;
                        }
                    }
                }

                // Step 2: Detect a targeted mob
                Mob currentlySelectedMob = ((Human) gameBoard.getPlayer1()).getSelectedMob();
                Mob currentlyTargetedMob = ((Human) gameBoard.getPlayer1()).getTargetedMob();
                if(currentlySelectedMob != null && currentlyTargetedMob == null){

                    // Check each opponent mob and determine if one was clicked
                    for(Mob mob: gameBoard.getPlayer2MobsOnBoard()){

                        if(mob.getBound().contains(x_cor, y_cor)){

                            // Targeted mob identified, update accordingly
                            ((Human) gameBoard.getPlayer1()).setTargetedMob(mob);
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
                        int index = gameBoard.getPlayer1Hand().getPlayerHand().indexOf(dObj);
                        Card card = gameBoard.getPlayer1Hand().getPlayerHand().get(index);

                        if (gameBoard.getPlayer1().getmPlayerMana() - card.getManaCost() >= 0) {
                            System.out.println("A card has been dropped in this container");
                            Mob mob = new Mob(mb.getX_location(), mb.getY_location(), gameBoard.getGameScreen(), (CharacterCard) dObj);
                            gameBoard.getPlayer1MobsOnBoard().add(mob);
                            mb.placeCard(mob);
                            game.setCardsSelected(false);
                            dObj.setHasBeenSelected(false);

                            gameBoard.getPlayer1Hand().getPlayerHand().remove(index);
                            System.out.println("Index of lifted card: " + index);
                            gameBoard.getPlayer1().setmPlayerMana(gameBoard.getPlayer1().getmPlayerMana() - card.getManaCost());
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
                    gameBoard.getPlayer2().setmPlayerMana(gameBoard.getPlayer2().getmPlayerMana() - card.getManaCost());
                    gameBoard.getPlayer2Hand().getPlayerHand().remove(i);
                }
            }
        }



    }
}





