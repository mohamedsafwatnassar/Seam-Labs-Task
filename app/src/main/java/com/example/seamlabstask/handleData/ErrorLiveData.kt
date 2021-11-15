package com.example.seamlabstask.handleData

import androidx.lifecycle.MutableLiveData

class ErrorLiveData<T> : MutableLiveData<HandleError<T>>() {

    fun postSuccess(data: T?) {
        postValue(HandleError<T>().success(data))
    }

    fun postError(throwable: String?) {
        postValue(HandleError<T>().error(throwable!!))
    }

    fun postConnectionError(throwable: String?) {
        postValue(HandleError<T>().connectionError(throwable!!))
    }
}