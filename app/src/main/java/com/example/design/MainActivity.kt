package com.example.design

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup bottom navigation
        setupBottomNavigation()

        // Try a different approach to find the clickable views
        setupClickables()
    }

    private fun setupClickables() {
        // Try to find views by ID directly - use any ID that might be in your layout
        // These are common IDs you might be using - adjust based on your actual layout

        try {
            // Let's try different possible ID naming patterns

            // Try option 1: Direct IDs
            setupClickListener(R.id.pollution_card, PollutionActivity::class.java, "Pollution")
            setupClickListener(R.id.aqi_card, AQIActivity::class.java, "AQI")
            setupClickListener(R.id.deforestation_card, DeforestationActivity::class.java, "Deforestation")

//            // Try option 2: Card IDs with different naming convention
//            setupClickListener(R.id.card_pollution, PollutionActivity::class.java, "Pollution")
//            setupClickListener(R.id.card_aqi, AQIActivity::class.java, "AQI")
//            setupClickListener(R.id.card_deforestation, DeforestationActivity::class.java, "Deforestation")
//
//            // Try option 3: Container IDs
//            setupClickListener(R.id.pollution_container, PollutionActivity::class.java, "Pollution")
//            setupClickListener(R.id.aqi_container, AQIActivity::class.java, "AQI")
//            setupClickListener(R.id.deforestation_container, DeforestationActivity::class.java, "Deforestation")
//
//            // Try option 4: Button IDs
//            setupClickListener(R.id.btn_pollution, PollutionActivity::class.java, "Pollution")
//            setupClickListener(R.id.btn_aqi, AQIActivity::class.java, "AQI")
//            setupClickListener(R.id.btn_deforestation, DeforestationActivity::class.java, "Deforestation")

            Log.d(TAG, "Finished setting up clickable items")

        } catch (e: Exception) {
            Log.e(TAG, "Error in setupClickables", e)
            Toast.makeText(this, "Error setting up UI interactions", Toast.LENGTH_SHORT).show()
        }
    }

    private fun <T> setupClickListener(viewId: Int, activityClass: Class<T>, activityName: String) {
        try {
            findViewById<android.view.View>(viewId)?.apply {
                setOnClickListener {
                    try {
                        val intent = Intent(this@MainActivity, activityClass)
                        startActivity(intent)
                        Log.d(TAG, "Starting $activityName activity")
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to start $activityName activity", e)
                        Toast.makeText(this@MainActivity, "Could not open $activityName", Toast.LENGTH_SHORT).show()
                    }
                }
                Log.d(TAG, "Successfully set up click listener for $activityName (ID: $viewId)")
            }
        } catch (e: Exception) {
            // Just log and continue - this is expected for IDs that don't exist
            Log.d(TAG, "View with ID $viewId for $activityName not found")
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the correct item as selected
        bottomNavigationView.selectedItemId = R.id.nav_home

        // Set listener for navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    overridePendingTransition(0, 0) // For smooth transition
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    overridePendingTransition(0, 0) // For smooth transition
                    true
                }
                else -> false
            }
        }
    }
}