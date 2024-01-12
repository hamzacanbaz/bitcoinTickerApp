package com.canbazdev.bitcointickerapp.presentation.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.presentation.base.BaseFragment
import com.canbazdev.bitcointickerapp.BitcoinTickerApp
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate, LoginViewModel::class.java
) {

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listen()
        observe()
    }

    override fun listen() {
        super.listen()
        binding.tvNoAccount.setOnClickListener {
            goToRegister()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }


    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.loginFlow.collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        BitcoinTickerApp.instance.toast(getString(R.string.success_login_text))
                        val bundle = Bundle()
                        bundle.putString(FirebaseAnalytics.Param.METHOD, "login")
                        analytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
                        goToHome()
                    }

                    is Resource.Error -> {
                        println(result.throwable.message.toString())
                        Snackbar.make(
                            requireView(),
                            getString(
                                R.string.error_login_text,
                                result.throwable.message.toString() + result.throwable.cause.toString()
                            ),
                            Snackbar.LENGTH_SHORT
                        ).show()

                    }

                }
            }
        }


    }

    private fun goToRegister() {
        findNavController().navigate(R.id.actionLoginToRegister)
    }

    private fun goToHome() {
        findNavController().navigate(R.id.actionLoginToHome)
    }


}