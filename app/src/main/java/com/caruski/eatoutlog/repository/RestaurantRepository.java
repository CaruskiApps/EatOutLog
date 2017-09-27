package com.caruski.eatoutlog.repository;

import com.caruski.eatoutlog.domain.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    /**
     * @param restaurant to be created
     * @return the id
     */
    long createRestaurant(Restaurant restaurant);

    /**
     * @param rest_id of the restaurant to retrieve
     * @return the restaurant (if found)
     */
    Restaurant getRestaurant(long rest_id);

    /**
     * @return all restaurants.
     */
    List<Restaurant> getAllRestaurants();

    /**
     * @param rest_id of the restaurant to be deleted.
     */
    void deleteRestaurant(long rest_id);
}
