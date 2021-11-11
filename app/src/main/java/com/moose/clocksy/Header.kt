package com.moose.clocksy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Header() {
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    val dark = MaterialTheme.colors.surface
    val colors = listOf(Color.Transparent, dark)
    val gradient = Brush.verticalGradient(colors = colors, 0f, 500f)

    LaunchedEffect(0) {
        while (true) {
            val cal = Calendar.getInstance()
            val formatted = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(cal.time)

            time = SimpleDateFormat("HH : mm : ss", Locale.ROOT).format(cal.time)
            date = formatted.replaceRange(4..5, formatted.substring(4, 6).toInt().toOrdinal())
            delay(1000)
        }
    }

    Box(modifier = Modifier.height(250.dp)) {
        Image(
            contentDescription = "banner",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.weather),
        )
        Box(modifier = Modifier.fillMaxSize().background(brush = gradient)) {
            Column(
                verticalArrangement = Bottom,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
            ) {
                Text(time, style = MaterialTheme.typography.h3)
                Spacer(modifier = Modifier.height(10.dp))
                Text(date)
            }
        }
    }
}

private fun Int.toOrdinal(): String {
    val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    val m = this % 100
    return "$this${suffix[if (m in 4..20) 0 else m % 10]}"
}