package com.caruski.eatoutlog.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Image {

    long _id;
    long dishId;
    byte[] data;
    String filename;

    public Image(){
    }
    public Image(long dishId, byte[] data, String filename){
        this.dishId = dishId;
        this.data = data;
        this.filename = filename;
    }

    //setters
    public void setId(long _id){
        this._id = _id;
    }
    public void setDishId(long dishId){
        this.dishId = dishId;
    }
    public void setData(byte[] data){
        this.data = data;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }

    //getters
    public long getId(){
        return this._id;
    }
    public long getDishId(){
        return this.dishId;
    }
    public byte[] getData(){
        return this.data;
    }
    public String getFilename(){
        return this.filename;
    }
    //convert Bitmap to byte[]
    public byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream byteData = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteData);
        return byteData.toByteArray();
    }
    //convert from byte[] to Bitmap
    public Bitmap getImage(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}



//    String fnm = "cat"; //  this is image file name
//    String PACKAGE_NAME = getApplicationContext().getPackageName();
//    int imgId = getResources().getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
//    System.out.println("IMG ID :: "+imgId);
//    System.out.println("PACKAGE_NAME :: "+PACKAGE_NAME);
//     //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgId);
//    your_image_view.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));

//  http://stackoverflow.com/questions/11790104/how-to-storebitmap-image-and-retrieve-image-from-sqlite-database-in-android