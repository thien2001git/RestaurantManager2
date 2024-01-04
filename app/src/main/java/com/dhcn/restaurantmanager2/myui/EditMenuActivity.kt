package com.dhcn.restaurantmanager2.myui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.DishAdapter
import com.dhcn.restaurantmanager2.roomdb.Dish
import com.dhcn.restaurantmanager2.roomdb.TodayMenu
import com.dhcn.restaurantmanager2.ui.theme.Pink01
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet01
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class EditMenuActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            RestaurantManager2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EditMenu()
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditMenu() {

    val allDish = remember {
        mutableStateOf(Utils.getAppModule().getAllDishOrDink(Constant.DISH))
    }
    val allDrink = remember {
        mutableStateOf(Utils.getAppModule().getAllDishOrDink(Constant.DRINK))
    }
    var currentDateTime = LocalDateTime.now()
    if (!Utils.getAppModule().isAddMenu) {
        currentDateTime = Utils.toLocalDateTime(Utils.getAppModule().currTodayMenu.time)
    }

    var _day = remember {
        mutableStateOf("${currentDateTime.dayOfMonth}")
    }
    var _month = remember {
        mutableStateOf("${currentDateTime.monthValue}")
    }
    var _year = remember {
        mutableStateOf("${currentDateTime.year}")
    }

    var todayMenu = TodayMenu(
        Utils.getAppModule().currTodayMenu.id,
        Utils.getAppModule().currTodayMenu.today,
        Utils.getAppModule().currTodayMenu.time
    )

    val _name = if (Utils.getAppModule().isAddMenu) {
        "Thêm"
    } else {
        "Cập nhật"
    }
    val appContext = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopNav(name = _name)


        TextField(
            value = _day.value, onValueChange = { _day.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Ngày") },
            placeholder = {
                Text(text = "10")
            },
            enabled = Utils.getAppModule().isAddMenu

        )


        TextField(
            value = _month.value, onValueChange = { _month.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Tháng") },
            placeholder = { Text(text = "10") },
            enabled = Utils.getAppModule().isAddMenu
        )


        TextField(
            value = _year.value, onValueChange = { _year.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Năm") },
            placeholder = { Text(text = "2001") },
            enabled = Utils.getAppModule().isAddMenu
        )

        val _allDay = Utils.getAppModule().getAllDay()
        Button(

            onClick = {
                try {
                    if (Utils.getAppModule().isAddMenu) {
                        todayMenu.id = System.currentTimeMillis()
                        todayMenu.time = Utils.convertToMilliseconds(
                            _year.value.toInt(),
                            _month.value.toInt(),
                            _day.value.toInt()
                        )
                        if (Utils.getAppModule()
                                .loadMenuByTime(todayMenu.time, _allDay)
                        ) {
                            throw Exception("Ngày đã tồn tại!")
                        }

                    }
                    todayMenu.today = ""
                    for (item in Utils.getAppModule().setSelect) {
                        todayMenu.today += "$item "
                    }
                    todayMenu.today.trim()
                    Utils.getAppModule().database.dao().insertTodayMenu(todayMenu)
                    Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)
                    Toast.makeText(appContext, "$_name thành công!", Toast.LENGTH_SHORT).show()
                } catch (e: DateTimeException) {
                    Toast.makeText(appContext, "$_name thất bại!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(appContext, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = _name)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            LazyColumn() {
                item {
                    Text(text = "Món ăn")
                    for (item in allDish.value) {
                        val isCheck = Utils.getAppModule().setSelect.contains(item.getId())
                        CheckBoxItem(item, isCheck)
                    }
                }

            }
            LazyColumn() {
                item {
                    Text(text = "Đồ uống")
                    for (item in allDrink.value) {
                        val isCheck = Utils.getAppModule().setSelect.contains(item.getId())
                        CheckBoxItem(item, isCheck)
                    }
                }

            }
        }

    }
}

@Composable
fun CheckBoxItem(v: DishAdapter, isCheck: Boolean) {

    val _check = remember {
        mutableStateOf(isCheck)
    }
    Row(

    ) {
        Checkbox(checked = _check.value, onCheckedChange = {
            _check.value = it
            if (it) {
                Utils.getAppModule().setSelect.add(v.getId())
            } else {
                Utils.getAppModule().setSelect.remove(v.getId())
            }
        })

        Column {
            val uri = if (v.getImage().isNullOrBlank()) {
                null
            } else {
                Uri.parse(v.getImage())
            }
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(text = v.getName(), fontSize = 24.sp, color = Violet01)
            Text(text = v.getPrice().toString(), color = Pink01)
        }
    }
}