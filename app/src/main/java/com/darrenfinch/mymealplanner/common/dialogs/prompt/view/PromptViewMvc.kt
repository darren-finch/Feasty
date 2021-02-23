package com.darrenfinch.mymealplanner.common.dialogs.prompt.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc

interface PromptViewMvc : ObservableViewMvc<PromptViewMvc.Listener> {
    interface Listener {
        fun onPositiveButtonClicked()
        fun onNegativeButtonClicked()
    }

    fun bindTitle(title: String)
    fun bindMessage(message: String)
    fun bindPositiveButtonCaption(caption: String)
    fun bindNegativeButtonCaption(caption: String)
}