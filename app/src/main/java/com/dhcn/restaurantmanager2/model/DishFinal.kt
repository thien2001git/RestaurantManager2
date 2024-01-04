package com.dhcn.restaurantmanager2.model

interface DishFinal {


    fun getId(): Long
    fun getImage() = ""
    fun getName() = ""
    fun getDescription() = ""
    fun getPrice(): Long
    fun getType(): Int
    fun getMyCount(): Int
}