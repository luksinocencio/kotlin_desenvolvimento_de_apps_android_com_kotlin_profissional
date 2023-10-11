package com.devmeist3r.checkbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.checkbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatus()

        binding.checkBox.setOnCheckedChangeListener { _, check ->
            setStatus()
        }

    }

    fun setStatus() {
        binding.textStatus.text = if (binding.checkBox.isChecked) {
            "Ativo"
        } else {
            "Inativo"
        }
    }
}