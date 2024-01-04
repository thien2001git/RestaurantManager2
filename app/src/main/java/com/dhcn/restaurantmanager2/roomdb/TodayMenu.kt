package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodayMenu(
    @PrimaryKey
    var id: Long,
    var today:String,
    var time: Long
) {
}