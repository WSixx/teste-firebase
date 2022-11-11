package com.example.firebasetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasetest.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : AppCompatActivity() {

    companion object {
        const val MINIMUM_FETCH_INTERVAL = 3600
        const val STRING_REMOTE_KEY = "super_string"
    }

    private var _binding: ActivityMainBinding?  = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings { MINIMUM_FETCH_INTERVAL }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.tvMain.text = remoteConfig[STRING_REMOTE_KEY].asString()
                } else {
                    Toast.makeText(this, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}