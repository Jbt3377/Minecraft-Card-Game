package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.ArrayList;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
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

    public static void processMobSelection(List<TouchEvent> touchEvents, Game game, GameBoard gameBoard) {

        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            // Mob Selection Checks
            if (t.type == TouchEvent.TOUCH_DOWN && gameBoard.getActivePlayer() instanceof Human) {

                // Variables necessary for container type check
                MobContainer.ContainerType containerTypeForSelection, containerTypeForTargeting;
                if (gameBoard.isPlayer1Turn()) {
                    containerTypeForSelection = MobContainer.ContainerType.BOTTOM_PLAYER;
                    containerTypeForTargeting = MobContainer.ContainerType.TOP_PLAYER;
                } else {
                    containerTypeForSelection = MobContainer.ContainerType.TOP_PLAYER;
                    containerTypeForTargeting = MobContainer.ContainerType.BOTTOM_PLAYER;
                }


                // Step 1: Detect a selected mob
                for (MobContainer container : gameBoard.getFieldContainers()) {

                    if (!container.isEmpty() && container.getContType() == containerTypeForSelection) {

                        Mob clickedMob = container.getContents();
                        if (clickedMob.getBound().contains(x_cor, y_cor) && !clickedMob.hasBeenUsed()) {

                            int clickedMobID = clickedMob.getId();
                            Mob currentlySelectedMob = (gameBoard.getActivePlayer()).getSelectedMob();

                            // Check for same mob selected twice, if so deselect
                            if ((currentlySelectedMob != null) && (clickedMobID == currentlySelectedMob.getId() && !game.isMagnificationToggled())) {
                                clickedMob.setSelectedToAttack(false);
                                clickedMob.updateMobBitmap();
                                (gameBoard.getActivePlayer()).setSelectedMob(null);
                                System.out.println("========== Mob Deselected =========");
                                break;
                            }
                            // Otherwise, set selected mob as currently selected mob
                            else {
                                if (!game.isMagnificationToggled()) {
                                    try {
                                        gameBoard.getActivePlayer().getSelectedMob().setSelectedToAttack(false);
                                        gameBoard.getActivePlayer().getSelectedMob().updateMobBitmap();
                                    } catch (NullPointerException np) {
                                        System.out.println("Ohh NO!");
                                    }
                                    (gameBoard.getActivePlayer()).setSelectedMob(clickedMob);
                                    clickedMob.setSelectedToAttack(true);
                                    clickedMob.updateMobBitmap();
                                    System.out.println("========== Mob Selected =========");
                                    break;
                                }
                            }
                        }
                    }
                }


                // Step 2: Detect a targeted mob
                Mob currentlySelectedMob = (gameBoard.getActivePlayer()).getSelectedMob();
                Mob currentlyTargetedMob = (gameBoard.getActivePlayer()).getTargetedMob();
                if (currentlySelectedMob != null && currentlyTargetedMob == null) {

                    // Check each opponent mob and determine if one was clicked
                    for (MobContainer container : gameBoard.getFieldContainers()) {

                        if (!container.isEmpty() && container.getContType() == containerTypeForTargeting) {

                            Mob clickedMob = container.getContents();
                            if (clickedMob.getBound().contains(x_cor, y_cor)) {

                                // Targeted mob identified, update accordingly
                                (gameBoard.getActivePlayer()).setTargetedMob(clickedMob);
                                System.out.println("========== Mob Targeted =========");
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void processCardMagnification(List<TouchEvent> touchEvents, Draggable dObj, Game game, PlayerHand playerHand) {
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;
            Card card;
            game.getAudioManager().setSfxVolume(0.2f);
            for (TouchEvent t : touchEvents) {
                float x_cor = t.x;
                float y_cor = game.getScreenHeight() - t.y;

                if (t.type == TouchEvent.TOUCH_DOWN && dObj.getBoundingBox().contains(x_cor, y_cor)) {
                    if (game.isMagnificationToggled()) {
                        int index = playerHand.getPlayerHand().indexOf(dObj);
                            card = playerHand.getPlayerHand().get(index);

                            game.setDrawCard(true);
                            game.setMagnifiedCard(card, game.getScreenManager().getCurrentScreen(), card.getCardStats());
                            game.getAudioManager().play(game.getAssetManager().getSound("zoom-in"));
                    }
                }

                if (t.type == TouchEvent.TOUCH_UP) {
                    if (game.isMagnificationToggled() ) {

                        if (game.drawCard){
                            game.getAudioManager().play(game.getAssetManager().getSound("zoom-out"));
                        }
                        game.setDrawCard(false);;
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

            // Variables necessary for container type check
            MobContainer.ContainerType contTypeOfPlayer;
            if (gameBoard.isPlayer1Turn()) {
                contTypeOfPlayer = MobContainer.ContainerType.BOTTOM_PLAYER;
            } else {
                contTypeOfPlayer = MobContainer.ContainerType.TOP_PLAYER;
            }

            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (!game.isCardsSelected()) && t.type == TouchEvent.TOUCH_DOWN) {
                game.setCardsSelected(true);
                dObj.setHasBeenSelected(true);
                touchOffsetX = x_cor - dObj.getCurrentXPosition();
                touchOffsetY = y_cor - dObj.getCurrentYPosition();
                dObj.setOriginalXPos(dObj.getCurrentXPosition());
                dObj.setOriginalYPos(dObj.getCurrentYPosition());
            }

            if (t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected() && !game.isMagnificationToggled()) {
                dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);
            }

            if (t.type == TouchEvent.TOUCH_UP && dObj.getHasBeenSelected()) {
                for (MobContainer mb : gameBoard.getFieldContainers()) {

                    if (mb.checkForNewContents(touchEvents, dObj) && mb.getContType() == contTypeOfPlayer) {
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

    public static void moveUtilityCardToContainer(List<TouchEvent> touchEvents, Draggable dObj, Game game, GameBoard gameBoard) {
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;


        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (!game.isCardsSelected()) && t.type == TouchEvent.TOUCH_DOWN) {
                System.out.println("Reached this statement for utils");
                game.setCardsSelected(true);
                dObj.setHasBeenSelected(true);
                touchOffsetX = x_cor - dObj.getCurrentXPosition();
                touchOffsetY = y_cor - dObj.getCurrentYPosition();
                dObj.setOriginalXPos(dObj.getCurrentXPosition());
                dObj.setOriginalYPos(dObj.getCurrentYPosition());
            }

            if (t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected() && !game.isMagnificationToggled()) {
                dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);
                System.out.println("Dragging utils card");
            }

            if (t.type == TouchEvent.TOUCH_UP && dObj.getHasBeenSelected()) {
                if (gameBoard.getUtilityCardContainer().checkForUtilityCard(touchEvents, dObj)) {
                    int index = gameBoard.getActivePlayerHand().getPlayerHand().indexOf(dObj);
                    Card card = gameBoard.getActivePlayerHand().getPlayerHand().get(index);

                    System.out.println("Utility card detected in utils container");
                    UtilityCard utilityCard = (UtilityCard) card;
                    if (gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost() >= 0 && !(utilityCard.isAnimationInProgress())) {
                                System.out.println("Playing this utils card");
                                utilityCard.setAnimationInProgress(true);
                                dObj.setOriginalXPos(dObj.getCurrentXPosition());
                                dObj.setOriginalYPos(dObj.getCurrentYPosition());
                                game.setCardsSelected(false);
                                dObj.setHasBeenSelected(false);

                            }
                    }   dObj.setNewPosition(dObj.getOriginalXPos(), dObj.getOriginalYPos());
                        game.setCardsSelected(false);
                        dObj.setHasBeenSelected(false);
                }
            }

        }

    public static void moveEquipCardToContainer(List<TouchEvent> touchEvents, Draggable dObj, Game game, GameBoard gameBoard) {
        float touchOffsetX = 0.0f;
        float touchOffsetY = 0.0f;

        for (TouchEvent t : touchEvents) {
            float x_cor = t.x;
            float y_cor = game.getScreenHeight() - t.y;

            MobContainer.ContainerType contTypeOfPlayer;

            if (gameBoard.isPlayer1Turn()) {
                contTypeOfPlayer = MobContainer.ContainerType.BOTTOM_PLAYER;
            } else {
                contTypeOfPlayer = MobContainer.ContainerType.TOP_PLAYER;
            }



            if (dObj.getBoundingBox().contains(x_cor, y_cor) && (!game.isCardsSelected()) && t.type == TouchEvent.TOUCH_DOWN) {
                System.out.println("Reached this statement for equip");
                game.setCardsSelected(true);
                dObj.setHasBeenSelected(true);
                touchOffsetX = x_cor - dObj.getCurrentXPosition();
                touchOffsetY = y_cor - dObj.getCurrentYPosition();
                dObj.setOriginalXPos(dObj.getCurrentXPosition());
                dObj.setOriginalYPos(dObj.getCurrentYPosition());
            }

            if (t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected() && !game.isMagnificationToggled()) {
                dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);
                System.out.println("Dragging equip card");
            }

            if (t.type == TouchEvent.TOUCH_UP && dObj.getHasBeenSelected()){
                for (MobContainer mb : gameBoard.getFieldContainers()) {

                    if ((!mb.isEmpty()) && mb.getContType() == contTypeOfPlayer && mb.getBound().contains(x_cor, y_cor)) {
                        System.out.println("An equip card is over an occupied container");

                        Mob mob = mb.getContainedMob();
                        int index = gameBoard.getActivePlayerHand().getPlayerHand().indexOf(dObj);
                        EquipCard card = (EquipCard) gameBoard.getActivePlayerHand().getPlayerHand().get(index);


                        if(gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost() >= 0 && (!card.isAnimationInProgress())) {

                            if (mob.getEquipCard() == null) {
                                System.out.println("Looks like we can equipped a card");
                                mob.setEquipCard(card);
                                if (mob.getEquipCard() != null) {
                                    System.out.println("Card is equipped");
                                    mob.setAttackDamage(mob.getAttackDamage() + 4);
                                    mob.updateMobBitmap();
                                    card.setAnimationInProgress(true);
                                    dObj.setOriginalXPos(mb.getX_location());
                                    dObj.setOriginalYPos(mb.getY_location());
                                    game.setCardsSelected(false);
                                    dObj.setHasBeenSelected(false);
                                }

                            }

                        }
                    }

                    dObj.setNewPosition(dObj.getOriginalXPos(), dObj.getOriginalYPos());
                    game.setCardsSelected(false);
                    dObj.setHasBeenSelected(false);

                }
            }
        }
    }

}















