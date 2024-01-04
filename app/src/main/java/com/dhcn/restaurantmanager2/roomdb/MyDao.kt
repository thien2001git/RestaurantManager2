package com.dhcn.restaurantmanager2.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(vararg value: Account)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDish(vararg value: Dish)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyTable(vararg value: MyTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(vararg value: Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodayMenu(vararg value: TodayMenu)

    @Query("SELECT * FROM Account")
    fun loadAllAccount(): Array<Account>

    @Query("SELECT * FROM Dish")
    fun loadAllDish(): Array<Dish>

    @Query("SELECT * FROM MyTable")
    fun loadAllMyTable(): Array<MyTable>

    @Query("SELECT * FROM MyTable WHERE id = :id")
    fun loadMyTable(id: Long): MyTable

    @Query("SELECT * FROM `Order`")
    fun loadAllOrder(): Array<Order>

    @Query("SELECT * FROM `TodayMenu`")
    fun loadAllTodayMenu(): Array<TodayMenu>


    @Query("SELECT * FROM Dish WHERE id = :id")
    fun loadDishById(id:Long): Dish

    @Query("SELECT * FROM TodayMenu WHERE id = :time")
    fun loadMenuByTime(time:Long): TodayMenu

    @Query("SELECT * FROM `Order` WHERE tableId = :tableId")
    fun loadOrderByTableId(tableId:Long): Array<Order>


    @Delete
    fun deleteDish(dish: Dish)

    @Delete
    fun deleteTodayMenu(todayMenu: TodayMenu)

    @Delete
    fun deleteTable(myTable: MyTable)

    @Delete
    fun deleteOrder(order: Order)
}