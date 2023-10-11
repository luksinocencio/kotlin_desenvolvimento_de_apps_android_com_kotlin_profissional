package com.devmeist3r.textview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devmeist3r.textview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val curso = "Curso Kotlin"
        bindind.textView.text = curso
    }
}