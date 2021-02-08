package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import java.io.Serializable

class ScreenResult {
    private val data = Bundle()

    fun putInt(key: String, value: Int) = data.putInt(key, value)
    fun getInt(key: String) = data.getInt(key)

    fun putSerializable(key: String, value: Serializable) = data.putSerializable(key, value)
    fun getSerializable(key: String) = data.getSerializable(key)
}