package com.example.nutrionall.models.Food;

import java.util.List;

public class SearchNutrientFood {
    int page;
    int limit;
    List<Food> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Food> getData() {
        return data;
    }

    public void setData(List<Food> data) {
        this.data = data;
    }
}
