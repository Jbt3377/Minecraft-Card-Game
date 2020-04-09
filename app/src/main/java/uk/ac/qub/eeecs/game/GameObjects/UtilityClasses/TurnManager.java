package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;

enum Phase{
    INACTIVE, SETUP , PREP , MOVE , BATTLE , END
}

public class TurnManager {

    //Properties
    private GameBoard gameBoard;
    private Phase player1PhaseFlag;
    private Phase player2PhaseFlag;


    //Constructor
    public TurnManager(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.player1PhaseFlag = Phase.SETUP;
        this.player2PhaseFlag = Phase.SETUP;
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
            gameBoard.getHumanHand().update(input);
            //gameboard.checkIfContainerFilled
        } else if(player2PhaseFlag == Phase.MOVE && player1PhaseFlag == Phase.INACTIVE){

        } else if(player1PhaseFlag == Phase.BATTLE && player2PhaseFlag == Phase.INACTIVE){

        } else if(player2PhaseFlag == Phase.BATTLE && player1PhaseFlag == Phase.INACTIVE){

        } else if(player1PhaseFlag == Phase.END && player2PhaseFlag == Phase.INACTIVE){

        } else if(player2PhaseFlag == Phase.END && player1PhaseFlag == Phase.INACTIVE){

        }
    }

    public void phaseSetup(){
        //player1.replenishHand
        //player2.replenishHand
        //setTurnCounter to 1
        //Determine who the first player will be
        player1PhaseFlag = Phase.MOVE;
        player2PhaseFlag = Phase.INACTIVE;
        //Popup saying whose turn it is
    }

    public void phasePrep(){

    }

    public void phaseMove(){

    }

    public void phaseBattle(){

    }

    public void phaseEnd(){

    }




}
