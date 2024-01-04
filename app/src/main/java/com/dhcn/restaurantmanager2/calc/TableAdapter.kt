package com.dhcn.restaurantmanager2.calc

import com.dhcn.restaurantmanager2.model.TableFinal
import com.dhcn.restaurantmanager2.roomdb.MyTable

class TableAdapter(var myTable: MyTable):TableFinal {
    override fun getId(): Long {
        return myTable.id
    }

    override fun getNum(): Int {
        return myTable.num
    }

    override fun getPeolpeNum(): Int {
        return myTable.numPeople
    }

    override fun getStatus(): Int {
        return myTable.status
    }
}