package com.darrenfinch.mymealplanner.common.dialogs.prompt.view

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.DialogPromptBinding

class PromptViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<PromptViewMvc.Listener>(), PromptViewMvc {
    private var _binding: DialogPromptBinding? =
        DataBindingUtil.inflate(inflater, R.layout.dialog_prompt, parent, false)
    private val binding = _binding!!

    init {
        setRootView(binding.root)
        setupUI()
    }

    fun setupUI() {
        binding.apply {
            positiveButton.setOnClickListener {
                for (listener in getListeners()) {
                    listener.onPositiveButtonClicked()
                }
            }
            negativeButton.setOnClickListener {
                for (listener in getListeners()) {
                    listener.onNegativeButtonClicked()
                }
            }
        }
    }

    override fun bindTitle(title: String) {
        binding.titleTextView.text = title
    }

    override fun bindMessage(message: String) {
        binding.msgTextView.text = message
    }

    override fun bindPositiveButtonCaption(caption: String) {
        binding.positiveButton.text = caption
    }

    override fun bindNegativeButtonCaption(caption: String) {
        binding.negativeButton.text = caption
    }


    override fun releaseViewRefs() {
        _binding = null
    }
}