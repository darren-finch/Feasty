package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import java.io.Serializable

class ScreenResult(val tag: String = "") : Serializable {
    private val data = Bundle()

    fun putInt(key: String, value: Int) = data.putInt(key, value)
    fun getInt(key: String) = data.getInt(key)

    fun putSerializable(key: String, value: Serializable) = data.putSerializable(key, value)
    fun getSerializable(key: String) = data.getSerializable(key)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScreenResult

        if (tag != other.tag) return false
        // Intentionally not comparing this right now
        // if (data != other.data) return false:

        return true
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }
}