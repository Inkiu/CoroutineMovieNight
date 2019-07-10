package com.example.coroutinemovienight.common

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.UseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private val supervisorJob = SupervisorJob()
    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + supervisorJob + errorHandler

    open fun onAttached() {}
    open fun onInitialAttached() {}

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }

    suspend fun <P, R> UseCase<P, R>.compose(
        param: P,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = withContext(dispatcher) { invoke(param) }
}