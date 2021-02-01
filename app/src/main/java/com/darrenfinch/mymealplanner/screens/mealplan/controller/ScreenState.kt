package com.darrenfinch.mymealplanner.screens.mealplan.controller

sealed class ScreenState
object Loading : ScreenState()
object HasData : ScreenState()
object NoData : ScreenState()
object Error : ScreenState()
object HasDialog : ScreenState()
