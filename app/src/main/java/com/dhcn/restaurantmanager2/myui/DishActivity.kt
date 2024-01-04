package com.dhcn.restaurantmanager2.myui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dhcn.restaurantmanager2.App
import com.dhcn.restaurantmanager2.R
import com.dhcn.restaurantmanager2.calc.DishAdapter
import com.dhcn.restaurantmanager2.event.EventListener
import com.dhcn.restaurantmanager2.model.DishFinal
import com.dhcn.restaurantmanager2.ui.theme.Orange
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.ui.theme.Violet01
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils
import kotlin.reflect.KClass

class DishActivity : ComponentActivity(), EventListener {

    val allDish = mutableStateOf(Utils.getAppModule().getAllDishOrDink(Utils.getAppModule().isDish))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.getAppModule().eventManager.add(this)
        setContent {
            RestaurantManager2Theme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowDish()
                }
            }
        }
    }


    @Composable
    fun ShowDish() {

        val _name = if (Utils.getAppModule().isDish == Constant.DISH) {
            "Món ăn"
        } else {
            "Đồ uống"
        }
        Column {
            TopNav2(_name, EditDishActivity::class.java as Class<Any>)
            LazyColumn() {
                item {
                    for (item in allDish.value) {
                        ItemDish(item = item)
                    }
                }

            }
        }
    }

    override fun handleEvent(ev: Int) {
        when (ev) {
            Constant.UPDATE_DATA -> {
                allDish.value = Utils.getAppModule().getAllDishOrDink(Utils.getAppModule().isDish)
            }
        }
    }

}


@Composable
fun TopNav(name: String) {
    val activity = (LocalContext.current as Activity)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        val backImg = painterResource(id = R.drawable.baseline_arrow_back_24)
        Image(
            painter = backImg,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    activity.finish()
                }
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Text(
                text = name,
                fontSize = 24.sp,
                color = Violet01
            )
        }
    }
}

@Composable
fun TopNav2(name: String, nextActivity: Class<Any>) {
    val activity = (LocalContext.current as Activity)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        val backImg = painterResource(id = R.drawable.baseline_arrow_back_24)
        val addImg = painterResource(id = R.drawable.baseline_add_24)
        Image(
            painter = backImg,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    activity.finish()
                }
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = name,
                fontSize = 24.sp,
                color = Violet01
            )
        }
        Image(
            painter = addImg,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    val intent = Intent(activity, nextActivity)
                    Utils.setIsAddDish(true)
                    Utils.getAppModule().isAddMenu = true
                    Utils.getAppModule().isAddTable = true
                    Utils.getAppModule().isAddOrder = true
                    Utils.getAppModule().setSelect.clear()
                    activity.startActivity(intent)
                }
        )
    }
}


@Composable
fun ItemDish(item: DishAdapter) {
    val activity = (LocalContext.current as Activity)
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(
                top = 10.dp,
                start = 10.dp
            )
            .clickable {
                val intent = Intent(activity, EditDishActivity::class.java)
                Utils.setIsAddDish(false)
                Utils.getAppModule().currDish = item.dish
                activity.startActivity(intent)
            }

    ) {


        val uri = if (item.getImage().isNullOrBlank()) {
            null
        } else {
            Uri.parse(item.getImage())
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

        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = item.getName(),
                fontSize = 20.sp,
                color = Orange
            )
            Text(text = item.getDescription())
            Text(text = item.getPrice().toString())
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clickable {
                    Utils.getAppModule().deleteDish(item.dish)
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



