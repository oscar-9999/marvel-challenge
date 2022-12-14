package com.marvel.inditex.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import dagger.android.support.AndroidSupportInjection
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, V : ViewBinding?>(layout: Int) : Fragment(layout) {

    protected lateinit var viewModel: VM

    private var _binding: V? = null

    val binding get() = _binding!!

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[getVMClass()]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            initViewModel(arguments)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun getViewBinding(): V

    abstract fun showLoading()

    abstract fun hideLoading()

    fun navigate(navResId: Int, bundle: Bundle?) {
        navController.navigate(navResId, bundle)
    }

    fun showError(message: String) {
        activity?.let { Toast.makeText(it, message, Toast.LENGTH_LONG).show() }
    }

    private fun <VM> getVMClass() =
        (Objects.requireNonNull(javaClass.genericSuperclass) as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>
}
