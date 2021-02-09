package com.darrenfinch.mymealplanner.common.logs

fun Any.getClassTag(): String {
    val tag = javaClass.simpleName
    return if (tag.length <= 23) tag else tag.substring(0, 23)
}