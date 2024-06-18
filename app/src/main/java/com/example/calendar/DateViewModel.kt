package com.example.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DateViewModel : ViewModel() {

    private val _dateStateFlow = MutableStateFlow<String>("Select Date")
    val dateStateFlow = _dateStateFlow.asStateFlow()

    fun updateDate(newDate: String){
        _dateStateFlow.value = newDate
    }

//    fun saveDate(key: String, value:String) {
//      savedStateHandle.set(key, value)
//    }
//
//    fun retrieveDate(key: String): String {
//      return savedStateHandle.get<String>(key)?: "Select Date"
//    }


}