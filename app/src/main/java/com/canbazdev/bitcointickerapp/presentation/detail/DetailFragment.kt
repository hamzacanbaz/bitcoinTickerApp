package com.canbazdev.bitcointickerapp.presentation.detail

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canbazdev.bitcointickerapp.common.extensions.gone
import com.canbazdev.bitcointickerapp.common.extensions.toast
import com.canbazdev.bitcointickerapp.common.extensions.visible
import com.canbazdev.bitcointickerapp.presentation.base.BaseFragment
import com.canbazdev.bitcointickerapp.BitcoinTickerApp
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.common.Resource
import com.canbazdev.bitcointickerapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(
    FragmentDetailBinding::inflate, DetailViewModel::class.java
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observe()
        listen()
    }

    private fun initUI() {
        val refreshTimeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_refresh_time,
            viewModel.refreshTimeIntervals.map { it.second })
        binding.actRefreshTime.setAdapter(refreshTimeAdapter)

    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.coinDetailFlow.collect { result ->

                when (result) {
                    is Resource.Loading -> binding.progressBar.visible()
                    is Resource.Success -> {
                        binding.progressBar.gone()
                        binding.tvCoinName.text = result.data.name
                        binding.tvCoinPrice.text = "$${result.data.currentPrice.toString()}"
                        binding.tvDetailText.text = result.data.description.toString()
                        binding.tvHashingAlgorithm.text = result.data.hashingAlgorithm.toString()
                        Glide.with(requireContext()).load(result.data.image)
                            .apply(RequestOptions.circleCropTransform()).into(binding.ivCoinImage)

                        if ((result.data.priceChangePercentage24h ?: 0.0) > 0.0) {
                            binding.tvCoinChangePercentage.text =
                                "+${
                                    String.format("%.2f", result.data.priceChangePercentage24h)
                                        .toDouble()
                                }%"
                            binding.tvCoinChangePercentage.setTextColor(
                                requireContext().resources.getColor(
                                    R.color.green
                                )
                            )
                        } else {
                            binding.tvCoinChangePercentage.text =
                                "${
                                    String.format("%.2f", result.data.priceChangePercentage24h)
                                        .toDouble()
                                }%"
                            binding.tvCoinChangePercentage.setTextColor(
                                requireContext().resources.getColor(
                                    R.color.red
                                )
                            )
                        }

                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        BitcoinTickerApp.instance.toast(result.throwable.message.toString())
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentPriceFlow.collect {
                binding.tvCoinPrice.text = "$$it"
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isFavFlow.collect {
                if (it){
                    binding.ivFavImage.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.round_favorite_24))
                }
                else{
                    binding.ivFavImage.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.round_favorite_border_24))
                }
            }
        }


    }

    override fun listen() {
        super.listen()
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivFavImage.setOnClickListener {
            viewModel.addToFavourites()
        }

        binding.actRefreshTime.setOnItemClickListener { _, _, position, _ ->
            viewModel.refreshCurrentCoinPeriodically(viewModel.refreshTimeIntervals[position].first.milliseconds)
        }


    }

}