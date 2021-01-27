package com.darrenfinch.mymealplanner.common.helpers

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.darrenfinch.mymealplanner.R

class ToastsHelper(private val activity: FragmentActivity) {
    fun showNetworkError() {
        Toast.makeText(activity, activity.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }
    fun showDatabaseError() {
        Toast.makeText(activity, activity.getString(R.string.database_error), Toast.LENGTH_SHORT).show()
    }
    fun showUnknownError() {
        Toast.makeText(activity, activity.getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
    }
    fun showLongMsg(resId: Int) {
        Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_SHORT).show()
    }
    fun showShortMsg(resId: Int) {
        Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_LONG).show()
    }
}