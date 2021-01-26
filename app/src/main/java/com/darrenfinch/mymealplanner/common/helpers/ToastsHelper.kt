package com.darrenfinch.mymealplanner.common.helpers

import android.content.Context
import android.widget.Toast
import com.darrenfinch.mymealplanner.R

class ToastsHelper(private val context: Context) {
    fun showNetworkError() {
        Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }
    fun showDatabaseError() {
        Toast.makeText(context, context.getString(R.string.database_error), Toast.LENGTH_SHORT).show()
    }
    fun showUnknownError() {
        Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
    }
    fun showLongMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    fun showShortMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}