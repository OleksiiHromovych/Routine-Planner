package android.hromovych.com.routineplanner.presentation

import android.app.Activity
import android.hromovych.com.routineplanner.R
import android.hromovych.com.routineplanner.presentation.utils.toast
import androidx.lifecycle.ViewModel
import com.google.android.play.core.review.ReviewManagerFactory

class MainViewModel: ViewModel() {

    fun requestReviewFlow(activity: Activity) {
        val reviewManager = ReviewManagerFactory.create(activity)
        val requestReviewFlow = reviewManager.requestReviewFlow()

        requestReviewFlow.addOnCompleteListener { request ->

            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = reviewManager.launchReviewFlow(activity, reviewInfo)

                flow.addOnCompleteListener {
                    activity.toast(R.string.review_success)
                }

            } else {
                request.exception?.printStackTrace()
               activity.toast(R.string.review_failed)

            }
        }
    }


}