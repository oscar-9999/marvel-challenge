package com.marvel.inditex.presentation.base

sealed class ViewModelState<out T> {
    data class Loaded<out T>(val value: T) : ViewModelState<T>()
    data class ApiError(val message: String) : ViewModelState<Nothing>()
    object DefaultError : ViewModelState<Nothing>()
    object InProgress : ViewModelState<Nothing>()
}