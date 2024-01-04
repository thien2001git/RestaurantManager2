package com.dhcn.restaurantmanager2.myui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.OrderAdapter
import com.dhcn.restaurantmanager2.event.EventListener
import com.dhcn.restaurantmanager2.ui.theme.Pink01
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet01
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils

class OrderActivity : ComponentActivity(), EventListener {

    val allOrder =
        mutableStateOf(Utils.getAppModule().loadOrderByTableId(Utils.getAppModule().currTable.id))

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.getAppModule().eventManager.add(this)
        setContent {
            RestaurantManager2Theme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AllOrder()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AllOrder() {


        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
        ) {
            TopNav2(name = "Order", nextActivity = EditOrderActivity::class.java as Class<Any>)
            LazyColumn {
                item {
                    for (item in allOrder.value) {
                        Spacer(modifier = Modifier.height(10.dp))
                        OneOrder(item)
                    }
                }
            }
        }
    }

    override fun handleEvent(ev: Int) {
        when (ev) {
            Constant.UPDATE_DATA -> {
                allOrder.value =
                    Utils.getAppModule().loadOrderByTableId(Utils.getAppModule().currTable.id)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OneOrder(v: OrderAdapter) {
    val activity = (LocalContext.current as Activity)
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clickable {
                Utils.getAppModule().isAddOrder = false
                Utils.getAppModule().currOrder = v.order
                val intent = Intent(activity, EditOrderActivity::class.java)
                activity.startActivity(intent)
            }
    ) {
        val img = painterResource(id = R.drawable.baseline_border_all_24)
        Image(painter = img, contentDescription = null, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Text(text = "Bàn ${v.getTable().getId()}", color = Violet01)
            Text(text = "Bắt đầu: ${Utils.formatLocalDateTime1(v.getStartTime())}", color = Pink01)
            Text(text = "Kết thúc: ${Utils.formatLocalDateTime1(v.getEndTime())}", color = Pink01)

            var num = 0
            for (item in v.getDish().values) {
                num += item.count
            }
            Text(text = "Số món: ${num}", color = Pink01)


            var price = Long.MAX_VALUE
            price = 0

            for (item in v.getDish().values) {
                price += item.count.toLong() * item.getPrice()
            }

            Text(text = "Giá tiền: ${price}", color = Pink01)
        }


        val delete = painterResource(id = R.drawable.baseline_delete_forever_24)
        Image(
            painter = delete,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    Utils.getAppModule().database.dao().deleteOrder(v.order)
                    Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)
                })

    }
}

