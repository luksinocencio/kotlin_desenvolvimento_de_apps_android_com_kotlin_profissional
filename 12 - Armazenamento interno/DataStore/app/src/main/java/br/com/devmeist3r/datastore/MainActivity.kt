package br.com.devmeist3r.datastore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.devmeist3r.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager(this)

        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val age = binding.editAge.text.toString().toInt()
            val authenticated = binding.checkBox.isChecked

//            lifecycleScope.launch {
//                userManager.saveUserData(name = name, age = age, authenticated = authenticated)
//            }
            lifecycleScope.launch {
                val user = userManager.getUserData()
                Log.i("INFOTESTE", "name: ${user.name}")
                Log.i("INFOTESTE", "age: ${user.age}")
                Log.i("INFOTESTE", "authenticated: ${user.authenticated}")
            }
        }
    }
}