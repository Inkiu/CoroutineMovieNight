package com.example.coroutinemovienight.common

import androidx.lifecycle.MutableLiveData


class NonNullMutableLiveData <T> (
    private val initialValue: T
) : MutableLiveData<T>() {
    override fun getValue(): T  = super.getValue() ?: initialValue
}