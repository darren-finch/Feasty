package com.darrenfinch.mymealplanner.common.dialogs

import com.darrenfinch.mymealplanner.common.misc.BaseObservable

class DialogsEventBus : BaseObservable<DialogsEventBus.Listener>() {
    interface Listener {
        fun onDialogEvent(event: Any)
    }

    fun postEvent(event: Any) {
        for(listener in getListeners()) {
            listener.onDialogEvent(event)
        }
    }
}