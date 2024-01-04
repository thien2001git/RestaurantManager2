package com.dhcn.restaurantmanager2.model

import com.dhcn.restaurantmanager2.calc.DishAdapter
import java.time.LocalDateTime

interface MenuFinal {
    fun getId(): Long
    fun getList(): ArrayList<DishAdapter>
//    fun getNumDish(): Int
//    fun getNumDrink(): Int
    fun getTime(): LocalDateTime
}