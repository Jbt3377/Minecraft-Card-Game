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
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Player;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;


public class TurnManager {

    enum Phase {
        INACTIVE, SETUP, PREP, MOVE, BATTLE, END
    }

    //Properties
    private GameBoard gameBoard;
    private Phase player1PhaseFlag;
    private Phase player2PhaseFlag;
    private boolean isPlayer1Turn;
    private MainGameScreen mainGameScreen;
    private Game game;
    private List<TouchEvent> input;

    //Constructor
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
        if (player1PhaseFlag == Phase.SETUP && player2PhaseFlag == Phase.SETUP) {
            phaseSetup();
        }
        if (isPlayer1Turn) {
            phaseCheckHuman(player1PhaseFlag);
        }
        else {
            if (gameBoard.getPlayer2() instanceof Human) {
                phaseCheckHuman(player2PhaseFlag);
            } else {
                phaseCheckAi();
            }

        }

    }


    private void phaseSetup() {

        gameBoard.getPlayer1Hand().replenishHand();
        gameBoard.getPlayer2Hand().replenishHand();
        player1PhaseFlag = Phase.MOVE;
        player2PhaseFlag = Phase.INACTIVE;
        isPlayer1Turn = true;
        gameBoard.setIsPlayer1Turn(true);
        addStartTurnPopup();
    }

    private void phaseHumanPrep() {

    }

    private void phaseAiPrep(){

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
            if (((Human) gameBoard.getPlayer1()).getTargetedMob() != null)
                player1PhaseFlag = Phase.BATTLE;
            player2PhaseFlag = Phase.INACTIVE;
        }

        // Check for end turn button clicked
        if (mainGameScreen.getEndTurnButton().isPushTriggered()) {
            isPlayer1Turn = !isPlayer1Turn;

            //Put this line of code in the prep phase
            addStartTurnPopup();

            //Put this line of code in the prep phase
            mainGameScreen.setTurnNumber(mainGameScreen.getTurnNumber() + 1);

            //Test code to work on ai movement phase
            player1PhaseFlag = Phase.END;
            player2PhaseFlag = Phase.INACTIVE;

        }

    }


    private void phaseHumanBattle() {

    }

    private void phaseAiBattle(){

    }

    private void phaseHumanEnd() {

        // TODO Reminder: Set Targeted Mob to null before turn ends
    }

    private void phaseAiEnd() {

    }


    private void phaseAiMove() {
        Interaction.moveAiCardToContainer(gameBoard);

        player1PhaseFlag = Phase.PREP;
        player2PhaseFlag = Phase.INACTIVE;

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

    public void phaseCheckHuman(Phase playerPhaseFlag) {
        switch (playerPhaseFlag) {
            case PREP: phaseHumanPrep();
                break;
            case MOVE: phaseMoveHuman();
                break;
            case BATTLE: phaseHumanBattle();
                break;
            case END:   phaseHumanEnd();
                break;
            default: System.out.println("ERROR: Invalid phase reached for Human player.");
                break;
        }
    }

    public void phaseCheckAi() {
        switch (player2PhaseFlag) {
            case PREP: phaseAiPrep();
                break;
            case MOVE: phaseAiMove();
                break;
            case BATTLE: phaseAiBattle();
                break;
            case END:   phaseAiEnd();
                break;
            default: System.out.println("ERROR: Invalid phase reached for Ai player.");
                break;
        }
    }


}
