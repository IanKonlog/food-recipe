package com.coding.Recipe4U.Classes.UserClasses;

public class Ingredient {
    private int ingredientNo;
    private String ingredientName,ingredientQty;

    public Ingredient(){}
    public Ingredient(int ingredientNo, String ingredientName, String ingredientQty) {
        this.ingredientNo = ingredientNo;
        this.ingredientName = ingredientName;
        this.ingredientQty = ingredientQty;
    }

    public int getIngredientNo() {
        return ingredientNo;
    }

    public void setIngredientNo(int ingredientNo) {
        this.ingredientNo = ingredientNo;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientQty() {
        return ingredientQty;
    }

    public void setIngredientQty(String ingredientQty) {
        this.ingredientQty = ingredientQty;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientNo=" + ingredientNo +
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientQty='" + ingredientQty + '\'' +
                '}';
    }
}
