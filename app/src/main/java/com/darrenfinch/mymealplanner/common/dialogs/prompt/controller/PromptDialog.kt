package com.darrenfinch.mymealplanner.common.dialogs.prompt.controller

import android.app.Dialog
import android.os.Bundle
import com.darrenfinch.mymealplanner.common.controllers.BaseDialog
import com.darrenfinch.mymealplanner.common.dialogs.prompt.PromptDialogEvent
import com.darrenfinch.mymealplanner.common.dialogs.prompt.view.PromptViewMvc

class PromptDialog : BaseDialog(), PromptViewMvc.Listener {

    companion object {
        const val TITLE_ARG = "TITLE_ARG"
        const val MESSAGE_ARG = "MESSAGE_ARG"
        const val POSITIVE_BUTTON_CAPTION_ARG = "POSITIVE_BUTTON_CAPTION_ARG"
        const val NEGATIVE_BUTTON_CAPTION_ARG = "NEGATIVE_BUTTON_CAPTION_ARG"

        fun newInstance(title: String, message: String, positiveButtonCaption: String, negativeButtonCaption: String): PromptDialog {
            val bundle = Bundle().apply {
                putString(TITLE_ARG, title)
                putString(MESSAGE_ARG, message)
                putString(POSITIVE_BUTTON_CAPTION_ARG, positiveButtonCaption)
                putString(NEGATIVE_BUTTON_CAPTION_ARG, negativeButtonCaption)
            }
            val fragment = PromptDialog()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewMvc: PromptViewMvc

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewMvc = fragmentCompositionRoot.getViewMvcFactory().getPromptViewMvc(null)

        viewMvc.bindTitle(requireArguments().getString(TITLE_ARG)!!)
        viewMvc.bindMessage(requireArguments().getString(MESSAGE_ARG)!!)
        viewMvc.bindPositiveButtonCaption(requireArguments().getString(POSITIVE_BUTTON_CAPTION_ARG)!!)
        viewMvc.bindNegativeButtonCaption(requireArguments().getString(NEGATIVE_BUTTON_CAPTION_ARG)!!)

        val dialog = Dialog(requireContext())
        dialog.setContentView(viewMvc.getRootView())

        return dialog
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onPositiveButtonClicked() {
        dismiss()
        fragmentCompositionRoot.getDialogsEventBus().postEvent(PromptDialogEvent.PositiveButtonClicked)
    }

    override fun onNegativeButtonClicked() {
        dismiss()
        fragmentCompositionRoot.getDialogsEventBus().postEvent(PromptDialogEvent.NegativeButtonClicked)
    }
}