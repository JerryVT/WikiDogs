package com.example.wikidogs.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.wikidogs.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // For bringing back arrow on action bar and Implememnting back transition
        navController = Navigation.findNavController(this, R.id.fragment)
        // The fragment accessed here in R.id. is the fragment in activity_main.xml

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {           //have to override onSsupportNavigateUp function
        return NavigationUI.navigateUp(navController, null)
    }
}