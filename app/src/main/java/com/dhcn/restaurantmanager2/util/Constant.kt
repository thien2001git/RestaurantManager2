package com.dhcn.restaurantmanager2.util

import com.dhcn.restaurantmanager2.roomdb.Dish
import com.dhcn.restaurantmanager2.roomdb.MyTable
import com.dhcn.restaurantmanager2.roomdb.Order
import com.dhcn.restaurantmanager2.roomdb.TodayMenu

object Constant {

    val DISH = 1
    val DRINK = 2

    val AVAILABLE = 0
    val NOT_AVAILABLE = 1

    val prototypeTable = MyTable(0,0,0, AVAILABLE)

    var prototypeDish = Dish(0, "", "", "", 0, DISH)
    var prototypeTodayMenu = TodayMenu(0, "", System.currentTimeMillis())
    var prototypeOrder = Order(0, 0, "", 0, 0)

    val UPDATE_DATA = 1001

}