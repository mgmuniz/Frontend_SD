package com.example.nutrionall.models.Meal;

public class Ingredient {
    String idFood;
    String nameFood;
    String qtdPortion;
    String portion;

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getNameFood() {return nameFood;}

    public void setNameFood(String nameFood) {this.nameFood = nameFood;}

    public String getQtdPortion() {
        return qtdPortion;
    }

    public void setQtdPortion(String qtdPortion) {
        this.qtdPortion = qtdPortion;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }
}
