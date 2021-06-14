package android.hromovych.com.routineplanner

import android.hromovych.com.routineplanner.databinding.ActivityMainBinding
import android.hromovych.com.routineplanner.presentation.utils.safeNavigate
import android.hromovych.com.routineplanner.presentation.utils.showDayNightDialog
import android.hromovych.com.routineplanner.presentation.utils.showInfoDialog
import android.hromovych.com.routineplanner.utils.SharedPreferencesHelper
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val drawerLayout: DrawerLayout by lazy {
        binding.drawerLayout
    }
    private val navigationView: NavigationView by lazy {
        binding.navView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        delegate.localNightMode = SharedPreferencesHelper(this).dayNightMode
//        setTheme(SharedPreferencesHelper(this).themeId)
        setContentView(binding.root)

//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        val statusColor = MaterialColors.getColor(this, android.R.attr.statusBarColor, getColor(R.color.status_bar))
//        window.statusBarColor = statusColor

        setupNavigation()
    }

    //
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //
    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.doingsFragment, R.id.templatesFragment, R.id.weekdayDoingsFragment),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
//        navigationView.setNavigationItemSelectedListener(this)
        initMenuItems()
    }

    private fun initMenuItems() {
        navigationView.setNavigationItemClickedListener()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (navController.safeNavigate(item.itemId)) {
            drawerLayout.closeDrawers()

            return true
        }
        when (item.itemId) {
            R.id.dayNight -> {
                showInfoDialog(R.string.attention, R.string.not_implemented_yet)
            }
        }
        return true
    }

    private fun NavigationView.setNavigationItemClickedListener() {
        val items = menu.children.flatMap {
            if (it.hasSubMenu())
                it.subMenu.children.toList()
            else
                listOf(it)
        }
        items.forEach { item: MenuItem ->
            when (item.itemId) {
                R.id.dayNight -> {
                    item.setOnMenuItemClickListener {
                        this@MainActivity.showDayNightDialog()
                        true
                    }
                }
            }
        }
    }
}
