package android.hromovych.com.routineplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        delegate.localNightMode = SharedPreferencesHelper(this).dayNightMode
//        setTheme(SharedPreferencesHelper(this).themeId)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        val statusColor = MaterialColors.getColor(this, android.R.attr.statusBarColor, getColor(R.color.status_bar))
//        window.statusBarColor = statusColor
}

}