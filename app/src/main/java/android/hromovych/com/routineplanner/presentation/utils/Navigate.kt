package android.hromovych.com.routineplanner.presentation.utils

import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.safeNavigate(@IdRes resId: Int): Boolean = try {
    navigate(resId)
    true
} catch (e: IllegalArgumentException) {
    false
}