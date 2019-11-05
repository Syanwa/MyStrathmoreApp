package com.example.mystrathmoreapp;
import javax.annotation.Nonnull;

public class UnitId {
    public String unitId;

    public<T extends UnitId> T withId(@Nonnull final String id){
        this.unitId = id;
        return (T) this;
    }
}
