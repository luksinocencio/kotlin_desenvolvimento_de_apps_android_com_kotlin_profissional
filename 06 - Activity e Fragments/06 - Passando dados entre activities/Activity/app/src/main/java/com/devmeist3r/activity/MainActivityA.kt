package com.devmeist3r.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.activity.databinding.ActivityMainABinding

class MainActivityA : AppCompatActivity() {
    private lateinit var binding: ActivityMainABinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainABinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivityB::class.java)
            intent.putExtra("user", User("Lucas", 30))
            startActivity(intent)
        }
    }
}