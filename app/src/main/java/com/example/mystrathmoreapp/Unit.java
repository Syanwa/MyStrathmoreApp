package com.example.mystrathmoreapp;

public class Unit extends UnitId {
    private String lecturer_name;
    private String unit_name;
    private String class_time;

    public Unit(){
        //empty constructor needed
    }
    public Unit(String lecturer_name, String unit_name, String class_time){
        this.class_time = class_time;
        this.unit_name = unit_name;
        this.lecturer_name = lecturer_name;
    }

    public String getLecturer_name() {
        return lecturer_name;
    }

    public void setLecturer_name(String lecturer_name) {
        this.lecturer_name = lecturer_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }
}