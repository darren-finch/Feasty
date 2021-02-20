package com.darrenfinch.mymealplanner.common.extensions

import android.text.BoringLayout
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

fun <A, B> List<A>.parallelMap(context: CoroutineContext, f: suspend (A) -> B): List<B> = runBlocking(context) {
    map { async(context) { f(it) } }.awaitAll()
}

fun <A, B> List<A>.parallelMapNotNull(context: CoroutineContext, f: suspend (A) -> B): List<B> = runBlocking(context) {
    mapNotNull { async(context) { f(it) } }.awaitAll()
}

fun <A> List<A>.indexIsValid(index: Int) = index in 0..lastIndex
fun <A> List<A>.indexOrLastIndex(index: Int) = if (indexIsValid(index)) index else lastIndex