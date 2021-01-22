package com.darrenfinch.mymealplanner.common.misc

import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class BaseObservable<ListenerType> {
    private val listeners = Collections.newSetFromMap(ConcurrentHashMap<ListenerType, Boolean>(1))

    fun registerListener(listener: ListenerType) {
        listeners.add(listener)
    }
    fun unregisterListener(listener: ListenerType) {
        listeners.remove(listener)
    }

    protected fun getListeners() = listeners.toSet()
}