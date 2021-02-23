package com.darrenfinch.mymealplanner.common.dialogs.prompt

sealed class PromptDialogEvent {
    object PositiveButtonClicked : PromptDialogEvent()
    object NegativeButtonClicked : PromptDialogEvent()
}