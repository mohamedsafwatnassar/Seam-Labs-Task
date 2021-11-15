package com.example.seamlabstask.handleData

class HandleError<T> {

    private var status: DataStatus? = null

    private var data: T? = null

    private var connectionError: String? = null
    private var error: String? = null

    fun success(data: T?): HandleError<T> {
        status = DataStatus.SUCCESS
        this.data = data
        this.connectionError = null
        this.error = null
        return this
    }

    fun error(error: String): HandleError<T> {
        status = DataStatus.ERROR
        this.error = error
        this.connectionError = null
        return this
    }

    fun connectionError(error: String): HandleError<T> {
        status = DataStatus.CONNECTIONERROR
        this.connectionError = error
        this.error = null
        return this
    }

    fun getStatus(): DataStatus {
        return status!!
    }

    fun getData(): T? {
        return data
    }

    fun getError(): String? {
        return error
    }

    fun getConnectionError(): String? {
        return connectionError
    }

    enum class DataStatus {
        SUCCESS, CONNECTIONERROR, ERROR
    }
}