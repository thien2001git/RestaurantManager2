package com.dhcn.restaurantmanager2.myui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.util.Utils
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.OrderAdapter
import com.dhcn.restaurantmanager2.roomdb.Order
import com.dhcn.restaurantmanager2.ui.theme.Violet01
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.TreeSet

class RevenueActivity : ComponentActivity() {
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
                    Revenue()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Revenue() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
        ) {
            TopNav(name = "Doanh thu")


            val allOrder = Utils.getAppModule().loadAllOrder()
            var res = Long.MIN_VALUE
            res = 0

            for (item in allOrder) {
                for (item2 in item.getDish().values) {
                    res += item2.count.toLong() * item2.getPrice()
                }
            }
            RevenueItem("Tổng tiền tất cả: $res")


            var localDate = remember {
                mutableStateOf(LocalDate.now())
            }


            Button(onClick = {
                localDate.value = localDate.value.plusMonths(1)
                val x = 0
            }) {
                Text(text = "+1 tháng")
            }


            RevenueItem("Tổng tiền tháng ${localDate.value.monthValue}/${localDate.value.year}: ${calcMonth(localDate.value)}")
            Button(onClick = {
                localDate.value = localDate.value.minusMonths(1)

            }) {
                Text(text = "-1 tháng")
            }
        }
    }







    @RequiresApi(Build.VERSION_CODES.O)
    fun calcMonth(localDate: LocalDate): Long {
        var thisMonth = Long.MIN_VALUE
        thisMonth = 0
        val allOrder = Utils.getAppModule().loadAllOrder()

        val currMonth = localDate.monthValue

        val firstDayOfMonth = localDate.withDayOfMonth(1)
        val ll = firstDayOfMonth.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val ts =
            TreeSet<OrderAdapter>(Comparator { o1, o2 -> o1.order.startTime.compareTo(o2.order.startTime) })
        ts.addAll(allOrder)

        val _obj = OrderAdapter(Order(0, 0, "", ll, 0))
        var x = ts.ceiling(_obj)

        while (x != null) {
            var chek = Utils.toLocalDateTime(x.order.startTime)
            if (chek.monthValue > currMonth) {
                break
            }
            for (item2 in x.getDish().values) {
                thisMonth += item2.count.toLong() * item2.getPrice()
            }
            x = ts.higher(x)
        }

        return thisMonth


    }

}

@Composable
fun RevenueItem(txt: String) {
    Row(

        modifier = Modifier
            .fillMaxWidth(1f)
    ) {

        val img = painterResource(id = R.drawable.baseline_attach_money_24)
        Image(painter = img, contentDescription = null, modifier = Modifier.size(40.dp))
        Text(text = txt, color = Violet01, fontSize = 30.sp)
    }
}