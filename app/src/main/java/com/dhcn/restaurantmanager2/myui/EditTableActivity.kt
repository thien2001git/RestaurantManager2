package com.dhcn.restaurantmanager2.myui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.dhcn.restaurantmanager2.event.EventListener
import com.dhcn.restaurantmanager2.roomdb.MyTable
import com.dhcn.restaurantmanager2.ui.theme.RestaurantManager2Theme
import com.dhcn.restaurantmanager2.util.Constant
import com.dhcn.restaurantmanager2.util.Utils

class EditTableActivity : ComponentActivity(), EventListener {
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
                    EditTable()
                }
            }
        }
    }

    override fun handleEvent(ev: Int) {

    }

    @Composable
    fun EditTable() {
        val appContext = LocalContext.current
        val _name = if (Utils.getAppModule().isAddTable) {
            "Thêm"
        } else {
            "Cập nhật"
        }

        var myTable = Constant.prototypeTable
        if (!Utils.getAppModule().isAddTable) {
            myTable = Utils.getAppModule().currTable
        }
        val _num = remember {
            mutableStateOf(myTable.num.toString())
        }
        val _numPeople = remember {
            mutableStateOf(myTable.numPeople.toString())
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopNav(name = _name)

            TextField(
                value = _num.value,
                onValueChange = { _num.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Số bàn") },
                placeholder = { Text(text = "0") }
            )


            TextField(value = _numPeople.value, onValueChange = { _numPeople.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Người") },
                placeholder = { Text(text = "1") }
            )
            Button(onClick = {
                try {
                    if(Utils.getAppModule().isAddTable) {
                        myTable.id = System.currentTimeMillis()
                    }
                    myTable.num = _num.value.toInt()
                    myTable.numPeople = _numPeople.value.toInt()
                    Utils.getAppModule().database.dao().insertMyTable(myTable)
                    Utils.getAppModule().eventManager.notify(Constant.UPDATE_DATA)


                    Toast.makeText(appContext, "$_name thành công", Toast.LENGTH_SHORT).show()
                } catch (e: NumberFormatException) {
                    Toast.makeText(appContext, "$_name thất bại! Bạn hãy nhập số không có '.'!", Toast.LENGTH_SHORT).show()
                }

            }) {
                Text(text = _name)
            }
        }
    }
}