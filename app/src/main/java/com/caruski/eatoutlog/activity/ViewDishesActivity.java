package com.caruski.eatoutlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.caruski.eatoutlog.EatOutLogApplication;
import com.caruski.eatoutlog.R;
import com.caruski.eatoutlog.domain.Dish;
import com.caruski.eatoutlog.domain.Restaurant;
import com.caruski.eatoutlog.repository.DishRepository;
import com.caruski.eatoutlog.repository.RestaurantRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class ViewDishesActivity extends AppCompatActivity {

    @Inject
    RestaurantRepository restaurantRepository;
    @Inject
    DishRepository dishRepository;
    private long restId;
    private ListView lv;
    List<Dish> dishes = null;

    protected void onCreate(Bundle savedInstanceState) {
        EatOutLogApplication.app().basicComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dishes);

        //get restId from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            restId = extras.getLong("rest_id");
        }
        //set activity title to restaurant name
        Restaurant restaurant = restaurantRepository.getRestaurant(restId);
        String title = restaurant.getName();
        title = title.substring(0, 1).toUpperCase(Locale.getDefault()) + title.substring(1);
        setTitle(title);

        //populate listView with dishes
        int index = 0;
        dishes = dishRepository.getAllDishes(restId);
        lv = (ListView) findViewById(R.id.dishList);
        registerForContextMenu(lv);
//        if (dishes.size() == 0) {
//            Toast.makeText(getApplicationContext(), "You need to add a dish!",
//                    Toast.LENGTH_SHORT).show();
//        }
        final String[] from = new String[dishes.size()];
        double average;
        //iterate and store dish names and ratings
        for (Dish d : dishes) {
            average = (double) Math.round(((d.getLook() + d.getTaste() + d.getTexture()) / 3) * 100d) / 100d;
            from[index] = d.getName() + "                " + average;
            from[index] = from[index].substring(0, 1).toUpperCase(Locale.getDefault()) + from[index].substring(1);
            index++;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                from);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), NewDishActivity.class);
                intent.putExtra("dish_id", dishes.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void newDish(View view) {
        Intent intent = new Intent(this, NewDishActivity.class);
        intent.putExtra("rest_id", restId);
        startActivity(intent);
    }

    public void deleteDish(long dishId) {
        dishRepository.deleteDish(dishId);
        Toast.makeText(getApplicationContext(), "Dish deleted.", Toast.LENGTH_SHORT).show();
        startActivity(this.getIntent());
    }

    public void deleteAllDishes() {
        int count = 0;
        for (Dish d : dishes) {
            dishRepository.deleteDish(d.getId());
            count++;
        }
        Toast.makeText(getApplicationContext(), count + " dishes deleted.", Toast.LENGTH_SHORT).show();
        startActivity(this.getIntent());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_dish_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_edit_dish:
                Intent intent = new Intent(getBaseContext(), NewDishActivity.class);
                intent.putExtra("dish_id", dishes.get((int) info.id).getId());
                startActivity(intent);
                return true;
            case R.id.action_delete_dish:
                int dishId = (int) info.id;
                deleteDish(dishes.get(dishId).getId());
                return true;
            case R.id.action_share_dish:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_restaurant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_sort_rest_view: {
                return true;
            }
            case R.id.action_delete_all_dishes: {
                deleteAllDishes();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
