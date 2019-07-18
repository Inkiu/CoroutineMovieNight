package com.example.coroutinemovienight.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <T> LiveData<T>.nonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    observe(owner, Observer { t -> t?.let { observer(it) } })
}

fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    val mediator: MediatorLiveData<T> = MediatorLiveData()
    mediator.addSource(this) {
        if (it != mediator.value) mediator.value = it
    }
    return mediator
}

fun <T> LiveData<T>.debounce(duration: Long): LiveData<T> {
    return MediatorLiveData<T>().also { mld ->
        val source = this
        val handler = android.os.Handler(android.os.Looper.getMainLooper())

        val runnable = Runnable {
            mld.value = source.value
        }

        mld.addSource(source) {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, duration)
        }
    }
}

fun <T, R> LiveData<T>.map(func: (t: T) -> R): LiveData<R> {
    return Transformations.map(this) { func(it) }
}

fun <T> LiveData<T>.filter(predicate: (t: T) -> Boolean): LiveData<T> {
    val mediator: MediatorLiveData<T> = MediatorLiveData()
    mediator.addSource(this) {
        if (it != null && predicate(it)) mediator.postValue(it)
    }
    return mediator
}