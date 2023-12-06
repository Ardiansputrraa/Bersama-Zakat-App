package com.example.bersamazakatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.bersamazakatapp.ui.about_page.AboutFragment
import com.example.bersamazakatapp.ui.home_page.HomeFragment
import com.example.bersamazakatapp.ui.panduan_aplikasi_page.PanduanAplikasiFragment
import com.example.bersamazakatapp.ui.zakat_emas.ZakatEmasFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        this.supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        bottomNav = findViewById(R.id.bottomNavigation)

        bottomNav.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.panduanAplikasiFragment,
            R.id.aboutFragment
        ).build()
        //Log.i("TAG", "onCreate: ${appBarConfiguration}")

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> hideBottomNav(true)
                R.id.splashSecondFragment -> hideBottomNav(true)
                R.id.zakatEmasFragment -> hideBottomNav(true)
                R.id.zakatFitrahFragment -> hideBottomNav(true)
                R.id.zakatPerikananFragment -> hideBottomNav(true)
                R.id.zakatPertanianFragment -> hideBottomNav(true)
                R.id.zakatProfesiFragment -> hideBottomNav(true)
                R.id.zakatPeternakanFragment -> hideBottomNav(true)
                R.id.homeFragment -> hideBottomNav(false)
                R.id.panduanAplikasiFragment -> hideBottomNav(false)
                R.id.aboutFragment -> hideBottomNav(false)
                else -> hideBottomNav(false)
            }
        }
    }

    private fun hideBottomNav(hide: Boolean) {
        if (hide) {
            bottomNav.visibility = View.GONE
        } else {
            bottomNav.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}