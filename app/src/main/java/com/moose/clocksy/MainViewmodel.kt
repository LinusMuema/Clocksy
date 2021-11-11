package com.moose.clocksy

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewmodel: ViewModel() {

    private val _weather: MutableLiveData<Weather> = MutableLiveData()
    val weather: LiveData<Weather> = _weather

    fun getData(location: Location) {
        viewModelScope.launch {
            _weather.value  = NetworkService.getData(location.latitude, location.longitude)
        }
    }
}