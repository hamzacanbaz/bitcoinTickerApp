package com.canbazdev.bitcointickerapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import com.canbazdev.bitcointickerapp.common.extensions.gone
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.common.extensions.visible
import com.canbazdev.bitcointickerapp.presentation.base.BaseFragment
import com.canbazdev.bitcointickerapp.BitcoinTickerApp
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.common.extensions.showDLog
import com.canbazdev.bitcointickerapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {

    private val homePageViewModel: HomeViewModel by viewModels()

    private val coinsAdapter by lazy { CoinsAdapter { goToCoinDetail(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        listen()
        observe()
    }

    override fun listen() {
        super.listen()

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_searchPage)
        }

        binding.ivLogOut.setOnClickListener {
            homePageViewModel.signOut()
            val navOptions: NavOptions =
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()

            findNavController().navigate(R.id.auth_graph, null, navOptions)
        }

    }

    private fun setAdapter() {
        binding.rvCoins.adapter = coinsAdapter
        binding.rvCoins.addItemDecoration(CoinsItemDecoration())
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.currentUser.collect { result ->

                when (result) {
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        binding.tvUsername.text = result.data.email
//                                result.data.email?.let { userManager.storeUserEmail(it) }
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        BitcoinTickerApp.instance.toast(result.throwable.message.toString())
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.coinMarketsFlow.collect { result ->

                when (result) {
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        coinsAdapter.setCoinMarkets(result.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                    }
                }
            }

        }

        viewModel.refreshCoinWork.observe(viewLifecycleOwner) { listOfWorkInfo ->

            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                showDLog("refresh coin worker is null!")
            }

            val workInfo: WorkInfo = listOfWorkInfo[0]

            if (workInfo.state == WorkInfo.State.ENQUEUED) {
                showDLog("refresh coin worker works well")
                viewModel.getCoinMarkets()
            }
        }




    }

    private fun goToCoinDetail(id: String) {
        val action = HomeFragmentDirections.actionHomePageToDetailPage(id)
        findNavController().navigate(action)
    }

}