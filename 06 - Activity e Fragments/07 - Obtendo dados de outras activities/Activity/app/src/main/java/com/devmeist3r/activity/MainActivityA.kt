package com.devmeist3r.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.devmeist3r.activity.databinding.ActivityMainABinding

const val TAG = "INFOTESTE"

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
            resultLauncher.launch(Intent(this, MainActivityB::class.java))
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        if (resultCode == RESULT_OK) {
            if (data != null) {
                if(data.hasExtra("user")) {
                    val user = data.getSerializableExtra("user") as User
                    Log.i(TAG, "resultLauncher: ${user.name}")
                    Log.i(TAG, "resultLauncher: ${user.age}")
                }
            }
        }
    }
}