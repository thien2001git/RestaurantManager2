package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey
    var id: Long,
    var tableId: Long,
    var dishId: String,
    var startTime: Long,
    var endTime: Long,
    ) {
}