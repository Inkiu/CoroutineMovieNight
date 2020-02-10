package com.example.coroutinemovienight.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

@ExperimentalCoroutinesApi
fun <T> Flow<T>.handleError(
    errorHandler: (e: Throwable) -> Unit = {}
): Flow<T> = catch { e -> errorHandler(e) }