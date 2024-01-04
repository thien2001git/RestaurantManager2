package com.dhcn.restaurantmanager2.myui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dhcn.restaurantmanager2.calc.DishAdapter
import com.dhcn.restaurantmanager2.calc.OrderAdapter
import com.dhcn.restaurantmanager2.roomdb.Order
import com.dhcn.restaurantmanager2.ui.theme.Pink01
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils
import java.time.LocalDateTime
import java.util.TreeSet

class EditOrderActivity : ComponentActivity() {
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
                    EditOrder()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun EditOrder() {
        val appContext = LocalContext.current
        val _name = if (Utils.getAppModule().isAddOrder) {
            "Thêm"
        } else {
            "Cập nhật"
        }
        val v = OrderAdapter(Utils.getAppModule().currOrder)
        val currTime = LocalDateTime.now()

        val startHour = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.hour.toString())
            } else {
                mutableStateOf(v.getStartTime().hour.toString())
            }
        }
        val startMinute = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.minute.toString())
            } else {
                mutableStateOf(v.getStartTime().minute.toString())
            }
        }
        val startDay = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.dayOfMonth.toString())
            } else {
                mutableStateOf(v.getStartTime().dayOfMonth.toString())
            }
        }
        val startMonth = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.monthValue.toString())
            } else {
                mutableStateOf(v.getStartTime().monthValue.toString())
            }
        }
        val startYear = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.year.toString())
            } else {
                mutableStateOf(v.getStartTime().year.toString())
            }
        }

        val endHour = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.hour.toString())
            } else {
                mutableStateOf(v.getEndTime().hour.toString())
            }
        }
        val endMinute = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.minute.toString())
            } else {
                mutableStateOf(v.getEndTime().minute.toString())
            }
        }
        val endDay = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.dayOfMonth.toString())
            } else {
                mutableStateOf(v.getEndTime().dayOfMonth.toString())
            }
        }

        val endMonth = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.monthValue.toString())
            } else {
                mutableStateOf(v.getEndTime().monthValue.toString())
            }
        }


        val endYear = remember {
            if (Utils.getAppModule().isAddOrder) {
                mutableStateOf(currTime.year.toString())
            } else {
                mutableStateOf(v.getEndTime().year.toString())
            }
        }
        LazyColumn {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TopNav(name = _name)




                    Column {


                        TextField(
                            value = startHour.value, onValueChange = { startHour.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Giờ bắt đầu"
                                )
                            },
                        )

                        TextField(
                            value = startMinute.value, onValueChange = { startMinute.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Phút bắt đầu"
                                )
                            },
                        )


                        TextField(
                            value = startDay.value, onValueChange = { startDay.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Ngày bắt đầu"
                                )
                            },
                        )


                        TextField(
                            value = startMonth.value, onValueChange = { startMonth.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Tháng bắt đầu"
                                )
                            },
                        )

                        TextField(
                            value = startYear.value, onValueChange = { startYear.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Năm bắt đầu"
                                )
                            },
                        )

                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    Column {


                        TextField(
                            value = endHour.value, onValueChange = { endHour.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Giờ kết thúc"
                                )
                            },
                        )

                        TextField(
                            value = endMinute.value, onValueChange = { endMinute.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Phút kết thúc"
                                )
                            },
                        )

                        TextField(
                            value = endDay.value, onValueChange = { endDay.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Ngày kết thúc"
                                )
                            },
                        )

                        TextField(
                            value = endMonth.value, onValueChange = { endMonth.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Tháng kết thúc"
                                )
                            },
                        )

                        TextField(
                            value = endYear.value, onValueChange = { endYear.value = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "Năm kết thúc"
                                )
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {

                        var order = if (Utils.getAppModule().isAddOrder) {
                            Constant.prototypeOrder
                        } else {
                            Utils.getAppModule().currOrder
                        }

                        if (Utils.getAppModule().isAddOrder) {
                            order.id = System.currentTimeMillis()
                        }

                        try {
                            val startLong = Utils.convertToMilliseconds(
                                startYear.value.toInt(),
                                startMonth.value.toInt(),
                                startDay.value.toInt(),
                                startHour.value.toInt(),
                                startMinute.value.toInt()
                            )
                            val endLong = Utils.convertToMilliseconds(
                                endYear.value.toInt(),
                                endMonth.value.toInt(),
                                endDay.value.toInt(),
                                endHour.value.toInt(),
                                endMinute.value.toInt()
                            )
                            if (endLong <= startLong) {
                                throw Exception()
                            }

                            val allOrder = TreeSet<OrderAdapter>(Comparator { o1, o2 ->
                                o1.order.startTime.compareTo(o2.order.startTime)
                            })
                            allOrder.addAll(
                                Utils.getAppModule()
                                    .loadOrderByTableId(Utils.getAppModule().currTable.id)
                            )

                            val _obj = OrderAdapter(Order(0, 0, "", startLong, 0))
                            val floor = allOrder.floor(_obj)
                            val ceiling = allOrder.ceiling(_obj)

                            if(floor != null && floor.order.endTime > startLong) {
                                throw Exception()
                            }

                            if(ceiling != null && ceiling.order.startTime < endLong) {
                                throw Exception()
                            }


                            order.startTime = startLong
                            order.endTime = endLong
                            order.dishId = ""
                            order.tableId = Utils.getAppModule().currTable.id

                            for (item in Utils.getAppModule().hashDish.entries) {
                                for (i in 1..item.value) {
                                    order.dishId += "${item.key} "
                                }
                            }


                            Utils.getAppModule().database.dao().insertOrder(order)


                            Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)
                            Toast.makeText(appContext, "$_name thành công", Toast.LENGTH_SHORT)
                                .show()
                        } catch (e: Exception) {
                            Toast.makeText(appContext, "$_name thất bại", Toast.LENGTH_SHORT).show()
                        }


                    }) {
                        Text(text = _name)
                    }


                    val orderAdapter = if (Utils.getAppModule().isAddOrder) {
                        OrderAdapter(Constant.prototypeOrder)
                    } else {
                        OrderAdapter(Utils.getAppModule().currOrder)
                    }

                    for (item in orderAdapter.getDish().values) {
                        Utils.getAppModule().hashDish[item.getId()] = item.count
                    }

                    Text(text = "Món ăn", color = Pink01, fontSize = 30.sp)
                    val all = Utils.getAppModule().getAllDishOrDink(Constant.DISH)
                    for (item in all) {

                        val num = if (orderAdapter.getDish().containsKey(item.getId())) {
                            orderAdapter.getDish()[item.getId()]!!.count
                        } else {
                            0
                        }
                        SelectDish(v = item, num)
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Text(text = "Đồ uống", color = Pink01, fontSize = 30.sp)
                    val all2 = Utils.getAppModule().getAllDishOrDink(Constant.DRINK)
                    for (item in all2) {
                        val num = if (orderAdapter.getDish().containsKey(item.getId())) {
                            orderAdapter.getDish()[item.getId()]!!.count
                        } else {
                            0
                        }
                        SelectDish(v = item, num)
                        Spacer(modifier = Modifier.height(10.dp))
                    }


                }
            }


        }
    }
}

@Composable
fun SelectDish(v: DishAdapter, _num: Int) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val num = remember {
            mutableStateOf(_num.toString())
        }
        Button(onClick = {
            var n = num.value.toInt()
            n++

            Utils.getAppModule().hashDish[v.getId()] = n

            num.value = n.toString()
        }) {
            Text(text = "+")
        }
        Text(text = num.value)
        Button(onClick = {
            var n = num.value.toInt()
            n--
            if (n < 0) {
                n = 0
            }
            Utils.getAppModule().hashDish[v.getId()] = n
            num.value = n.toString()
        }) {
            Text(text = "-")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(

        ) {
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
            Text(text = v.getName(), color = Violet)
            Text(text = v.getPrice().toString(), color = Pink01)
        }

    }
}