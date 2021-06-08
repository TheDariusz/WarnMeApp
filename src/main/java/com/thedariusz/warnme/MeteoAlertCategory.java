package com.thedariusz.warnme;

public class MeteoAlertCategory {
    private String name;

    public MeteoAlertCategory( String name) {
        this.name = name;
    }

    public MeteoAlertCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MeteoAlertCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
