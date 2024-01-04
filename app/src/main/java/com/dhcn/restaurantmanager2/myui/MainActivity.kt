package com.dhcn.restaurantmanager2.myui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dhcn.restaurantmanager2.App
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.ui.theme.Pink
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.getInstant().appModule.firstSetUpDatabase(this)

        setContent {
            RestaurantManager2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}


@Composable
fun ItemMenu(myDrawble: Int, name: String) {
    val mContext = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {

            when (name) {
                "Món ăn" -> {
                    Utils.getAppModule().isDish = Constant.DISH
                    val intent = Intent(mContext, DishActivity::class.java)
                    mContext.startActivity(intent)
                }

                "Đồ uống" -> {
                    Utils.getAppModule().isDish = Constant.DRINK
                    val intent = Intent(mContext, DishActivity::class.java)
                    mContext.startActivity(intent)
                }

                "Lập menu" -> {
                    val intent = Intent(mContext, MenuActivity::class.java)
                    mContext.startActivity(intent)
                }

                "Đặt bàn" -> {
                    val intent = Intent(mContext, OrderTableActivity::class.java)
                    mContext.startActivity(intent)
                }

                "Doanh thu" -> {
                    val intent = Intent(mContext, RevenueActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
        }
    ) {
        val image: Painter = painterResource(id = myDrawble)

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .width(90.dp)
                .height(90.dp)
        )
        Text(
            text = name,
            color = Pink
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainView() {
    val names = arrayListOf<String>(
        "Đồ uống",
        "Món ăn",
        "Lập menu",
        "Đặt bàn",
        "Doanh thu",

    )

    val draw = arrayListOf<Int>(
        R.drawable.baseline_category_24,
        R.drawable.baseline_egg_24,
        R.drawable.baseline_apps_24,
        R.drawable.baseline_border_all_24,
        R.drawable.baseline_attach_money_24,

    )


    Column(verticalArrangement = Arrangement.SpaceAround) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Text(
                text = "HOME",
                fontSize = 32.sp,
                color = Violet
            )
        }
        for (i in 0..3) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                for (j in 0..1) {
                    val id = i * 2 + j
                    if (id > 4) {
                        break
                    }
                    ItemMenu(myDrawble = draw[id], name = names[id])
                }
            }
        }
    }
}