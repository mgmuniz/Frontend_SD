package com.example.nutrionall.models.Food;

import java.io.Serializable;

public class Definition implements Serializable {
    public String value;
    public String type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

