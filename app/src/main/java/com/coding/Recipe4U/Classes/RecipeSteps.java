package com.coding.Recipe4U.Classes;

public class RecipeSteps {

    private int StepNo,  stepDuration;
    private String stepDescription;

    //Constructor
    public RecipeSteps(int stepNo, int stepDuration, String stepDescription) {
        StepNo = stepNo;
        this.stepDuration = stepDuration;
        this.stepDescription = stepDescription;
    }

    //Getters and Setters
    public int getStepNo() {
        return StepNo;
    }

    public void setStepNo(int stepNo) {
        StepNo = stepNo;
    }

    public int getStepDuration() {
        return stepDuration;
    }

    public void setStepDuration(int stepDuration) {
        this.stepDuration = stepDuration;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }
}
