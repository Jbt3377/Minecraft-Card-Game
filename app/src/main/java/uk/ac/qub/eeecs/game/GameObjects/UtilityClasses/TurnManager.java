package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
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

        gameBoard.getActivePlayerHand().replenishHand();
        this.isPlayer1Turn = !isPlayer1Turn;
        gameBoard.setIsPlayer1Turn(isPlayer1Turn);

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

    }

    private void phaseMoveHuman() {

        // Check for mob selection
        Interaction.processTouchEvents(input, game, gameBoard);

        // Check for card drops in container
        for (int i = 0; i < gameBoard.getPlayer1Hand().getPlayerHand().size(); i++) {
            Card card;
            if (isPlayer1Turn) card = gameBoard.getPlayer1Hand().getPlayerHand().get(i);
            else card = gameBoard.getPlayer2Hand().getPlayerHand().get(i);
            Interaction.moveCardToContainer(input, card, game, gameBoard);
        }

        // Check if targeted mob selected, if so, switch to battle phase
        if (gameBoard.getPlayer1() instanceof Human) {
            if (((Human) gameBoard.getPlayer1()).getTargetedMob() != null) {
                player1PhaseFlag = Phase.BATTLE;
                player2PhaseFlag = Phase.INACTIVE;
            }
        }

        // Check for end turn button clicked
        if (mainGameScreen.getEndTurnButton().isPushTriggered()) {
            isPlayer1Turn = !isPlayer1Turn;

            //Put this line of code in the prep phase
            addStartTurnPopup();

            //Put this line of code in the prep phase
            mainGameScreen.setTurnNumber(mainGameScreen.getTurnNumber() + 1);

            // Update Phases Accordingly
//            if(isPlayer1Turn){
//                player1PhaseFlag = Phase.END;
//                player2PhaseFlag = Phase.INACTIVE;
//            }else{
//                player1PhaseFlag = Phase.INACTIVE;
//                player2PhaseFlag = Phase.END;
//            }

            // Test code to work on ai movement phase
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.MOVE;

        }

    }

    private void phaseMoveAi(){
        Interaction.moveAiCardToContainer(gameBoard);

//        player1PhaseFlag = Phase.PREP;
//        player2PhaseFlag = Phase.INACTIVE;
        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.BATTLE;
    }


    private void phaseBattleHuman() {

    }

    private void phaseBattleAi(){

        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.END;
    }

    private void phaseEndHuman() {

        // TODO Reminder: Set Targeted Mob to null before turn ends
    }

    private void phaseEndAi() {

        player1PhaseFlag = Phase.PREP;
        player2PhaseFlag = Phase.INACTIVE;
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
    private void addStartTurnPopup() {
        String msg;
        if (isPlayer1Turn) msg = "Player 1 Turn";
        else msg = "Player 2 Turn";

        Game mGame = gameBoard.getGameScreen().getGame();
        new PopUpObject(mGame.getScreenWidth() / 2, mGame.getScreenHeight() / 2,
                mGame.getAssetManager().getBitmap("PopupSign"), gameBoard.getGameScreen(),
                50, msg);
    }


}
