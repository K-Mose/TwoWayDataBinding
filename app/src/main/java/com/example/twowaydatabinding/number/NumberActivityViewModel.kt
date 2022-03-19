package com.example.twowaydatabinding.number

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.NumberFormatException

class NumberActivityViewModel(startingTotal: Int, private val context: Context) : ViewModel() {
    private var total = MutableLiveData<Int>()
    val totalData: LiveData<Int>
    get() = total

    val inputText = MutableLiveData<String>()

    init {
        total.value = startingTotal
    }

    fun setTotal() {
        try {
            total.value = (total.value)?.plus(inputText.value!!.toInt())
            inputText.value = null
        } catch (e: NumberFormatException) {
            Toast.makeText(context,"Pleas input Only Number", Toast.LENGTH_LONG).show()
        }
    }

    fun subTotal() {
        try {
            total.value = (total.value)?.minus(inputText.value!!.toInt())
            inputText.value = null
        } catch (e: NumberFormatException) {
            Toast.makeText(context,"Pleas input Only Number", Toast.LENGTH_LONG).show()
        }
    }
}