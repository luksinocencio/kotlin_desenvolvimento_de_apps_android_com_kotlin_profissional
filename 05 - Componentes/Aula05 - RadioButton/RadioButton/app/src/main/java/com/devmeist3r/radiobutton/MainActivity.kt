package com.devmeist3r.radiobutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devmeist3r.radiobutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var bindind: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        
        bindind.radioGroup.setOnCheckedChangeListener { _, id ->
            when(id) {
                R.id.rbOpcao1 -> {
                    Log.i("INFOTESTE", "Opção 1")
                }
                R.id.rbOpcao2 -> {
                    Log.i("INFOTESTE", "Opção 2")
                }
                else -> {
                    Log.i("INFOTESTE", "Opção 3")
                }
            }
        }
    }
}