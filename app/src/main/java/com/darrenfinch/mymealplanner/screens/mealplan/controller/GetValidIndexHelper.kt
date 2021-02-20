package com.darrenfinch.mymealplanner.screens.mealplan.controller

import com.darrenfinch.mymealplanner.common.extensions.indexOrLastIndex

class GetValidIndexHelper {
    /**
     * Returns input index if it's valid, or last index of list otherwise.
     * Throws exception if list is empty.
     */
    fun getValidIndex(list: List<Any>, indexToTry: Int): Int {
        if (list.isEmpty()) throw IllegalArgumentException("getValidIndex(): List is empty!")
        return list.indexOrLastIndex(indexToTry)
    }
}
