package com.canbazdev.bitcointickerapp.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.canbazdev.bitcointickerapp.databinding.ItemSearchCoinBinding
import com.canbazdev.bitcointickerapp.domain.model.CoinList

class SearchCoinAdapter(
    val itemOnClick: (coinId: String) -> Unit
) :
    ListAdapter<CoinList, SearchCoinAdapter.CoinViewHolder>(CoinComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding =
            ItemSearchCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchCoinAdapter.CoinViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    inner class CoinViewHolder(private var binding: ItemSearchCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CoinList) {

            binding.tvName.text = item.name

            binding.cardCoin.setOnClickListener {
                itemOnClick.invoke(item.coinId ?: "bitcoin")
            }
        }
    }



    class CoinComparator : DiffUtil.ItemCallback<CoinList>() {
        override fun areItemsTheSame(oldItem: CoinList, newItem: CoinList) =
            oldItem.coinId == newItem.coinId

        override fun areContentsTheSame(oldItem: CoinList, newItem: CoinList) =
            oldItem == newItem
    }
}