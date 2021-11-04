package com.moose.clocksy

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.moose.clocksy.ui.theme.ClocksyTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime


@ExperimentalTime
class MainActivity : ComponentActivity() {

    private val viewmodel: MainViewmodel by viewModels()

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

        // start watching the location
        getLocation()
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
                date = formatted.replaceRange(4..5, formatted.substring(4, 6).toInt().toOrdinal())
                delay(1000)
            }
        }

        Column(horizontalAlignment = CenterHorizontally) {
            Text(time, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(10.dp))
            Text(date)
        }
    }

    private fun getLocation() {
        val grantedCoarse = checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        val grantedFine = checkSelfPermission(this, ACCESS_FINE_LOCATION)
        if ( grantedCoarse != PERMISSION_GRANTED &&  grantedFine != PERMISSION_GRANTED) {
            val permissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            requestPermissions(this, permissions, 0)
        }

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                viewmodel.getData(result.lastLocation)
            }
        }

        val client = LocationServices.getFusedLocationProviderClient(this)
        val request = LocationRequest.create().apply {
            interval = 50000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        client.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    private fun Int.toOrdinal(): String {
        val suffix = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        val m = this % 100
        return "$this${suffix[if (m in 4..20) 0 else m % 10]}"
    }
}

