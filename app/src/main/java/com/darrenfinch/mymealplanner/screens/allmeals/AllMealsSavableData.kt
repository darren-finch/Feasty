package com.darrenfinch.mymealplanner.screens.allmeals

import com.darrenfinch.mymealplanner.common.constants.Constants

class AllMealsSavableData {
    private var pendingIdForMealToDelete = Constants.INVALID_ID

    fun setPendingIdForMealToDelete(pendingIdForMealToDelete: Int) {
        this.pendingIdForMealToDelete = pendingIdForMealToDelete
    }

    fun getPendingIdForMealToDelete() = pendingIdForMealToDelete
}