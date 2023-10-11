package com.devmeist3r.aswitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.aswitch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        setStatus()

        bindind.switchMaterial.setOnCheckedChangeListener { _, check ->
            setStatus()
        }

    }

    private fun setStatus() {
        bindind.textStatus.text = if (bindind.switchMaterial.isChecked) {
            "Ativo"
        } else {
            "Inativo"
        }
    }
}