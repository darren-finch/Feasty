package com.darrenfinch.mymealplanner.common.helpers

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity

class ToastsHelper(private val activity: FragmentActivity) {
    fun showLongMsg(@StringRes resId: Int) {
        Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_SHORT).show()
    }
    fun showShortMsg(@StringRes resId: Int) {
        Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_LONG).show()
    }
    fun showLongMsg(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
    fun showShortMsg(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}