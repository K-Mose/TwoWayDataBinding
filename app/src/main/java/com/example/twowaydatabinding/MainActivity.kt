package com.example.twowaydatabinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.twowaydatabinding.databinding.ActivityMainBinding
import com.example.twowaydatabinding.number.NumberActivity
import com.example.twowaydatabinding.texting.TextActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding // viewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.apply {
            textActivity.setOnClickListener {
                val intent = Intent(this@MainActivity, TextActivity::class.java)
                startActivity(intent)
            }
            numberActivity.setOnClickListener {
                val intent = Intent(this@MainActivity, NumberActivity::class.java)
                startActivity(intent)
            }
        }
    }
}