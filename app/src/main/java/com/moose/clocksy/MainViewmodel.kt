package com.moose.clocksy

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.*

class MainViewmodel: ViewModel() {

    private val cal = Calendar.getInstance()

    private val _time: MutableLiveData<Time> = MutableLiveData()
    val time: LiveData<Time> = _time

    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> = _date

    fun startTime(){
        viewModelScope.launch {
            while (true) {
                Log.d("Clocksy", "startTime: still executing....");
                val sec = cal.get(Calendar.SECOND)
                val mins = cal.get(Calendar.MINUTE)
                val hrs = cal.get(Calendar.HOUR_OF_DAY)

                _time.value = Time( hrs, mins, sec)
                delay(1000)
            }
        }
    }

    fun getDate() {

    }
}