package com.darrenfinch.mymealplanner.screens.selectmealplanmeal

class SelectMealPlanMealSavableData {
    private var curQuery = ""

    fun setCurQuery(curQuery: String) {
        this.curQuery = curQuery
    }

    fun getCurQuery() = curQuery
}