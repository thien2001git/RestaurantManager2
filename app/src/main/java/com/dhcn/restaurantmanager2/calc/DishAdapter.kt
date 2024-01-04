package com.dhcn.restaurantmanager2.calc

import com.dhcn.restaurantmanager2.model.DishFinal
import com.dhcn.restaurantmanager2.roomdb.Dish

class DishAdapter(var dish: Dish): DishFinal {

    var count = 0;
    override fun getId(): Long {
        return dish.id
    }

    override fun getImage(): String {
        return dish.image
    }

    override fun getName(): String {
        return dish.name
    }

    override fun getDescription(): String {
        return dish.description
    }

    override fun getPrice(): Long {
        return dish.price
    }

    override fun getType(): Int {
        return dish.type
    }

    override fun getMyCount() = count
}