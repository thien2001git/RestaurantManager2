package com.dhcn.restaurantmanager2.dependencyinjection

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.room.Delete
import androidx.room.Room
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.DishAdapter
import com.dhcn.restaurantmanager2.calc.MenuAdapter
import com.dhcn.restaurantmanager2.calc.OrderAdapter
import com.dhcn.restaurantmanager2.calc.TableAdapter
import com.dhcn.restaurantmanager2.event.EventManager
import com.dhcn.restaurantmanager2.model.DishFinal
import com.dhcn.restaurantmanager2.myui.MainActivity
import com.dhcn.restaurantmanager2.roomdb.AppDatabase
import com.dhcn.restaurantmanager2.roomdb.Dish
import com.dhcn.restaurantmanager2.roomdb.MyTable
import com.dhcn.restaurantmanager2.roomdb.Order
import com.dhcn.restaurantmanager2.roomdb.TodayMenu
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.SharedPreferenceHelper
import com.dhcn.restaurantmanager2.util.Utils
import java.time.LocalDateTime
import java.util.Objects
import kotlin.reflect.KClass

class AppModule {
    var appContext: Context
    var database: AppDatabase
    var isAddDish: Boolean
    var isDish: Int
    var currDish: Dish
    var eventManager = EventManager()

    var currTodayMenu: TodayMenu
    var isAddMenu: Boolean
    var setSelect: HashSet<Long>

    var isAddTable: Boolean
    var currTable: MyTable

    var isAddOrder: Boolean
    var currOrder: Order


    var hashDish: HashMap<Long, Int>

    constructor(appContext: Context) {
        this.appContext = appContext
        database = Room.databaseBuilder(appContext, AppDatabase::class.java, "Restaurant.db")
            .allowMainThreadQueries()
            .build()
        isAddDish = true
        isDish = Constant.DISH
        currDish = Dish(0, "", "", "", 0, Constant.DISH)

        isAddMenu = true
        currTodayMenu = Constant.prototypeTodayMenu
        setSelect = HashSet<Long>()

        isAddTable = true
        currTable = Constant.prototypeTable

        isAddOrder = true
        currOrder = Constant.prototypeOrder

        hashDish = HashMap()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun firstSetUpDatabase(activity: Activity) {
        if (SharedPreferenceHelper.getBoolean(
                activity,
                appContext.getString(R.string.is_first_run),
                true
            )
        ) {
            SharedPreferenceHelper.setBoolean(
                activity,
                appContext.getString(R.string.is_first_run),
                false
            )
            for (i in 0..9) {
                database.dao().insertDish(
                    Dish(
                        i.toLong(),
                        "",
                        "Gỏi bò$i",
                        "Gỏi bò$i",
                        price = 1000000 + i.toLong() * 100,
                        Constant.DISH
                    )
                )
            }
            for (i in 10..19) {
                database.dao().insertDish(
                    Dish(
                        i.toLong(),
                        "",
                        "Gỏi bò$i",
                        "Gỏi bò$i",
                        price = 1000000 + i.toLong() * 100,
                        Constant.DRINK
                    )
                )
            }


            for (i in 0..9) {

                database.dao().insertTodayMenu(
                    TodayMenu(
                        i.toLong(),
                        "0 1 11 12 6 9",
                        System.currentTimeMillis() + 86400000 * i
                    )
                )
            }


            for (i in 0..29) {

                database.dao().insertMyTable(
                    MyTable(i.toLong(), i, 6, Constant.AVAILABLE)
                )
            }

            val x = System.currentTimeMillis()
            for (i in 0..9) {

                database.dao().insertOrder(
                    Order(
                        i.toLong(),
                        0,
                        "1 0 9 5 3 2 1",
                        x + i.toLong() * 3600000,
                        x + (i + 1).toLong() * 3600000
                    )
                )
            }
        }
    }

    fun getAllDishOrDink(type: Int): ArrayList<DishAdapter> {
        val res = ArrayList<DishAdapter>()
        val all = database.dao().loadAllDish()
        for (item in all) {
            if (item.type == type) {
                res.add(DishAdapter(item))
            }
        }
        return res
    }

    fun getDishById(id: Long) = database.dao().loadDishById(id)

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadMenuByTime(time: Long, list: ArrayList<MenuAdapter>): Boolean {
        val l = Utils.toLocalDateTime(time)
        val set = HashSet<String>()
        for (item in list) {
            set.add(Utils.formatLocalDateTime(item.getTime()))
        }
        return set.contains(Utils.formatLocalDateTime(l))
    }

    fun getAllDay(): ArrayList<MenuAdapter> {
        val res = ArrayList<MenuAdapter>()
        val all = database.dao().loadAllTodayMenu()
        for (item in all) {
            res.add(MenuAdapter(item))
        }
        return res
    }


    fun deleteDish(dish: Dish) = database.dao().deleteDish(dish)
    fun deleteTodayMenu(todayMenu: TodayMenu) = database.dao().deleteTodayMenu(todayMenu)

    fun getAllTable(): ArrayList<TableAdapter> {
        val res = ArrayList<TableAdapter>()
        val ll = database.dao().loadAllMyTable()
        for (item in ll) {
            res.add(TableAdapter(item))
        }
        return res
    }

    fun deleteTable(myTable: MyTable) = database.dao().deleteTable(myTable)


    fun loadOrderByTableId(tableId: Long): ArrayList<OrderAdapter> {
        val all = database.dao().loadOrderByTableId(tableId)
        val res = ArrayList<OrderAdapter>()
        for (item in all) {
            res.add(OrderAdapter(item))
        }
        return res
    }

    fun loadAllOrder(): ArrayList<OrderAdapter> {

        val all = database.dao().loadAllOrder()
        val res = ArrayList<OrderAdapter>()
        for (item in all) {
            res.add(OrderAdapter(item))
        }
        return res
    }
//    fun

}