package com.darrenfinch.mymealplanner.common.views

interface SavableStateViewMvc<ViewState> : ViewMvc {
    fun saveState() : ViewState
    fun loadState(viewState: ViewState)
}