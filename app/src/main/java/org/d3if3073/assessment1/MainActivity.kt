package org.d3if3073.assessment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var kembali: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kembali = findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, kembali)
    }

    override fun onSupportNavigateUp(): Boolean {
        return kembali.navigateUp()
    }
}
