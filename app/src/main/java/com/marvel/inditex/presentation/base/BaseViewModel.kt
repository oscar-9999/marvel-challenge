package com.marvel.inditex.presentation.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.inditex.domain.RequestFailure
import com.marvel.inditex.domain.ResultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected abstract fun init(arguments: Bundle)

    fun initViewModel(arguments: Bundle?) {
        init(arguments ?: Bundle())
    }

    inline fun <S> async(flow: Flow<ResultOf<S>>, crossinline block: (S) -> Unit) =
        viewModelScope.launch {
            flow.collect {
                when (it) {
                    is ResultOf.Success -> block(it.value)
                    is ResultOf.Failure -> errorManager(it.requestFailure)
                }
            }
        }

    abstract fun errorManager(requestFailure: RequestFailure)
}