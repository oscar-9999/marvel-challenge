package com.marvel.inditex.presentation.widget.spinner

import android.content.Context

interface SpinnerLoading {
    fun show(context: Context)
    fun show(context: Context, listener: SpinnerLoadingListener)

    fun dismiss()
    fun dismiss(listener: SpinnerLoadingListener)
}
