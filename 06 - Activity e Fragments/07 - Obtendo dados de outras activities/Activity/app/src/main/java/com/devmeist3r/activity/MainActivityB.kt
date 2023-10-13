package com.devmeist3r.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.devmeist3r.activity.databinding.ActivityMainBBinding

class MainActivityB : AppCompatActivity() {

    private lateinit var binding: ActivityMainBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtra()
    }

    private fun getExtra() {
        // fazer um "casting"
        if (intent.hasExtra("user")) {
            val user = intent.getSerializableExtra("user") as User

            Log.i("INFOTESTE", "getExtra: ${user.name}")
            Log.i("INFOTESTE", "getExtra: ${user.age}")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent()
            intent.putExtra("user", User("Lucas", 30))
            setResult(RESULT_OK, intent)
            finish()
        }
        return true
    }
}