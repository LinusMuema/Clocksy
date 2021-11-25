package com.moose.clocksy.presentation.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.view.WindowCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.moose.clocksy.ui.theme.ClocksyTheme
import java.util.*
import kotlin.time.ExperimentalTime


@ExperimentalTime
class MainActivity : ComponentActivity() {

    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ClocksyTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally) {
                        Header()
                        WeatherCard()
                    }
                }
            }
        }

        // start watching the location
        getLocation()
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
}

