package com.dhcn.restaurantmanager2.calc

import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.dhcn.restaurantmanager2.model.DishFinal
import com.dhcn.restaurantmanager2.model.OrderFinal
import com.dhcn.restaurantmanager2.model.TableFinal
import com.dhcn.restaurantmanager2.roomdb.Order
import com.dhcn.restaurantmanager2.util.Utils
import java.time.LocalDateTime

class OrderAdapter(var order: Order) : OrderFinal {


    override fun getId() = order.id


    override fun getDish(): HashMap<Long, DishAdapter> {
        val res = HashMap<Long, DishAdapter>()

        val arr = order.dishId.split(" ")
        for (item in arr) {
            if (!TextUtils.isEmpty(item)) {
                val dish = Utils.getAppModule().getDishById(item.toLong())
                if (dish != null) {

                    if (res.containsKey(dish.id)) {
                        val x = res[dish.id]
                        x!!.count++
                    } else {
                        val x = DishAdapter(dish)
                        x.count++
                        res[dish.id] = x
                    }
                }
            }
        }
        return res
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getStartTime(): LocalDateTime {
        return Utils.toLocalDateTime(order.startTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getEndTime(): LocalDateTime {
        return Utils.toLocalDateTime(order.endTime)
    }

    override fun getTable(): TableFinal {
        return TableAdapter(Utils.getAppModule().database.dao().loadMyTable(order.tableId))
    }



}