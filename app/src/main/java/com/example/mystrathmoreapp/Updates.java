package com.example.mystrathmoreapp;

public class Updates {
    String updateUnit , updateDescription;

    public Updates(String updateUnit, String updateDetails) {
        this.updateUnit = updateUnit;
        this.updateDescription = updateDetails;
    }

    public Updates(){

    }

    public String getUpdateUnit() {
        return updateUnit;
    }

    public void setUpdateUnit(String updateUnit) {
        this.updateUnit = updateUnit;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }
}
