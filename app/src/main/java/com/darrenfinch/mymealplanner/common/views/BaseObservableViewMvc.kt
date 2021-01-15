package com.darrenfinch.mymealplanner.common.views

import java.util.concurrent.ConcurrentLinkedDeque

open class BaseObservableViewMvc<ListenerType> : BaseViewMvc(), ObservableViewMvc<ListenerType> {
    private val listeners = ConcurrentLinkedDeque<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        listeners.remove(listener)
    }

    protected fun getListeners() = listeners.toSet()
}