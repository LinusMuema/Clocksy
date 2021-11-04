package com.moose.clocksy

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewmodel: ViewModel() {

    fun getData(location: Location) {
        viewModelScope.launch {
            val weather = NetworkService.getData(location.latitude, location.longitude)
            Log.d("Weather", "getData: $weather")
        }
    }
}