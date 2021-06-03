package com.thedariusz.warnme;

public class MeteoAlertCategory {
    private int categoryId;
    private String name;

    public MeteoAlertCategory(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public MeteoAlertCategory() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
