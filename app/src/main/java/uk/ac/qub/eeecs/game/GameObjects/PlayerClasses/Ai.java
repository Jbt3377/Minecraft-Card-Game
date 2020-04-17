package uk.ac.qub.eeecs.game.GameObjects.PlayerClasses;

import java.util.ArrayList;

public class Ai extends Player {

    private int selectedAiContainerIndex;
    private int selectedAiCardToMoveIndex;

    private boolean aiFinishedMoves;
    private boolean aiFinishedAttacks;
    private long animationTimer;
    private int selectedMobToAttackIndex;
    private int targetedMobIndex;

    public Ai(String selectedDeckName) {
        super(selectedDeckName);

        this.aiFinishedAttacks = false;
        this.aiFinishedMoves = false;
        this.animationTimer = -1;
        this.selectedMobToAttackIndex = 0;
        this.selectedAiContainerIndex = 0;
        this.selectedAiCardToMoveIndex = 0;
    }

    /////////
    //Methods
    /////////

    public void resetAiProperties(){
        this.aiFinishedAttacks = false;
        this.aiFinishedMoves = false;
        this.animationTimer = -1;
        this.selectedMobToAttackIndex = 0;
        this.selectedAiContainerIndex = 0;
        this.selectedAiCardToMoveIndex = 0;
    }

    ////////////////////
    // Getters & Setters
    ////////////////////

    public int getSelectedAiContainerIndex() {
        return selectedAiContainerIndex;
    }

    public void setSelectedAiContainerIndex(int selectedAiContainerIndex) {
        this.selectedAiContainerIndex = selectedAiContainerIndex;
    }

    public int getSelectedAiCardToMoveIndex() {
        return selectedAiCardToMoveIndex;
    }

    public void setSelectedAiCardToMoveIndex(int selectedAiCardToMoveIndex) {
        this.selectedAiCardToMoveIndex = selectedAiCardToMoveIndex;
    }

    public boolean isAiFinishedMoves() {
        return aiFinishedMoves;
    }

    public void setAiFinishedMoves(boolean aiFinishedMoves) {
        this.aiFinishedMoves = aiFinishedMoves;
    }

    public boolean isAiFinishedAttacks() {
        return aiFinishedAttacks;
    }

    public long getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(long animationTimer) {
        this.animationTimer = animationTimer;
    }

    public int getSelectedMobToAttackIndex() {
        return selectedMobToAttackIndex;
    }

    public void setSelectedMobToAttackIndex(int selectedMobToAttackIndex) {
        this.selectedMobToAttackIndex = selectedMobToAttackIndex;
    }

    public int getTargetedMobIndex() {
        return targetedMobIndex;
    }

    public void setTargetedMobIndex(int targetedMobIndex) {
        this.targetedMobIndex = targetedMobIndex;
    }


}
