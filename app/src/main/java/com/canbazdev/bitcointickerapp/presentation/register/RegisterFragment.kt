package com.canbazdev.bitcointickerapp.presentation.register

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.presentation.base.BaseFragment
import com.canbazdev.bitcointickerapp.BitcoinTickerApp
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate, RegisterViewModel::class.java
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        listen()
    }

    override fun listen() {
        super.listen()
        binding.tvAlreadyHaveAnAccount.setOnClickListener {
            goToLogin()
        }
        binding.btnRegister.setOnClickListener {

            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.register(email, password)


        }

    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.registerFlow.collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        BitcoinTickerApp.instance.toast(getString(R.string.success_register_text))
                        goToLogin()
                    }

                    is Resource.Error -> {
                        BitcoinTickerApp.instance.toast(
                            getString(
                                R.string.error_register_text,
                                result.throwable.message.toString() + result.throwable.cause.toString()
                            ))
                    }
                }
            }
        }

    }


    private fun goToLogin() {
        findNavController().navigate(R.id.actionRegisterToLogin)
    }

}