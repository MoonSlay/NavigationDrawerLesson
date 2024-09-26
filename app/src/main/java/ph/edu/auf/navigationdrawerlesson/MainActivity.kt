package ph.edu.auf.navigationdrawerlesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ph.edu.auf.navigationdrawerlesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_more)

        // Setup NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController

        // Handle Navigation Drawer Item Clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fave_quotes -> navController.navigate(R.id.nav_fave_quotes)
                R.id.nav_love_quotes -> navController.navigate(R.id.nav_love_quotes)
                R.id.nav_quotes_day -> navController.navigate(R.id.nav_quotes_day)
                R.id.nav_funny_quotes -> navController.navigate(R.id.nav_funny_quotes)
                R.id.nav_inspirational_quotes -> navController.navigate(R.id.nav_inspirational_quotes)
                R.id.nav_motivational_quotes -> navController.navigate(R.id.nav_motivational_quotes)
                R.id.nav_historical_quotes -> navController.navigate(R.id.nav_historical_quotes)
                R.id.nav_travel_quotes -> navController.navigate(R.id.nav_travel_quotes)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Load default fragment
        if (savedInstanceState == null) {
            navController.navigate(R.id.nav_quotes_day)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}


