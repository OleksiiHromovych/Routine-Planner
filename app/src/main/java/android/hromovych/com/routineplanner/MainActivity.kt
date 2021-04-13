package android.hromovych.com.routineplanner

import android.hromovych.com.routineplanner.utils.SharedPreferencesHelper
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.MaterialColors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.localNightMode = SharedPreferencesHelper(this).dayNightMode
//        SharedPreferencesHelper(this).themeId = R.style.Theme_RoutinePlanner_Vasyl
        setTheme(SharedPreferencesHelper(this).themeId)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val statusColor = MaterialColors.getColor(this, android.R.attr.statusBarColor, getColor(R.color.status_bar))
        window.statusBarColor = statusColor
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, DoingsFragment.newInstance())
//            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            setResult(RESULT_OK)
            finish()
        } else {
            super.onBackPressed()
        }
    }
}