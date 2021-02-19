package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import com.darrenfinch.mymealplanner.common.logs.getClassTag
import java.io.Serializable
import java.lang.NullPointerException

class ScreenDataReturnBuffer {
    private val screenData = Bundle()
    private val tokens = HashSet<String>()

    fun putData(data: Serializable, asyncCompletionToken: String) {
        tokens.add(asyncCompletionToken)
        screenData.putSerializable(asyncCompletionToken, data)
    }

    fun hasDataForToken(asyncCompletionToken: String) =
        tokens.contains(asyncCompletionToken) && screenData.getSerializable(asyncCompletionToken) != null

    fun getData(asyncCompletionToken: String): Serializable {
        if (!hasDataForToken(asyncCompletionToken)) {
            throw NullPointerException("${getClassTag()}: No screen data for token \"${asyncCompletionToken}\"")
        }

        tokens.remove(asyncCompletionToken)
        return screenData.getSerializable(asyncCompletionToken)!!
    }
}