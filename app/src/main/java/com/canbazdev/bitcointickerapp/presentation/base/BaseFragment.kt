package com.canbazdev.bitcointicker.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.canbazdev.bitcointickerapp.common.Constants
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.BitcoinTickerApp

abstract class BaseFragment<B : ViewBinding, V : ViewModel>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B,
    private val modelClass: Class<V>
) : Fragment() {


    protected lateinit var viewModel: V

    private var _binding: B? = null
    val binding: B get() = _binding ?: error("Must only access binding while fragment is attached.")

    open fun B.initialize() {}

    open fun observe() {}
    open fun listen() {}

    protected fun printException(exception: Exception) {
        Log.e(Constants.ERRORTAG, "An error occurred: " + exception.message, exception)

        BitcoinTickerApp.instance.toast("An error occurred. Please try again later.")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = bindingInflater(inflater, container, false)

     //   binding.initialize()

        viewModel = createViewModelLazy(modelClass.kotlin, { viewModelStore }).value

        return binding.root

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}