package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.MobContainer;
import uk.ac.qub.eeecs.game.GameObjects.GameBoard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Ai;
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

        // Reset Player Health and Mana levels
        final int PLAYER_STARTING_HEALTH = 40;
        final int PLAYER_STARTING_MANA = 10;

        gameBoard.getPlayer1().setmPlayerHealth(PLAYER_STARTING_HEALTH);
        gameBoard.getPlayer1().setmPlayerMana(PLAYER_STARTING_MANA);
        gameBoard.getPlayer2().setmPlayerHealth(PLAYER_STARTING_HEALTH);
        gameBoard.getPlayer2().setmPlayerMana(PLAYER_STARTING_MANA);

        boolean isPlayer1First = gameBoard.getGameScreen().getGame().isPlayer1First();
        if(isPlayer1First) {
            player1PhaseFlag = Phase.MOVE;
            player2PhaseFlag = Phase.INACTIVE;
            isPlayer1Turn = true;
            gameBoard.setIsPlayer1Turn(true);
        }else{
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.MOVE;
            isPlayer1Turn = false;
            gameBoard.setIsPlayer1Turn(false);
        }

        addStartTurnPopup();
    }

    private void phasePrepHuman() {

        // Replenish Player's Hand
        gameBoard.getActivePlayerHand().replenishHand();

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

            if(card instanceof CharacterCard){
                Interaction.moveCardToContainer(input, card, game, gameBoard);
            }

            else if(card instanceof EquipCard){
                Interaction.moveEquipCardToContainer(input, card, game, gameBoard);
                EquipCard equipCard = (EquipCard) card;
                if(equipCard.isAnimationInProgress()){
                    System.out.println("Reached this animation line of code");
                    equipCard.runCardAnimation();
                }
                if(equipCard.isAnimationFinished()) {
                    int index = gameBoard.getActivePlayerHand().getPlayerHand().indexOf(equipCard);
                    gameBoard.getActivePlayerHand().getPlayerHand().remove(index);
                    gameBoard.getActivePlayer().setmPlayerMana(gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost());
                }
            }
            else if(card instanceof UtilityCard){
                Interaction.moveUtilityCardToContainer(input,card,game,gameBoard);
                UtilityCard utilityCard = (UtilityCard) card;
                if(utilityCard.isAnimationInProgress()){
                    System.out.println("Reached this animation line of code");
                    utilityCard.runCardAnimation();
                }

                if(utilityCard.isAnimationFinished()) {
                    int index = gameBoard.getActivePlayerHand().getPlayerHand().indexOf(utilityCard);
                    utilityCard.runUtilityEffect(gameBoard);
                    gameBoard.getActivePlayerHand().getPlayerHand().remove(index);
                    gameBoard.getActivePlayer().setmPlayerMana(gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost());
                }


            }



        }

        Interaction.processMobSelection(input, game, gameBoard);


        // Check for mob selection


        // Check if targeted mob selected, if so, switch to battle phase
        if (gameBoard.getActivePlayer() instanceof Human) {
            if ((gameBoard.getActivePlayer()).getTargetedMob() != null) {
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
            System.out.println("AI Move Phase");

            Ai aiPlayer = (Ai)gameBoard.getPlayer2();

            if(aiPlayer.isAiFinishedMoves()){
                System.out.println("Ending move phase for AI");
                player1PhaseFlag = Phase.INACTIVE;
                player2PhaseFlag = Phase.BATTLE;
            }


            ArrayList<MobContainer> aiMobContainers = new ArrayList<MobContainer>();
            for (MobContainer mb : gameBoard.getFieldContainers()) {
                if (mb.getContType() == MobContainer.ContainerType.TOP_PLAYER) {
                    aiMobContainers.add(mb);
                }
            }

            int aiContainerIndex = aiPlayer.getSelectedAiContainerIndex();
            if (aiContainerIndex < aiMobContainers.size()) {
                System.out.println("aiContainerIndex is less than container.size");
                //Establish there is a free container
                if (aiMobContainers.get(aiContainerIndex).isEmpty()) {
                    System.out.println("This container is free. Container index: " + aiPlayer.getSelectedAiContainerIndex());
                    MobContainer mc = aiMobContainers.get(aiContainerIndex);

                    //Compile a list of the integers of index of CharacterCards
                    ArrayList<Integer> characterCardsIndexList = new ArrayList<>();
                    int chosenIndex;
                    Random rand = new Random();
                    //Establish if a card is currently selected
                    if (aiPlayer.getSelectedAiCardToMoveIndex() == -1) {
                        characterCardsIndexList.clear();
                        for (int i = 0; i < gameBoard.getActivePlayerHand().getPlayerHand().size(); i++) {
                            if (gameBoard.getActivePlayerHand().getPlayerHand().get(i) instanceof CharacterCard) {
                                characterCardsIndexList.add(i);
                            }
                        }
                        chosenIndex = rand.nextInt(characterCardsIndexList.size());
                        aiPlayer.setSelectedAiCardToMoveIndex(chosenIndex);
                    } else {
                        chosenIndex = aiPlayer.getSelectedAiCardToMoveIndex();
                    }
                    aiPlayer.setSelectedAiCardToMoveIndex(chosenIndex);
                    Card card = gameBoard.getActivePlayerHand().getPlayerHand().get(chosenIndex);
                    System.out.println("Chosen index: " + chosenIndex);
                    if (card instanceof CharacterCard) {
                        if (aiPlayer.getmPlayerMana() - card.getManaCost() >= 0) {
                            //Begin moving the card towards the target if not in  correct place
                            if ((card.readyToTurnToMob(mc.getX_location(), mc.getY_location()))) {
                                Mob mob = new Mob(mc.getX_location(), mc.getY_location(), gameBoard.getGameScreen(), (CharacterCard) card);
                                System.out.println("PLAYING THIS CARD");
                                aiMobContainers.get(aiContainerIndex).placeCard(mob);
                                gameBoard.getActivePlayersMobsOnBoard().add(mob);
                                aiPlayer.setmPlayerMana(aiPlayer.getmPlayerMana() - card.getManaCost());
                                gameBoard.getActivePlayerHand().getPlayerHand().remove(chosenIndex);
                                aiPlayer.setSelectedAiCardToMoveIndex(-1);
                                aiPlayer.setSelectedAiContainerIndex(aiPlayer.getSelectedAiContainerIndex() + 1);
                            } else {
                                if (!card.readyToTurnToMob(mc.getX_location(), mc.getY_location())) {
                                    card.cardMoveXAnimation(mc.getX_location());
                                }
                                if (!card.readyToTurnToMob(mc.getX_location(), mc.getY_location())) {
                                    card.cardMoveYAnimation(mc.getY_location());
                                }
                            }

                        }else{
                            aiPlayer.setSelectedAiContainerIndex(aiPlayer.getSelectedAiContainerIndex() + 1);
                        }
                    }
                }else{
                    aiPlayer.setSelectedAiContainerIndex(aiPlayer.getSelectedAiContainerIndex() + 1);
                }
            }else{

                aiPlayer.setAiFinishedMoves(true);
            }
        }

    private void phaseMoveAiCharacterCards() {
        System.out.println("MOVE CHARACTER CARD PHASE");
        //Creating a temporary ArrayList to store AI field containers
        ArrayList<MobContainer> freeAiMobContainers = new ArrayList<>();
        for (MobContainer mb : gameBoard.getFieldContainers()) {
            if (mb.getContType() == MobContainer.ContainerType.TOP_PLAYER && mb.isEmpty()) {
                freeAiMobContainers.add(mb);
            }
        }

        //Creating a temporary ArrayList to store the AI's CharacterCards
        ArrayList<CharacterCard> tempCharacterCardList = new ArrayList<CharacterCard>();
        for(Card card: gameBoard.getActivePlayerHand().getPlayerHand()){
            if(card instanceof CharacterCard){
                System.out.println("Found CharacterCard, adding to temp list.");
                tempCharacterCardList.add((CharacterCard) card);
            }
        }

        for(MobContainer mobContainer : freeAiMobContainers){
                System.out.println("This is a free mob container. Searching for valid CharacterCard to place in container.");
                for(int i = 0; i < tempCharacterCardList.size(); i++){
                    CharacterCard characterCard = tempCharacterCardList.get(i);
                    if(enoughManaToPerformAction(gameBoard,characterCard) && mobContainer.isEmpty()){

                        System.out.println("There is enough mana to place this card in the container. Moving card to mob container");
                        Mob mob = new Mob(mobContainer.getX_location(),mobContainer.getY_location(),gameBoard.getGameScreen(),characterCard);
                        mobContainer.placeCard(mob);
                        gameBoard.getActivePlayersMobsOnBoard().add(mob);

                        System.out.println("Adjusting player mana after card placement");
                        adjustPlayerManaAfterCardPlacement(gameBoard,characterCard);


                        System.out.println("Card transformed to mob. Now removing card from hand and temp list.");
                        tempCharacterCardList.remove(i);
                        gameBoard.getActivePlayerHand().getPlayerHand().remove(characterCard);

                    }else{
                        System.out.println("There is not enough mana to place this card. Trying another card");
                    }
                }

            }
        System.out.println("Tried all containers. No free containers remaining. Ending PHASE MOVE AI, going to PHASE BATTLE AI");
        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.BATTLE;
        }

    private void phaseMoveAiUtilityCards(){
        System.out.println("MOVE UTILITY CARD PHASE");
        //Creating a temporary ArrayList to store the AI's UtilityCards
        ArrayList<UtilityCard> utilityCardArrayList = new ArrayList<UtilityCard>();
        for(Card card: gameBoard.getActivePlayerHand().getPlayerHand()){
            if(card instanceof UtilityCard){
                System.out.println("Found UtilityCard, adding to temp list.");
                utilityCardArrayList.add((UtilityCard) card);
            }
        }

        MobContainer utilityCardContainer = gameBoard.getUtilityCardContainer();

            for(int i = 0; i < utilityCardArrayList.size(); i++){
                UtilityCard utilityCard = utilityCardArrayList.get(i);
                if(enoughManaToPerformAction(gameBoard,utilityCard) && utilityCardContainer.isEmpty()){

                    System.out.println("There is enough mana to play this utility card");
                    utilityCard.runUtilityEffect(gameBoard);


                    System.out.println("Adjusting player mana after card placement");
                    adjustPlayerManaAfterCardPlacement(gameBoard,utilityCard);

                    System.out.println("Utility card used. Now removing card from hand and temp list.");


                    utilityCardArrayList.remove(i);
                    gameBoard.getActivePlayerHand().getPlayerHand().remove(utilityCard);

                }else{
                    System.out.println("There is not enough mana to place this card. Trying another card");
                }


        }
        System.out.println("Ending PHASE MOVE AI, going to PHASE BATTLE AI");
        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.BATTLE;
    }

    private void phaseBattleHuman() {

        // Check for mob selection
        Interaction.processMobSelection(input, game, gameBoard);

        Mob currentlySelectedMob = (gameBoard.getActivePlayer()).getSelectedMob();
        Mob currentlyTargetedMob = (gameBoard.getActivePlayer()).getTargetedMob();

        // Check if selected mob and targeted mob not null
        if((currentlySelectedMob != null) && (currentlyTargetedMob != null)){

            // Run attack sequence (!!!)
            currentlySelectedMob.attackTarget(currentlyTargetedMob);
            currentlySelectedMob.setSelectedToAttack(false);
            currentlySelectedMob.updateMobBitmap();
            currentlyTargetedMob.updateMobBitmap();

            // Reset selected and targeted mobs to null
            (gameBoard.getActivePlayer()).setSelectedMobNull();
            (gameBoard.getActivePlayer()).setTargetedMobNull();

        }

        // Check for Mob Death
        for(MobContainer container: gameBoard.getFieldContainers()){
            if(!container.isEmpty()){

                // If mob died, remove from container
                Mob containedMob = container.getContents();
                if(containedMob.getHealthPoints() <= 0){
                    //Remove the mob from the inactive player's activeMobsOnBoardArrayList.
                    gameBoard.getInactivePlayersMobsOnBoard().remove(containedMob);

                    // Surplus Damage inflicted on player
                    if(containedMob.getHealthPoints()<0){
                        int surplusDamage = Math.abs(containedMob.getHealthPoints());
                        gameBoard.decreaseInactivePlayerHP(surplusDamage);
                    }
                    container.emptyContainer();
                }
            }
        }

        // Check for Player Death
        if(gameBoard.getInactivePlayer().getmPlayerHealth() <=0) {
            phaseGameEnded();
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

    private void phaseBattleAi() {
        System.out.println("Ai battle phase reached");

        Ai aiPlayer = (Ai)gameBoard.getPlayer2();

        if(gameBoard.getActivePlayersMobsOnBoard().size() == 0 || gameBoard.getPlayer1MobsOnBoard().size() == 0){
            (aiPlayer).setSelectedMobNull();
            (aiPlayer).setTargetedMobNull();
            for(Mob mob : gameBoard.getActivePlayersMobsOnBoard()){
                mob.setHasBeenUsed(false);
                mob.setSelectedToAttack(false);
                mob.updateMobBitmap();
            }
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.END;
            System.out.println("Ai battle phase finished because no valid mobs to attack");
        }

        if(aiPlayer.isAiFinishedAttacks() || aiPlayer.getSelectedMobToAttackIndex() >= gameBoard.getActivePlayersMobsOnBoard().size()){
            for(Mob mob : gameBoard.getActivePlayersMobsOnBoard()){
                mob.setHasBeenUsed(false);
                mob.setSelectedToAttack(false);
                mob.updateMobBitmap();
            }
            (aiPlayer).setSelectedMobNull();
            (aiPlayer).setTargetedMobNull();
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.END;
            System.out.println("Ai battle phase ended by finishing all attacks");
        }else{

            //Put in a slight delay here so the player can see what is happening
            if(aiPlayer.getAnimationTimer() == -1){
                aiPlayer.setAnimationTimer(System.currentTimeMillis());

                if(player2PhaseFlag != Phase.END) {
                    System.out.println("Ai still has valid moves to perform");
                    //Get the AIs first mob and select it
                    aiPlayer.setSelectedMob(gameBoard.getActivePlayersMobsOnBoard().get(aiPlayer.getSelectedMobToAttackIndex()));
                    aiPlayer.getSelectedMob().setSelectedToAttack(true);
                    //AiMob has been selected, so change border to green
                    aiPlayer.getSelectedMob().updateMobBitmap();
                }
                //Get the opponents first mob and select it.
                if(player2PhaseFlag != Phase.END) {
                    Random rand = new Random();
                    aiPlayer.setTargetedMobIndex(rand.nextInt(gameBoard.getPlayer1MobsOnBoard().size()));
                    aiPlayer.setTargetedMob(gameBoard.getPlayer1MobsOnBoard().get(aiPlayer.getTargetedMobIndex()));
                    aiPlayer.getTargetedMob().setSelectedToAttack(true);
                    //OpponentMob selected, so change border to green
                    aiPlayer.getTargetedMob().updateMobBitmap();
                }

            }

            if(System.currentTimeMillis() > (aiPlayer.getAnimationTimer() + 1000)) {

                //Attack the opponents mob with the AI mob
                try {
                    aiPlayer.getSelectedMob().attackTarget(aiPlayer.getTargetedMob());
                } catch (Exception e){
                    System.out.println("NULL POINTER THING");
                }
                //Check for mob death
                for(MobContainer container: gameBoard.getFieldContainers()){
                    if(!container.isEmpty()){

                        // If mob died, remove from container
                        Mob containedMob = container.getContents();
                        if(containedMob.getHealthPoints() <= 0){
                            int surplusDamage = Math.abs(containedMob.getHealthPoints());
                            gameBoard.decreaseInactivePlayerHP(surplusDamage);
                            container.emptyContainer();
                            gameBoard.getPlayer1MobsOnBoard().remove(aiPlayer.getTargetedMobIndex());
                        }
                    }
                }

                if(gameBoard.getInactivePlayer().getmPlayerHealth() <=0) {
                    phaseGameAiEnded();
                }


                //Deselect both cards and set aiFinishedAttacks to true
                try {
                    aiPlayer.getSelectedMob().setSelectedToAttack(false);
                    aiPlayer.getSelectedMob().setHasBeenUsed(true);
                    aiPlayer.getSelectedMob().updateMobBitmap();
                    aiPlayer.getTargetedMob().setSelectedToAttack(false);
                    aiPlayer.getTargetedMob().updateMobBitmap();
                    aiPlayer.setAnimationTimer(-1);
                    aiPlayer.setSelectedMobToAttackIndex(aiPlayer.getSelectedMobToAttackIndex() + 1);
                } catch(Exception e){
                    System.out.println("More crashing");
                }
            }
        }


    }

    private void phaseEndHuman() {

        // Clear mob selections (remove green outline)
        try {
            gameBoard.getActivePlayer().getSelectedMob().setSelectedToAttack(false);
            gameBoard.getActivePlayer().getSelectedMob().setHasBeenUsed(false);
            gameBoard.getActivePlayer().getSelectedMob().updateMobBitmap();
        } catch(NullPointerException np){
            System.out.println("Ohh NO!");
        }

        // Reset selected and targeted mobs to null
        (gameBoard.getActivePlayer()).setSelectedMobNull();
        (gameBoard.getActivePlayer()).setTargetedMobNull();


        // Reset hasBeenUsed status for all player's mobs
        for (Mob playerMob : gameBoard.getActivePlayersMobsOnBoard()) {
            playerMob.setHasBeenUsed(false);
            playerMob.updateMobBitmap();
        }


        // Update Phases accordingly
        if(isPlayer1Turn){
            player1PhaseFlag = Phase.INACTIVE;
            player2PhaseFlag = Phase.PREP;
        }else{
            player1PhaseFlag = Phase.PREP;
            player2PhaseFlag = Phase.INACTIVE;
        }

        // Increase Mana at the end of each turn
        gameBoard.getActivePlayer().setmPlayerMana(gameBoard.getActivePlayer().getmPlayerMana() + 4);

        // Update Boolean flags accordingly
        this.isPlayer1Turn = !isPlayer1Turn;
        gameBoard.setIsPlayer1Turn(isPlayer1Turn);
    }

    private void phaseEndAi() {

        Ai aiPlayer = (Ai)gameBoard.getActivePlayer();

        player1PhaseFlag = Phase.PREP;
        player2PhaseFlag = Phase.INACTIVE;

        aiPlayer.setmPlayerMana(aiPlayer.getmPlayerMana() + 4);

        aiPlayer.resetAiProperties();

        // Update Boolean flags accordingly
        this.isPlayer1Turn = !isPlayer1Turn;
        gameBoard.setIsPlayer1Turn(isPlayer1Turn);
    }

    private void phaseGameAiEnded(){
        Game mGame = gameBoard.getGameScreen().getGame();
        player1PhaseFlag = Phase.INACTIVE;
        player2PhaseFlag = Phase.INACTIVE;


        String msg = "Ai Wins!";

        new PopUpObject(mGame.getScreenWidth() / 2, mGame.getScreenHeight() / 2,
                mGame.getAssetManager().getBitmap("PopupSign"), gameBoard.getGameScreen(),
                50, msg);

    }

    private void phaseGameEnded(){

        Game mGame = gameBoard.getGameScreen().getGame();

        // Display winner notification
        String msg;
        if(isPlayer1Turn)
            if(gameBoard.getPlayer2() instanceof Human)
                msg = "Player 1 Wins!";
            else
                msg = "You Win!";
        else
            if(gameBoard.getPlayer2() instanceof Human)
                msg = "Player 2 Wins!";
            else
                msg = "Opponent Wins!";

        new PopUpObject(mGame.getScreenWidth() / 2, mGame.getScreenHeight() / 2,
                mGame.getAssetManager().getBitmap("PopupSign"), gameBoard.getGameScreen(),
                50, msg);
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
            case MOVE: phaseMoveAiCharacterCards();
                       phaseMoveAiUtilityCards();
                break;
            case BATTLE: phaseBattleAi();
                break;
            case END: phaseEndAi();
                break;
            default: System.out.println("ERROR: Invalid phase reached for Ai player.");
                break;
        }
    }

    public boolean enoughManaToPerformAction(GameBoard gb, Card card){
        return(gb.getActivePlayer().getmPlayerMana() - card.getManaCost() >= 0);
    }

    public void adjustPlayerManaAfterCardPlacement(GameBoard gb, Card card){
        gb.getActivePlayer().setmPlayerMana(gameBoard.getActivePlayer().getmPlayerMana() - card.getManaCost());
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
