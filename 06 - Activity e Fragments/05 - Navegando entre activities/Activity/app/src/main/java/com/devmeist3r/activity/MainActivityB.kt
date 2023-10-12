package com.devmeist3r.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.activity.databinding.ActivityMainABinding
import com.devmeist3r.activity.databinding.ActivityMainBBinding

class MainActivityB : AppCompatActivity() {

    private lateinit var binding: ActivityMainBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}