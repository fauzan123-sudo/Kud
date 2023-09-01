package com.example.kud.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kud.R
import com.example.kud.databinding.ActivityMainBinding
import com.example.kud.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var token: TokenManager
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkToken()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

//        Bottom Navigation
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            when (nd.id) {
                R.id.checkOutFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.userAddressFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                else -> {
                    binding.bottomNav.visibility = View.VISIBLE

                }
            }
        }

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.berandaFragment
//        ).build()
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        setupActionBarWithNavController(navController)
    }

    private fun checkToken() {
        if (token.getToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}