package com.example.dz1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberScreen()
        }
    }
}
@Composable
fun NumberScreen() {
    var count by rememberSaveable {
        mutableIntStateOf(0)
    }
    var select by rememberSaveable{
        mutableStateOf<Int?>(null)
    }
    val orientation = LocalConfiguration.current.orientation
    val columns = if (
        orientation == Configuration.ORIENTATION_PORTRAIT
    ) {
        integerResource(R.integer.portrait_columns)
    } else {
        integerResource(R.integer.landscape_columns)
    }
    val spacing = dimensionResource(R.dimen.spacing)
    val rows = (count + columns - 1) / columns
    if (select != null){
        OneNumberScreen(select!!){
            select = null
        }
    } else {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f).padding(spacing)
            ) {
                items(rows) { row ->
                    Row(Modifier.fillMaxWidth()) {
                        repeat(columns) { column ->
                            val index = row * columns + column
                            Box(Modifier.weight(1f)) {
                                if (index < count) {
                                    NumberCard(index){
                                        select = index
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {count++},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing)
            ) {
                Text(stringResource(R.string.add))
            }
        }
    }
}
@Composable
fun NumberCard(index: Int, onClick: () -> Unit) {
    val spacing = dimensionResource(R.dimen.spacing)
    val color = if (index % 2 == 0) {
        colorResource(R.color.red)
    } else {
        colorResource(R.color.blue)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing)
            .aspectRatio(1f)
            .background(color)
            .clickable{onClick()},
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = index.toString(),
            color = colorResource(R.color.white)
        )
    }
}
@Composable
fun OneNumberScreen(index: Int, back: () -> Unit){
    val spacing = dimensionResource(R.dimen.spacing)
    val color = if (index % 2 == 0) {
        colorResource(R.color.red)
    } else {
        colorResource(R.color.blue)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = index.toString(),
            color = colorResource(R.color.white)
        )
        Button(
            onClick = {back()},
            modifier = Modifier.align(Alignment.BottomCenter).padding(spacing),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.black))
        ){
            Text(
                stringResource(R.string.back),
                color = colorResource(R.color.white),
            )
        }
    }
}
