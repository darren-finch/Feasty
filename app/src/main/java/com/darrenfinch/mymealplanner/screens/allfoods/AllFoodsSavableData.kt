package com.darrenfinch.mymealplanner.screens.allfoods

import com.darrenfinch.mymealplanner.common.constants.Constants
import java.io.Serializable

class AllFoodsSavableData : Serializable {
    private var pendingIdOfFoodToDelete = Constants.INVALID_ID

    fun setPendingIdOfFoodToDelete(pendingIdOfFoodToDelete: Int) {
        this.pendingIdOfFoodToDelete = pendingIdOfFoodToDelete
    }

    fun getPendingIdOfFoodToDelete(): Int = pendingIdOfFoodToDelete
}