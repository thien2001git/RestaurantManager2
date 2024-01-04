package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import javax.annotation.processing.Generated

@Entity
data class Account(
    @PrimaryKey
    var id: Long,
    var name: String,
    var password: String,
)