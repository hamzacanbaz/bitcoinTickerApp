package com.canbazdev.bitcointicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.canbazdev.bitcointickerapp.common.extensions.gone
import com.canbazdev.bitcointickerapp.common.extensions.visible
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        setUpBottomNavigationView(navHostFragment.navController, binding.bottomNav)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.favoritesFragment) {
                binding.bottomNav.visible()
            } else {
                binding.bottomNav.gone()
            }
        }


    }

    private fun setUpBottomNavigationView(
        navController: NavController,
        bottomNavigationView: BottomNavigationView,
    ) {

        bottomNavigationView.setupWithNavController(navController)
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            if(item.itemId != binding.bottomNav.selectedItemId)
                NavigationUI.onNavDestinationSelected(item, navController)
            true
        }
    }

}

