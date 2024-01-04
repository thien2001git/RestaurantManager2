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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import com.dhcn.restaurantmanager2.App
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.MenuAdapter
import com.dhcn.restaurantmanager2.event.EventListener
import com.dhcn.restaurantmanager2.roomdb.TodayMenu
import com.dhcn.restaurantmanager2.ui.theme.Orange
import com.dhcn.restaurantmanager2.ui.theme.Pink
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet01
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils

class MenuActivity : ComponentActivity(), EventListener {

    val allDay = mutableStateOf(Utils.getAppModule().getAllDay())

    @RequiresApi(Build.VERSION_CODES.O)
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
                    AllDayMenu()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AllDayMenu() {
        Column {
            TopNav2(name = "MENU", EditMenuActivity::class.java as Class<Any>)
            LazyColumn() {
                item {
                    for (item in allDay.value) {
                        OneDayMenu(v1 = item)
                    }
                }
            }
        }
    }

    override fun handleEvent(ev: Int) {
        when (ev) {
            Constant.UPDATE_DATA -> {
                allDay.value = Utils.getAppModule().getAllDay()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OneDayMenu(v1: MenuAdapter) {
//    val v = TodayMenu(0, "0 1 11 12 6 9", System.currentTimeMillis())
//    val v1 = MenuAdapter(v)
    val activity = (LocalContext.current as Activity)
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 10.dp, top = 10.dp)
            .clickable {
                Utils.getAppModule().currTodayMenu = v1.menu
                Utils.getAppModule().setSelect.clear()
                Utils.getAppModule().isAddMenu = false
                for (item in v1.getList()) {
                    Utils.getAppModule().setSelect.add(item.getId())
                }
                val intent = Intent(activity, EditMenuActivity::class.java)
                activity.startActivity(intent)
            }

    ) {
        val img = painterResource(id = R.drawable.baseline_edit_document_24)
        Image(
            painter = img,
            contentDescription = null,
            modifier = Modifier.size(width = 32.dp, height = 32.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Ngày ${Utils.formatLocalDateTime(v1.getTime())}",
                color = Violet01,
                fontSize = 20.sp
            )
            Text(text = "Số món: ${v1.getList().size}", color = Orange)
            Text(text = "Món ăn: ${v1.numDish}", color = Pink)
            Text(text = "Đồ uống: ${v1.numDrink}", color = Pink)
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable {
                    Utils.getAppModule().deleteTodayMenu(v1.menu)
                    Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)
                }
        ) {
            val delImg = painterResource(id = R.drawable.baseline_delete_forever_24)
            Image(
                painter = delImg,
                contentDescription = null,
                modifier = Modifier.size(width = 50.dp, height = 50.dp)
            )
        }
    }
}


