package com.darrenfinch.mymealplanner.common.views

import android.content.Context
import android.view.View

abstract class BaseViewMvc : ViewMvc {
    private lateinit var view: View

    override fun getRootView(): View {
        return view
    }

    fun setRootView(view: View) {
        this.view = view
    }

    fun getContext(): Context {
        return view.context
    }

    fun getString(stringId: Int): String {
        return getContext().getString(stringId)
    }
}