package com.dhcn.restaurantmanager2.myui

import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dhcn.restaurantmanager2.roomdb.Dish
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.ImageUtils
import com.dhcn.restaurantmanager2.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.File

class EditDishActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantManager2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EditDishCompose()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditDishCompose() {
    var dish = if (Utils.getIsAddDish()) {
        Constant.prototypeDish
    } else {
        Utils.getAppModule().currDish
    }

    val name = if (Utils.getIsAddDish()) {
        "Thêm"
    } else {
        "Cập nhật"
    }


    val uri = if (Utils.getIsAddDish() || Utils.getAppModule().currDish.image.isEmpty()) {
        null
    } else {
        Uri.parse(Utils.getAppModule().currDish.image)
    }

    var imageUri = remember {
        mutableStateOf<Uri?>(uri)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }


    val appContext = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
    ) {


        var _name = remember {
            mutableStateOf(dish.name)
        }

        TopNav(name = name)

        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "select image")
        }

        AsyncImage(
            model = imageUri.value,
            contentDescription = null,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )

        TextField(
            value = _name.value,
            onValueChange = { _name.value = it },
            label = { Text(text = "Tên món ăn") },
            placeholder = { Text(text = "Phở") }
        )


        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        var description = remember {
            mutableStateOf(dish.description)
        }
        TextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text(text = "Mô tả") },
            placeholder = { Text(text = "Phở") }
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        var price = remember {
            mutableStateOf(dish.price.toString())
        }
        TextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = { Text(text = "Giá") },
            placeholder = { Text(text = "0") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        val opt = if (dish.type == Constant.DISH) {
            0
        } else {
            1
        }
        val radioOptions = listOf("Món ăn", "Đồ uống")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[opt]) }
        Row {
            radioOptions.forEach { text ->
                Column(
                    Modifier
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Button(
                onClick = {
                    if (Utils.getIsAddDish()) {
                        dish.id = System.currentTimeMillis()
                    }


                    dish.name = _name.value
                    if (imageUri.value != null && imageUri.value.toString().isNotBlank()) {


                        runBlocking(Dispatchers.IO) {
                            val f = ImageUtils.saveImageFromContentUri(appContext, imageUri.value!!)!!.absolutePath
                            Log.i("IMG", f)
                            dish.image = f
                        }



                    }
                    dish.description = description.value
                    dish.price = price.value.toLong()
                    dish.type = if (selectedOption == "Món ăn") {
                        Constant.DISH
                    } else {
                        Constant.DRINK
                    }
                    if (TextUtils.isEmpty(dish.name) || TextUtils.isEmpty(dish.description) || dish.price < 0) {
                        Toast.makeText(appContext, "$name thất bại!", Toast.LENGTH_SHORT).show()
                    } else {
                        Utils.getAppModule().database.dao().insertDish(dish)
                        Toast.makeText(appContext, "$name thành công!", Toast.LENGTH_SHORT).show()
                    }
                    Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)
                },
            ) {
                Text(text = name)
            }
        }

    }
}