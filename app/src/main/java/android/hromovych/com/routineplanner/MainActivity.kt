package android.hromovych.com.routineplanner

import android.hromovych.com.routineplanner.doings.DoingsFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DoingsFragment.newInstance())
            .commit()
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