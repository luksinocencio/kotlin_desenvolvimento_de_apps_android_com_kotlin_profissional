package com.devmeist3r.progressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.devmeist3r.progressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatus()

        binding.toggleButton.setOnCheckedChangeListener { _, _ ->
            setStatus()
        }
    }

    private fun setStatus() {
        binding.progressBar.isVisible = binding.toggleButton.isChecked
    }
}