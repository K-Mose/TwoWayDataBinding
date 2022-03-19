package com.example.twowaydatabinding.number

import com.example.twowaydatabinding.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.twowaydatabinding.databinding.ActivityNumberBinding

class NumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNumberBinding
    private lateinit var viewModel: NumberActivityViewModel
    private lateinit var viewModelFactory: NumberActivityViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_number)
        viewModelFactory = NumberActivityViewModelFactory(318, this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NumberActivityViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}