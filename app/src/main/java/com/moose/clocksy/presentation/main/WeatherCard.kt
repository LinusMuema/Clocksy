package com.moose.clocksy.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.BaselineShift.Companion.Superscript
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moose.clocksy.R
import kotlin.math.round

@Composable
fun WeatherCard() {
    val viewmodel: MainViewmodel = viewModel()
    val weather by viewmodel.weather.observeAsState()
    val superscript = SpanStyle( baselineShift = Superscript, fontSize = 10.sp)

    weather?.let {
        Column(modifier = Modifier.padding(25.dp), horizontalAlignment = CenterHorizontally) {
            Text(it.name, modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceEvenly) {
                Row {
                    Image(
                        contentDescription = "clouds icon",
                        modifier = Modifier.size(48.dp).padding(10.dp),
                        painter = painterResource(id = R.drawable.clouds)
                    )
                    Column {
                        Text("Clouds", style = typography.h6)
                        Text("${it.clouds.all} %")
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth(.3f))
                Row {
                    Image(
                        contentDescription = "temperature icon",
                        modifier = Modifier.size(48.dp).padding(10.dp),
                        painter = painterResource(id = R.drawable.temperature),
                    )
                    Column {
                        Text("Temp", style = typography.h6)
                        Text(text = buildAnnotatedString {
                            append("${(it.main.temp - 273.15).round(2)} ")
                            withStyle(superscript){ append("o") }
                            append("C")
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceEvenly) {
                Row {
                    Image(
                        contentDescription = "humidity icon",
                        modifier = Modifier.size(48.dp).padding(10.dp),
                        painter = painterResource(id = R.drawable.humidity)
                    )
                    Column {
                        Text("Humidity", style = typography.h6)
                        Text("${it.main.humidity} %")
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth(.3f))
                Row {
                    Image(
                        contentDescription = "wind icon",
                        modifier = Modifier.size(48.dp).padding(10.dp),
                        painter = painterResource(id = R.drawable.wind)
                    )
                    Column {
                        Text("Wind", style = typography.h6)
                        Text("${it.wind.speed} ms/sec")
                    }
                }
            }
        }
    }
}

private fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
