package com.example.bsm1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import java.security.Key


class MainActivity : AppCompatActivity() {
   private lateinit var navController: NavController

    companion object {
        fun newInstance() = MainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  navController = findNavController(R.id.navigation_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigation_view) as NavHostFragment
        navController = navHostFragment.navController
    }



}

