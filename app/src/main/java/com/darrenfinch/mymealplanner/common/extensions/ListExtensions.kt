package com.darrenfinch.mymealplanner.common.extensions

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