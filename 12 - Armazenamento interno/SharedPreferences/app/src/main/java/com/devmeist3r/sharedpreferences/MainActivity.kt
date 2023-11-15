package com.devmeist3r.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE)
        initlisteners()
    }

    private fun initlisteners() {
        binding.button.setOnClickListener { saveData() }

        binding.buttonRecuperar.setOnClickListener { getData() }
    }

    private fun saveData() {
        val name = binding.editName.text.toString()
        val lastName = binding.editLastName.text.toString()

        with (sharedPref.edit()) {
            putString("name", name)
            putString("lastName", lastName)
            apply()
        }
    }

    private fun getData() {
        val name = sharedPref.getString("name", "")
        val lastName = sharedPref.getString("lastName", "")

        binding.editName.setText(name)
        binding.editLastName.setText(lastName)
    }
}