package com.moose.clocksy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moose.clocksy.ui.theme.ClocksyTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClocksyTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Timer()
                    }
                }
            }
        }
    }

    @Composable
    fun Timer() {
        var time by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }

        LaunchedEffect(0) {
            while (true) {
                val cal = Calendar.getInstance()
                val formatted = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(cal.time)

                time = SimpleDateFormat("HH : mm : ss", Locale.ROOT).format(cal.time)
                date = formatted.replaceRange(4..5, formatted.substring(4,6).toInt().toOrdinal())
                delay(1000)
            }
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(time, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(10.dp))
            Text(date)
        }
    }

    private fun Int.toOrdinal(): String {
        val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        val m = this % 100
        return "$this${suffix[if (m in 4..20) 0 else m % 10]}"
    }
}

