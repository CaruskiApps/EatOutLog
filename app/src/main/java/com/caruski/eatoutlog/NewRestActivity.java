package com.caruski.eatoutlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewRestActivity extends AppCompatActivity{

    DBOpenHelper dbHelper;
    long restId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rest);

        dbHelper = new DBOpenHelper(getApplicationContext());
        final EditText restNameBox = (EditText) findViewById(R.id.editRestName);
        final EditText restCityBox = (EditText) findViewById(R.id.editRestCity);
        final EditText restStateBox = (EditText) findViewById(R.id.editRestState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("rest_id")) {
                restId = extras.getLong("rest_id");
                Restaurant rest = dbHelper.getRestaurant(restId);
                setTitle(rest.getName());
                restNameBox.setText(rest.getName());
                restCityBox.setText(rest.getCity());
                restStateBox.setText(rest.getState());
            }
        }

        FloatingActionButton saveRestButton = (FloatingActionButton)findViewById(R.id.newRestSaveButton);
        saveRestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText name = (EditText)findViewById(R.id.editRestName);
                String rName = name.getText().toString();
                EditText state = (EditText)findViewById(R.id.editRestState);
                String rState = state.getText().toString();
                EditText city = (EditText)findViewById(R.id.editRestCity);
                String rCity = city.getText().toString();

                if(rName.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please enter a name for restaurant.",
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }
                else{
                    Restaurant restaurant = new Restaurant(rName, rState, rCity);
                    long restId = dbHelper.createRestaurant(restaurant);
                    Toast.makeText(getApplicationContext(), "Restaurant Saved\nID: " + restId,
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewRestActivity.this, NewDishActivity.class);
                    intent.putExtra("rest_id", restId);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_rest_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
