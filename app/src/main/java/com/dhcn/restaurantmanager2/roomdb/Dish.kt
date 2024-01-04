package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dish(
    @PrimaryKey
    var id: Long,
    var image: String,
    var name: String,
    var description: String,
    var price: Long,
    var type: Int,
) {
}