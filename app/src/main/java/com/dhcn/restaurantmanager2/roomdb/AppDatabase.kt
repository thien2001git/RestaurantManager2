package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Account::class, Dish::class, MyTable::class, Order::class, TodayMenu::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): MyDao
}