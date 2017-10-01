package com.caruski.eatoutlog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.caruski.eatoutlog.domain.Restaurant;
import com.caruski.eatoutlog.repository.DishRepository;
import com.caruski.eatoutlog.repository.RestaurantRepository;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.caruski.eatoutlog.constants.Constants.REST_ID;

public class MainActivity extends AppCompatActivity {

    @Inject
    RestaurantRepository restaurantRepository;
    @Inject
    DishRepository dishRepository;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Restaurant> restaurants = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pass the view to Dagger for injection
        EatOutLogApplication.injector().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int index = 0;
        restaurants = restaurantRepository.getAllRestaurants();

        ListView lv = (ListView) findViewById(R.id.restList);
        registerForContextMenu(lv);
        final String[] from = new String[restaurants.size()];
        for (Restaurant r : restaurants) {
            from[index] = r.getName();
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
                Intent intent = new Intent(MainActivity.this, ViewDishesActivity.class);
                intent.putExtra(REST_ID, restaurants.get(position).getId());
                startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void newRestaurant(View view) {
        Intent intent = new Intent(this, NewRestActivity.class);
        startActivity(intent);
    }

    public void deleteRestaurant(long restId) {
        int count = dishRepository.getDishCount(restId);
        if (count > 0) {
            Toast.makeText(getApplicationContext(), "Restaurant has " + count + " dishes still", Toast.LENGTH_SHORT).show();
        } else {
            restaurantRepository.deleteRestaurant(restId);
            Toast.makeText(getApplicationContext(), "Restaurant deleted.", Toast.LENGTH_SHORT).show();
            startActivity(this.getIntent());
        }
    }

    public void editRestaurant(long restId) {
        Intent intent = new Intent(this, NewRestActivity.class);
        intent.putExtra(REST_ID, restId);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_edit_restaurant:
                editRestaurant(restaurants.get((int) info.id).getId());
                return true;
            case R.id.action_delete_restaurant:
                int restId = (int) info.id;
                deleteRestaurant(restaurants.get(restId).getId());
                return true;
            case R.id.action_share_restaurant:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add_new: {
                Intent intent = new Intent(this, NewRestActivity.class);
                startActivity(intent);
            }
            case R.id.swiperefresh: {
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}