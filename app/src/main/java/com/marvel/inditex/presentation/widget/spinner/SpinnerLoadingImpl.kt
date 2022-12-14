package com.marvel.inditex.presentation.widget.spinner


import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.marvel.inditex.R

class SpinnerLoadingImpl : SpinnerLoading {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var progressBar: ProgressBar

    private fun setupSpinnerView(context: Context) {
        progressDialog = ProgressDialog(context)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        progressBar = ProgressBar(context)
        progressBar.id = R.id.spinnerProgress
        progressBar.isIndeterminate = true
        progressBar.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(
                context,
                R.color.dark_grey
            ), PorterDuff.Mode.SRC_IN
        )
        progressBar.setBackgroundResource(android.R.color.transparent)
    }

    override fun show(context: Context) {
        setupSpinnerView(context)
        if (!progressDialog.isShowing) {
            progressDialog.show()
            progressDialog.setContentView(progressBar)
        }
    }

    override fun show(context: Context, listener: SpinnerLoadingListener) {
        progressDialog.setOnShowListener { listener.onFinishAction() }
        this.show(context)
    }

    override fun dismiss() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }

    override fun dismiss(listener: SpinnerLoadingListener) {
        progressDialog.setOnDismissListener { listener.onFinishAction() }
        this.dismiss()
    }
}