package com.dhcn.restaurantmanager2.event

class EventManager {
    val list = ArrayList<EventListener>()
    fun add(listener: EventListener) {
        list.add(listener)
    }

    fun remove(listener: EventListener) {
        list.remove(listener)
    }

    fun notify(ev: Int) {
        for (item in list) {
            item.handleEvent(ev)
        }
    }
}