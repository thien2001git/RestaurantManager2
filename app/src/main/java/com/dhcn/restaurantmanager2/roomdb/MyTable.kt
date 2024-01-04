package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyTable(
    @PrimaryKey
    var id: Long,
    var num: Int,
    var numPeople: Int,
    var status: Int
) {
}