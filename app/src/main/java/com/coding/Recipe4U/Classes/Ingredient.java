package com.coding.Recipe4U.Classes;

public class Ingredient {
    private int ingredientNo, ingredientQty;
    private String ingredientName, ingredientPicUrl;

    public Ingredient(int ingredientNo, int ingredientQty, String ingredientName, String ingredientPicUrl) {
        this.ingredientNo = ingredientNo;
        this.ingredientQty = ingredientQty;
        this.ingredientName = ingredientName;
        this.ingredientPicUrl = ingredientPicUrl;
    }

    public int getIngredientNo() {
        return ingredientNo;
    }

    public void setIngredientNo(int ingredientNo) {
        this.ingredientNo = ingredientNo;
    }

    public int getIngredientQty() {
        return ingredientQty;
    }

    public void setIngredientQty(int ingredientQty) {
        this.ingredientQty = ingredientQty;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientPicUrl() {
        return ingredientPicUrl;
    }

    public void setIngredientPicUrl(String ingredientPicUrl) {
        this.ingredientPicUrl = ingredientPicUrl;
    }

}
