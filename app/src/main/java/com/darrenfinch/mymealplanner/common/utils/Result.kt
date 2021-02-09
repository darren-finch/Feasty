package com.darrenfinch.mymealplanner.common.utils

sealed class Result<out S, out F: Throwable> {
    data class Success<out S, out F: Throwable>(val value: S) : Result<S, F>()
    data class Failure<out S, out F: Throwable>(val failure: F) : Result<S, F>()
}