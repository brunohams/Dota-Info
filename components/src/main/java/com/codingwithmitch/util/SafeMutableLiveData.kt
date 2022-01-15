package com.codingwithmitch.util

import androidx.lifecycle.LiveData

class SafeMutableLiveData<T:Any>(value: T) : LiveData<T>(value) {
    override fun getValue(): T = super.getValue() as T
    public override fun setValue(value: T) = super.setValue(value)
    public override fun postValue(value: T) = super.postValue(value)
}