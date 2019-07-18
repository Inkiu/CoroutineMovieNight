package com.example.coroutinemovienight.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.UseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + errorHandler

    open fun onAttached() {}
    open fun onInitialAttached() {}

    suspend fun <P, R> UseCase<P, R>.compose(
        param: P,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = withContext(dispatcher) { invoke(param) }
}