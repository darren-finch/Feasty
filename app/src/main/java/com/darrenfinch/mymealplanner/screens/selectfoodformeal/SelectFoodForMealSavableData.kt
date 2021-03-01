package com.darrenfinch.mymealplanner.screens.selectfoodformeal

import java.io.Serializable

class SelectFoodForMealSavableData : Serializable {
    private var curQuery = ""

    fun setCurQuery(curQuery: String) {
        this.curQuery = curQuery
    }

    fun getCurQuery() = curQuery
}