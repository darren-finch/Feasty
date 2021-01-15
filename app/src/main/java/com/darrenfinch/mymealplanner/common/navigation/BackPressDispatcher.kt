package com.darrenfinch.mymealplanner.common.navigation

interface BackPressDispatcher {
    fun registerListener(listener: BackPressListener)
    fun unregisterListener(listener: BackPressListener)
}