package com.dhcn.restaurantmanager2.model

import com.dhcn.restaurantmanager2.calc.DishAdapter
import java.time.LocalDateTime

interface OrderFinal {
    fun getId(): Long
    fun getDish(): HashMap<Long, DishAdapter>
    fun getStartTime(): LocalDateTime
    fun getEndTime(): LocalDateTime
    fun getTable(): TableFinal
}