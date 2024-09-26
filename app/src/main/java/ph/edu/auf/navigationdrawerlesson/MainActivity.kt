package ph.edu.auf.navigationdrawerlesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ph.edu.auf.navigationdrawerlesson.databinding.ActivityMainBinding
import ph.edu.auf.navigationdrawerlesson.fragments.FaveQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.FunnyQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.HistoricalQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.InspirationalQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.LoveQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.MotivationalQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.QuotesOfTheDayFragment
import ph.edu.auf.navigationdrawerlesson.fragments.TravelQuotesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_more)

        // Handle Navigation Drawer Item Clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fave_quotes -> openFragment(FaveQuotesFragment())
                R.id.nav_love_quotes -> openFragment(LoveQuotesFragment())
                R.id.nav_quotes_day -> openFragment(QuotesOfTheDayFragment())
                R.id.nav_funny_quotes -> openFragment(FunnyQuotesFragment())
                R.id.nav_inspirational_quotes -> openFragment(InspirationalQuotesFragment())
                R.id.nav_motivational_quotes -> openFragment(MotivationalQuotesFragment())
                R.id.nav_historical_quotes -> openFragment(HistoricalQuotesFragment())
                R.id.nav_travel_quotes -> openFragment(TravelQuotesFragment())
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Load default fragment
        if (savedInstanceState == null) {
            openFragment(FaveQuotesFragment())
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment) // Ensure you have a correct FragmentContainerView or FrameLayout
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}

