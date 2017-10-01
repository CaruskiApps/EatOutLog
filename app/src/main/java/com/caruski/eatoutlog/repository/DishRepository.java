package com.caruski.eatoutlog.repository;

import com.caruski.eatoutlog.domain.Dish;

import java.util.List;

public interface DishRepository {

    long createDish(Dish dish);

    Dish getDish(long dishId);

    /**
     * get all dishes that match a restId
     *
     * @param restId to find dishes of
     * @return the dishes
     */
    List<Dish> getAllDishes(long restId);

    int updateDish(Dish dish);

    /**
     * get number of dishes for given restaurant ID
     *
     * @param restId to count
     * @return # of dishes
     */
    int getDishCount(long restId);

    void deleteDish(long dishId);
}
