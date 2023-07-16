package com.example.strelyukhin_lesson_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.strelyukhin_lesson_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewPadding.text = "Padding and background"
        binding.textViewBold.text = "Bold text"
        binding.textViewGreen.text = "Color green"
        binding.textViewRed.text = "Color red"

    }
}