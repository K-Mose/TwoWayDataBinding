package com.example.twowaydatabinding.texting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextActivityViewModel : ViewModel() {

    val userName = MutableLiveData<String>()

    init {
        userName.value = "Mose"
    }
}