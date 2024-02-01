package com.amineaytac.biblictora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.amineaytac.biblictora.databinding.ActivityMainBinding
import com.amineaytac.biblictora.util.gone
import com.amineaytac.biblictora.util.visible
import com.amineaytc.biblictora.util.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)

        binding.bottomNavigationView.itemActiveIndicatorColor=null

        val noBottomNavigationIds = listOf(
            R.id.homeFragment,
            R.id.myBooksFragment,
            R.id.favoriteFragment
        )

        navController.addOnDestinationChangedListener { _, destination,_  ->
            if (noBottomNavigationIds.contains(destination.id)) {
                binding.bottomNavigationView.visible()
            } else {
                binding.bottomNavigationView.gone()
            }
        }
    }
}