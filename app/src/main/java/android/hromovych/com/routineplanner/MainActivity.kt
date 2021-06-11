package android.hromovych.com.routineplanner

import android.hromovych.com.routineplanner.presentation.utils.showInfoDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController

    private val drawerLayout: DrawerLayout by lazy {
        findViewById(R.id.drawer_layout)
    }
    private val navigationView: NavigationView by lazy {
        findViewById(R.id.nav_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        delegate.localNightMode = SharedPreferencesHelper(this).dayNightMode
//        setTheme(SharedPreferencesHelper(this).themeId)
        setContentView(R.layout.activity_main)

//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        val statusColor = MaterialColors.getColor(this, android.R.attr.statusBarColor, getColor(R.color.status_bar))
//        window.statusBarColor = statusColor

        setupNavigation()
    }

    //
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    //
    private fun setupNavigation() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navigationView.setupWithNavController(navController)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dayNight -> {
                showInfoDialog(R.string.attention, R.string.not_implemented_yet)
            }
        }
        return true
    }
}