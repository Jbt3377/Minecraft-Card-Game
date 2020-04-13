package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;


public class TurnManager {

    enum Phase {
        INACTIVE, SETUP, PREP, MOVE, BATTLE, END
    }

    // Properties
    private GameBoard gameBoard;
    private Phase player1PhaseFlag;
    private Phase player2PhaseFlag;
    private boolean isPlayer1Turn;
    private MainGameScreen mainGameScreen;
    private Game game;
    private List<TouchEvent> input;

    // Constructor
    public TurnManager(GameBoard gameBoard, MainGameScreen mainGameScreen, Game game) {
        this.gameBoard = gameBoard;
        this.player1PhaseFlag = Phase.SETUP;
        this.player2PhaseFlag = Phase.SETUP;
        this.mainGameScreen = mainGameScreen;
        this.game = game;
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {
        gameBoard.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }

    public void update() {
        Input touchInputs = game.getInput();
        input = touchInputs.getTouchEvents();

        if (player1PhaseFlag == Phase.SETUP && player2PhaseFlag == Phase.SETUP)
            phaseSetup();

        if (isPlayer1Turn)
            phaseCheckHuman(player1PhaseFlag);
        else {

            if (gameBoard.getPlayer2() instanceof Human)
                phaseCheckHuman(player2PhaseFlag);
            else
                phaseCheckAi();
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    // Phase Methods
    ////////////////////////////////////////////////////////////////////////////

    private void phaseSetup() {

        // Replenish both Player's Hands
        gameBoard.getPlayer1Hand().replenishHand();
        gameBoard.getPlayer2Hand().replenishHand();

        // TODO: Feature to set which player goes first
        player1PhaseFlag = Phase.MOVE;
        player2PhaseFlag = Phase.INACTIVE;

        isPlayer1Turn = true;
        gameBoard.setIsPlayer1Turn(true);

        addStartTurnPopup();
    }

    private void phasePrepHuman() {

        // Replenish Player's Hand
        gameBoard.getActivePlayerHand().replenishHand();

//        this.isPlayer1Turn = !isPlayer1Turn;
//        gameBoard.setIsPlayer1Turn(isPlayer1Turn);

        // Increment Turn Counter
        mainGameScreen.setTurnNumber(mainGameScreen.getTurnNumber() + 1);

        // Update Phase flags accordingly
        if(isPlayer1Turn){
            player1PhaseFlag = Phase.MOVE;
            player2PhaseFlag = Phase.INACTIVE;
        }else{
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.MOVE;
        }

        addStartTurnPopup();
    }

    private void phasePrepAi(){

        // Replenish Player's Hand
        gameBoard.getPlayer2Hand().replenishHand();

        // Increment Turn Counter
        mainGameScreen.setTurnNumber(mainGameScreen.getTurnNumber() + 1);

        // Update Phase flags accordingly
        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.MOVE;

        addStartTurnPopup();
    }

    private void phaseMoveHuman() {

        // Check for card drops in container
        for (int i = 0; i < gameBoard.getActivePlayerHand().getPlayerHand().size(); i++) {
            Card card;
            card = gameBoard.getActivePlayerHand().getPlayerHand().get(i);
            Interaction.moveCardToContainer(input, card, game, gameBoard);
        }

        // Check for mob selection
        Interaction.processMobSelection(input, game, gameBoard);

        // Check if targeted mob selected, if so, switch to battle phase
        if (gameBoard.getActivePlayer() instanceof Human) {
            if (((Human) gameBoard.getActivePlayer()).getTargetedMob() != null) {
                if(isPlayer1Turn) {
                    player1PhaseFlag = Phase.BATTLE;
                    player2PhaseFlag = Phase.INACTIVE;
                }else{
                    player1PhaseFlag = Phase.INACTIVE;
                    player2PhaseFlag = Phase.BATTLE;
                }
            }
        }

        // Check for end turn button clicked
        if (mainGameScreen.getEndTurnButton().isPushTriggered()) {

            // Update Phases Accordingly
            if(isPlayer1Turn){
                player1PhaseFlag = Phase.END;
                player2PhaseFlag = Phase.INACTIVE;
            }else{
                player1PhaseFlag = Phase.INACTIVE;
                player2PhaseFlag = Phase.END;
            }
        }
    }

    private void phaseMoveAi(){

        Interaction.moveAiCardToContainer(gameBoard);

        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.BATTLE;
    }


    private void phaseBattleHuman() {

        // Check for mob selection
        Interaction.processMobSelection(input, game, gameBoard);

        Mob currentlySelectedMob = ((Human) gameBoard.getActivePlayer()).getSelectedMob();
        Mob currentlyTargetedMob = ((Human) gameBoard.getActivePlayer()).getTargetedMob();

        // Check if selected mob and targeted mob not null
        if((currentlySelectedMob != null) && (currentlyTargetedMob != null)){

            // Run attack sequence (!!!)
            currentlySelectedMob.attackTarget(currentlyTargetedMob);
            currentlyTargetedMob.updateMobBitmap();

            // Set selected and targeted mobs as null
            ((Human) gameBoard.getActivePlayer()).setSelectedMobNull();
            ((Human) gameBoard.getActivePlayer()).setTargetedMobNull();

        }

        // Check for mob death
        ArrayList<Mob> updatedOpponentMobsOnBoard = new ArrayList<>();
        for(Mob currentMobToCheck: gameBoard.getInactivePlayersMobsOnBoard()){

            // If Mob has died, remove it from their list of mobs on board
            if(currentMobToCheck.getHealthPoints() > 0){
                updatedOpponentMobsOnBoard.add(currentMobToCheck);
            }
        }

        gameBoard.setInactivePlayersMobsOnBoard(updatedOpponentMobsOnBoard);


        // Check for end turn button clicked
        if (mainGameScreen.getEndTurnButton().isPushTriggered()) {
            // Update Phases Accordingly
            if(isPlayer1Turn){
                player1PhaseFlag = Phase.END;
                player2PhaseFlag = Phase.INACTIVE;
            }else{
                player1PhaseFlag = Phase.INACTIVE;
                player2PhaseFlag = Phase.END;
            }
        }

    }

    private void phaseBattleAi(){

        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.END;
    }

    private void phaseEndHuman() {

        // Reset selected and targeted mobs to null
        ((Human) gameBoard.getActivePlayer()).setSelectedMobNull();
        ((Human) gameBoard.getActivePlayer()).setTargetedMobNull();

        // Reset hasBeenUsed status
        for(Mob playerMob: gameBoard.getActivePlayersMobsOnBoard())
            playerMob.setHasBeenUsed(false);

        // Update Phases accordingly
        if(isPlayer1Turn){
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.PREP;
        }else{
            player1PhaseFlag = Phase.PREP;
            player2PhaseFlag = Phase.INACTIVE;
        }

        // Update Boolean flags accordingly
        this.isPlayer1Turn = !isPlayer1Turn;
        gameBoard.setIsPlayer1Turn(isPlayer1Turn);
    }

    private void phaseEndAi() {

        player1PhaseFlag = Phase.PREP;
        player2PhaseFlag = Phase.INACTIVE;

        // Update Boolean flags accordingly
        this.isPlayer1Turn = !isPlayer1Turn;
        gameBoard.setIsPlayer1Turn(isPlayer1Turn);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Phase Check Methods
    ////////////////////////////////////////////////////////////////////////////

    private void phaseCheckHuman(Phase playerPhaseFlag) {
        switch (playerPhaseFlag) {
            case PREP: phasePrepHuman();
                break;
            case MOVE: phaseMoveHuman();
                break;
            case BATTLE: phaseBattleHuman();
                break;
            case END: phaseEndHuman();
                break;
            default: System.out.println("ERROR: Invalid phase reached for Human player.");
                break;
        }
    }

    private void phaseCheckAi() {
        switch (player2PhaseFlag) {
            case PREP: phasePrepAi();
                break;
            case MOVE: phaseMoveAi();
                break;
            case BATTLE: phaseBattleAi();
                break;
            case END: phaseEndAi();
                break;
            default: System.out.println("ERROR: Invalid phase reached for Ai player.");
                break;
        }
    }



    /**
     * Method displays a popup message informing the player who's turn it is
     * TODO: Custom message based of type of player (Human/AI)
     */
    private void addStartTurnPopup(){
        String msg;
        if(gameBoard.getPlayer2() instanceof Human) {
            if (isPlayer1Turn) msg = "Player 1 Turn";
            else msg = "Player 2 Turn";
        }else{
            if (isPlayer1Turn) msg = "Player Turn";
            else msg = "Opponent Turn";
        }

        Game mGame = gameBoard.getGameScreen().getGame();
        new PopUpObject(mGame.getScreenWidth() / 2, mGame.getScreenHeight() / 2,
                mGame.getAssetManager().getBitmap("PopupSign"), gameBoard.getGameScreen(),
                50, msg);
    }


}
