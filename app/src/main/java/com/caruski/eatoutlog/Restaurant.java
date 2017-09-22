package com.caruski.eatoutlog;

public class Restaurant {
    long _id;
    String name;
    String city;
    String state;
    String last_visit;

    //constructors
    public Restaurant(){
    }
    public Restaurant(String name){
        this.name = name;
    }
    public Restaurant(String name, String state, String city){
        this.name = name;
        this.state = state;
        this.city = city;
    }

    //setters
    public void setId(long _id){
        this._id = _id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLastVisit(String last_visit){
        this.last_visit = last_visit;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setCity(String city){
        this.city = city;
    }

    //getters
    public long getId(){
        return this._id;
    }
    public String getName(){
        return this.name;
    }
    public String getCity(){
        return this.city;
    }
    public String getState(){
        return this.state;
    }
    public String getLast_visit(){
        String[] dateF = this.last_visit.split("-");
        this.last_visit = dateF[1] + "/" + dateF[2] + "/" + dateF[0];
        return this.last_visit;
    }
}
