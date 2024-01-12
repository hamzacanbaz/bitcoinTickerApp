package com.canbazdev.bitcointicker.presentation.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canbazdev.bitcointickerapp.common.extensions.gone
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.common.extensions.visible
import com.canbazdev.bitcointicker.presentation.base.BaseFragment
import com.canbazdev.bitcointickerapp.BitcoinTickerApp
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.databinding.FragmentSearchBinding
import com.canbazdev.bitcointickerapp.presentation.home.CoinsItemDecoration
import com.canbazdev.bitcointickerapp.presentation.search.SearchCoinAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate, SearchViewModel::class.java
) {

    private val searchCoinAdapter by lazy { SearchCoinAdapter { goToCoinDetail(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observe()
        listen()

    }

    private fun setAdapter() {
        binding.rvCoins.adapter = searchCoinAdapter
        binding.rvCoins.addItemDecoration(CoinsItemDecoration())

    }

    override fun observe() {
        super.observe()


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.coinListFlow.collect { result ->

                when (result) {
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        println(result.data)
                        searchCoinAdapter.submitList(result.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        BitcoinTickerApp.instance.toast(result.throwable.message.toString())
                    }
                }
            }
        }

        binding.svCoinSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchCoin(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchCoin(it)
                }

                return false
            }
        })
    }

    override fun listen() {
        super.listen()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun goToCoinDetail(id: String) {
        val action = SearchFragmentDirections.actionSearchToDetail(id)
        findNavController().navigate(action)
    }

}