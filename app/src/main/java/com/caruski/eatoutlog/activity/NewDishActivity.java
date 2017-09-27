package com.caruski.eatoutlog.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.caruski.eatoutlog.R;
import com.caruski.eatoutlog.domain.Dish;
import com.caruski.eatoutlog.repository.DishRepository;
import com.caruski.eatoutlog.repository.DishRepositoryImpl;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class NewDishActivity extends AppCompatActivity  implements View.OnClickListener{

    long restId, dishId;
    // TODO: inject this.
    private DishRepository dishRepository;
    private final int CAMERA_REQUEST_1 = 1200;
    private final int CAMERA_REQUEST_2 = 1201;
    private final int CAMERA_REQUEST_3 = 1202;
    private final int SELECT_PHOTO = 1;
    private ListView lv;
    private ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final Context context = getApplicationContext();
        dishRepository = new DishRepositoryImpl(context);

        final EditText dishNameBox = (EditText) findViewById(R.id.dishName);
        final RatingBar rateBarLook = (RatingBar) findViewById(R.id.ratingBarLook);
        final RatingBar rateBarTaste = (RatingBar) findViewById(R.id.ratingBarTaste);
        final RatingBar rateBarTexture = (RatingBar) findViewById(R.id.ratingBarTexture);
        final EditText commentBox = (EditText) findViewById(R.id.dishComments);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("rest_id")) {
                restId = extras.getLong("rest_id");
            } else if (extras.containsKey("dish_id")) {
                dishId = extras.getLong("dish_id");
                Dish dish = dishRepository.getDish(dishId);
                setTitle(dish.getName());
                dishNameBox.setText(dish.getName());
                rateBarLook.setRating(dish.getLook());
                rateBarTaste.setRating(dish.getTaste());
                rateBarTexture.setRating(dish.getTexture());
                commentBox.setText(dish.getComments());
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        }
        setListeners();

        lv = (ListView) findViewById(R.id.dishImageList);
        imageView = (ImageView) findViewById(R.id.imageView);
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.newDishSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dishName = dishNameBox.getText().toString();
                float lookRating = rateBarLook.getRating();
                float tasteRating = rateBarTaste.getRating();
                float textureRating = rateBarTexture.getRating();
                String comments = commentBox.getText().toString();

                if (dishName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a name for dish.",
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                } else if (dishId > 0) {
                    Dish dish = dishRepository.getDish(dishId);
                    dish.setName(dishName);
                    dish.setLook(lookRating);
                    dish.setTaste(tasteRating);
                    dish.setTexture(textureRating);
                    dish.setComments(comments);
                    dishRepository.updateDish(dish);
                    startActivity(new Intent(NewDishActivity.this, MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Dish updated.", Toast.LENGTH_SHORT).show();
                } else {
                    Dish dish = new Dish(restId, dishName, lookRating, tasteRating, textureRating, comments);
                    dishRepository.createDish(dish);
                    Intent intent = new Intent(NewDishActivity.this, ViewDishesActivity.class);
                    intent.putExtra("rest_id", dish.getRestId());
                    finish();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Dish saved.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        switch(v.getId()){
//            case R.id.changeImage1:
//                startActivityForResult(cameraIntent, CAMERA_REQUEST_1);
//                break;
//            case R.id.changeImage2:
//                startActivityForResult(cameraIntent, CAMERA_REQUEST_2);
//                break;
//            case R.id.changeImage3:
//                startActivityForResult(cameraIntent, CAMERA_REQUEST_3);
//                break;
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_dish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_image: {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_dish_image_context, menu);
    }

    //Most likely store images from these methods
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_1 && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            image1.setImageBitmap(photo);
//        }
//        else if(requestCode == CAMERA_REQUEST_2 && resultCode == RESULT_OK){
//            Bitmap photo = (Bitmap)data.getExtras().get("data");
//            image2.setImageBitmap(photo);
//        }
//        else if(requestCode == CAMERA_REQUEST_3 && resultCode == RESULT_OK){
//            Bitmap photo = (Bitmap)data.getExtras().get("data");
//            image3.setImageBitmap(photo);
//        }
        if(requestCode == SELECT_PHOTO && resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    
    public void setListeners(){
//        changeImage1 = (ImageButton)findViewById(R.id.changeImage1);
//        changeImage2 = (ImageButton)findViewById(R.id.changeImage2);
//        changeImage3 = (ImageButton)findViewById(R.id.changeImage3);
//        image1 = (ImageView)findViewById(R.id.dishImage1);
//        image2 = (ImageView)findViewById(R.id.dishImage2);
//        image3 = (ImageView)findViewById(R.id.dishImage3);
//        changeImage1.setOnClickListener(this);
//        changeImage2.setOnClickListener(this);
//        changeImage3.setOnClickListener(this);
    }

    public void getRating(View v){
        // TODO: Fixing Linting error, this method does nothing yet.
    }
}
