package com.coding.Recipe4U.Classes;

public class RecipeSteps {

    private int StepNo;
    private String stepDescription;

    //Constructor
    public RecipeSteps(int stepNo, String stepDescription) {
        StepNo = stepNo;
        this.stepDescription = stepDescription;
    }

    //Getters and Setters
    public int getStepNo() {
        return StepNo;
    }

    public void setStepNo(int stepNo) {
        StepNo = stepNo;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    @Override
    public String toString() {
        return "RecipeSteps{" +
                "StepNo=" + StepNo +
                ", stepDescription='" + stepDescription + '\'' +
                '}';
    }
}
