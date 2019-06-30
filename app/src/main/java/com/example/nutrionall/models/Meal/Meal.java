package com.example.nutrionall.models.Meal;

import java.io.Serializable;
import java.util.List;

public class Meal implements Serializable {
    String _id;
    String urlImg;
    String idUser;
    String name;
    String description;
    Boolean visibility;
    int classification;
    List<Ingredient> ingredients;
    float avgEvaluation;

    public float getAvgEvaluation() {
        return avgEvaluation;
    }

    public void setAvgEvaluation(float avgEvaluation) {
        this.avgEvaluation = avgEvaluation;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
