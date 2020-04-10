package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameScreens.MainGameScreen;


public class TurnManager {

    enum Phase{
        INACTIVE, SETUP , PREP , MOVE , BATTLE , END
    }

    //Properties
    private GameBoard gameBoard;
    private Phase player1PhaseFlag;
    private Phase player2PhaseFlag;
    private boolean isPlayer1Turn;
    private MainGameScreen mainGameScreen;
    private Game game;


    //Constructor
    public TurnManager(GameBoard gameBoard, MainGameScreen mainGameScreen, Game game) {
        this.gameBoard = gameBoard;
        this.player1PhaseFlag = Phase.SETUP;
        this.player2PhaseFlag = Phase.SETUP;
        this.mainGameScreen = mainGameScreen;
        this.game = game;
    }


    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport){
        gameBoard.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }

    public void update(List< TouchEvent > input){
        if(player1PhaseFlag == Phase.SETUP && player2PhaseFlag == Phase.SETUP){
            phaseSetup();
        } else if(player1PhaseFlag == Phase.PREP && player2PhaseFlag == Phase.INACTIVE){

        } else if(player2PhaseFlag == Phase.PREP && player1PhaseFlag == Phase.INACTIVE){

        } else if(player1PhaseFlag == Phase.MOVE && player2PhaseFlag == Phase.INACTIVE){

            for(int i = 0; i < gameBoard.getHumanHand().getPlayerHand().size(); i++){
                Interaction.moveCardToContainer(input,gameBoard.getHumanHand().getPlayerHand().get(i), game, gameBoard);
            }
            if(mainGameScreen.getEndTurnButton().isPushTriggered()){
                isPlayer1Turn = false;
                addStartTurnPopup();
                mainGameScreen.setTurnNumber(mainGameScreen.getTurnNumber() + 1);
            }
        } else if(player2PhaseFlag == Phase.MOVE && player1PhaseFlag == Phase.INACTIVE){

        } else if(player1PhaseFlag == Phase.BATTLE && player2PhaseFlag == Phase.INACTIVE){

        } else if(player2PhaseFlag == Phase.BATTLE && player1PhaseFlag == Phase.INACTIVE){

        } else if(player1PhaseFlag == Phase.END && player2PhaseFlag == Phase.INACTIVE){

        } else if(player2PhaseFlag == Phase.END && player1PhaseFlag == Phase.INACTIVE){

        }
    }


    private void phaseSetup(){

        // Replenish both player hands
        gameBoard.getHumanHand().replenishHand();
        gameBoard.getAiHand().replenishHand();

        //setTurnCounter to 1
        //Determine who the first player will be
        player1PhaseFlag = Phase.MOVE;
        player2PhaseFlag = Phase.INACTIVE;
        isPlayer1Turn = true;

        addStartTurnPopup();
    }

    private void phasePrep(){

    }

    private void phaseMove(){

    }

    private void phaseBattle(){

    }

    private void phaseEnd(){

    }

    /**
     * Method displays a popup message informing the player who's turn it is
     * TODO: Custom message based of type of player (Human/AI)
     */
    private void addStartTurnPopup(){
        String msg;
        if(isPlayer1Turn) msg = "Player 1 Turn";
        else msg = "Player 2 Turn";

        Game mGame = gameBoard.getGameScreen().getGame();
        new PopUpObject(mGame.getScreenWidth() / 2,mGame.getScreenHeight() / 2,
                mGame.getAssetManager().getBitmap("PopupSign"), gameBoard.getGameScreen(),
                50, msg);
    }




}
