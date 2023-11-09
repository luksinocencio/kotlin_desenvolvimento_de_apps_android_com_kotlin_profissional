package com.devmeist3r.taskapp.util

sealed class StateView<T>(val data: T? = null, val massage: String? = null) {
    class OnLoading<T>: StateView<T>()
    class OnSucess<T>(data: T, massage: String? = null): StateView<T>(data, massage)
    class OnError<T>(massage: String? = null): StateView<T>(null, massage)
}