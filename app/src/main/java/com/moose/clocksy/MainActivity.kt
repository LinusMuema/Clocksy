package com.moose.clocksy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moose.clocksy.ui.theme.ClocksyTheme
import kotlinx.coroutines.delay
import java.sql.Time
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

                time = SimpleDateFormat("HH : mm : ss", Locale.ROOT).format(cal.time)
                date = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(cal.time)
                delay(1000)
            }
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(time, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(10.dp))
            Text(date)
        }
    }
}

