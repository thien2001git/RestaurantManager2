package com.dhcn.restaurantmanager2.calc

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.dhcn.restaurantmanager2.model.MenuFinal
import com.dhcn.restaurantmanager2.roomdb.Dish
import com.dhcn.restaurantmanager2.roomdb.TodayMenu
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class MenuAdapter(var menu: TodayMenu): MenuFinal {

    var numDish = 0;
    var numDrink = 0;
    override fun getId(): Long {
        return menu.id
    }

    override fun getList(): ArrayList<DishAdapter> {
        val ll = menu.today.split(" ")
        val res = ArrayList<DishAdapter>()
        for(item in ll) {
            if(!TextUtils.isEmpty(item)) {
                val e1 = Utils.getAppModule().getDishById(item.toLong())
                if(e1 != null) {
                    val e = DishAdapter(e1)
                    res.add(e)
                    if (e.getType() == Constant.DISH) {
                        numDish++
                    } else {
                        numDrink++
                    }
                }
            }
        }
        return res
    }

//    override fun getNumDish(): Int {
//        return numDish
//    }
//
//    override fun getNumDrink(): Int {
//        return numDrink
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTime(): LocalDateTime {

        return Utils.toLocalDateTime(menu.time)
    }
}