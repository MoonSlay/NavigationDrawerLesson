package ph.edu.auf.navigationdrawerlesson

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ph.edu.auf.navigationdrawerlesson.databinding.ActivityMainBinding
import ph.edu.auf.navigationdrawerlesson.fragments.FaveQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.LoveQuotesFragment
import ph.edu.auf.navigationdrawerlesson.fragments.QuotesOfTheDayFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_more)
        // Make sure you have this icon in res

        // Handle Navigation Drawer Item Clicks
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fave_quotes -> {
                    openFragment(FaveQuotesFragment())
                }
                R.id.nav_love_quotes -> {
                    openFragment(LoveQuotesFragment())
                }
                R.id.nav_quotes_day -> {
                    openFragment(QuotesOfTheDayFragment())
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Load default fragment
        if (savedInstanceState == null) {
            openFragment(FaveQuotesFragment())
        }
    }

    // Helper function to open the fragment
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)  // Make sure to have a FrameLayout/FragmentContainerView in the layout
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}
