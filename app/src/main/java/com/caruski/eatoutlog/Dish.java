package com.caruski.eatoutlog;

public class Dish {

    long _id;
    long restId;
    String name;
    float look;
    float taste;
    float texture;
    String comments;
    String date_added;

    //constructors
    public Dish(){
    }
    public Dish(long restId, String name, float look, float taste, float texture, String comments){
        this.restId = restId;
        this.name = name;
        this.look = look;
        this.taste = taste;
        this.texture = texture;
        this.comments = comments;
    }

    //setters
    public void setId(long _id){
        this._id = _id;
    }
    public void setRestId(long restId){
        this.restId = restId;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLook(float look){
        this.look = look;
    }
    public void setTaste(float taste){
        this.taste = taste;
    }
    public void setTexture(float texture){
        this.texture = texture;
    }
    public void setComments(String comments){
        this.comments = comments;
    }
    public void setDate_added(String date_added){
        this.date_added = date_added;
    }

    //getters
    public long getId() {
        return this._id;
    }
    public long getRestId() {
        return this.restId;
    }
    public String getName() {
        return this.name;
    }
    public float getLook() {
        return this.look;
    }
    public float getTaste() {
        return this.taste;
    }
    public float getTexture() {
        return this.texture;
    }
    public String getComments(){
        return this.comments;
    }
    public String getDate_added() {
        String[] dateF = this.date_added.split("-");
        this.date_added = dateF[1] + "/" + dateF[2] + "/" + dateF[0];
        return this.date_added;
    }
}
