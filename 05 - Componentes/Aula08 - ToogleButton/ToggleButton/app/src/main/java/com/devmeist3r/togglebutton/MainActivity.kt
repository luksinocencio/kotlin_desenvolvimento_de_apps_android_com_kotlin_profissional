package com.devmeist3r.togglebutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.togglebutton.databinding.ActivityMainBinding

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
        binding.textStatus.text = if(binding.toggleButton.isChecked) {
            "Ligado"
        } else {
            "Desligado"
        }
    }
}