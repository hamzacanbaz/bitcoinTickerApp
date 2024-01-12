package com.canbazdev.bitcointickerapp.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.bitcointickerapp.common.extensions.gone
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.common.extensions.visible
import com.canbazdev.bitcointickerapp.presentation.base.BaseFragment
import com.canbazdev.bitcointicker.presentation.favorites.FavoritesViewModel
import com.canbazdev.bitcointickerapp.BitcoinTickerApp
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.databinding.FragmentFavoritesBinding
import com.canbazdev.bitcointickerapp.presentation.home.CoinsItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(
    FragmentFavoritesBinding::inflate, FavoritesViewModel::class.java
) {


    private val favouriteAdapter by lazy { FavoritesAdapter { goToCoinDetail(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observe()
        listen()
    }

    private fun setAdapter() {
        binding.rvCoins.adapter = favouriteAdapter
        binding.rvCoins.addItemDecoration(CoinsItemDecoration())


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                favouriteAdapter.deleteCoin(viewHolder.adapterPosition) {
                    viewModel.deleteFromFavourites(it)
                }
                favouriteAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.rvCoins)
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.favouritesFlow.collect { result ->

                when (result) {
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        println(result.data)
                        favouriteAdapter.setCoinDetail(result.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        BitcoinTickerApp.instance.toast(result.throwable.message.toString())
                    }
                }
            }
        }

    }

    override fun listen() {
        super.listen()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun goToCoinDetail(id: String) {
        val action = FavoritesFragmentDirections.actionFavoritesToDetail(id)
        findNavController().navigate(action)
    }


}