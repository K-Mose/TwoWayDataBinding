package com.example.twowaydatabinding.number

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class NumberActivityViewModelFactory(
    private val startingTotal: Int,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NumberActivityViewModel::class.java)) {
            return NumberActivityViewModel(startingTotal, context) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}