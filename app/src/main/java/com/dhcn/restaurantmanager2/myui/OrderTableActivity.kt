package com.dhcn.restaurantmanager2.myui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.TableAdapter
import com.dhcn.restaurantmanager2.event.EventListener
import com.dhcn.restaurantmanager2.ui.theme.Pink01
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet01
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils

class OrderTableActivity : ComponentActivity(), EventListener {
    val allTable = mutableStateOf(Utils.getAppModule().getAllTable())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.getAppModule().eventManager.add(this)
        setContent {
            RestaurantManager2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AllTable()
                }
            }
        }
    }

    override fun handleEvent(ev: Int) {
        when (ev) {
            Constant.UPDATE_DATA -> {
                allTable.value = Utils.getAppModule().getAllTable()
            }
        }
    }

    @Composable
    fun AllTable() {
        Column {
            TopNav2(name = "Đặt bàn", nextActivity = EditTableActivity::class.java as Class<Any>)
            LazyColumn() {
                item {
                    for (item in allTable.value) {
                        OneTable(v = item)
                    }
                }
            }
        }
    }
}

@Composable
fun OneTable(v: TableAdapter) {
    val activity = (LocalContext.current as Activity)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .clickable {
                Utils.getAppModule().isAddTable = false
                Utils.getAppModule().currTable = v.myTable
                val intent = Intent(activity, EditTableActivity::class.java)
                activity.startActivity(intent)
            }
    ) {
        val img = painterResource(id = R.drawable.baseline_border_all_24)
        Image(
            painter = img,
            contentDescription = null,
            modifier = Modifier
                .height(60.dp)
                .width(60.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text(text = "Bàn ${v.getNum()}", color = Violet01)
            Text(text = "${v.getPeolpeNum()} người", color = Pink01)
        }


        val imgOrder= painterResource(id = R.drawable.baseline_calendar_today_24)
        Image(
            painter = imgOrder,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    Utils.getAppModule().currTable = v.myTable
                    val intent = Intent(activity, OrderActivity::class.java)
                    activity.startActivity(intent)
                }
        )


        val imgDelete = painterResource(id = R.drawable.baseline_delete_forever_24)
        Image(
            painter = imgDelete,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    Utils
                        .getAppModule()
                        .deleteTable(v.myTable)
                    Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)
                }
        )
    }
}
