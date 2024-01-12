package com.canbazdev.bitcointickerapp.presentation.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canbazdev.bitcointickerapp.R
import com.canbazdev.bitcointickerapp.databinding.ItemCoinBinding
import com.canbazdev.bitcointickerapp.domain.model.CoinDetail

class FavoritesAdapter(
    val itemOnClick: (coin: String) -> Unit) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var coinList = ArrayList<CoinDetail>()
    private var userCurrentPosition: Int? = null

    inner class ViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindItems(coin: CoinDetail) {
            Glide.with(itemView.context).load(coin.image)
                .apply(RequestOptions.circleCropTransform()).into(binding.ivCoinImage)
            binding.tvName.text = coin.name
            binding.tvPrice.text = "$${coin.currentPrice.toString()}"

            if ((coin.priceChangePercentage24h ?: 0.0) > 0.0) {
                binding.tvChange.text =
                    "+${String.format("%.2f", coin.priceChangePercentage24h).toDouble()}%"
                binding.tvChange.setTextColor(itemView.resources.getColor(R.color.green))
            } else {
                binding.tvChange.text =
                    "${String.format("%.2f", coin.priceChangePercentage24h).toDouble()}%"
                binding.tvChange.setTextColor(itemView.resources.getColor(R.color.red))
            }
            binding.cardCoin.setOnClickListener {
                itemOnClick.invoke(coin.coinId ?: "bitcoin")
            }




//            if (userCurrentPosition!=null && layoutPosition == userCurrentPosition) {
//                binding.clActionImage.background =
//                    itemView.context.resources.getDrawable(R.color.color_green)
//            } else {
//                binding.clActionImage.background =
//                    itemView.context.resources.getDrawable(R.color.white)
//
//            }

        }

        override fun onClick(p0: View?) {

        }

    }

    fun setCoinDetail(list: List<CoinDetail>) {
        println("kk")
        coinList.clear()
        coinList.addAll(list)
        list.forEach {
            println(it.name)
        }
        notifyDataSetChanged()
    }

    fun setUserPosition(position: Int) {
        userCurrentPosition = position
        notifyItemChanged(position)
    }

    fun deleteCoin(position: Int, getDeletedCoin: (coin: CoinDetail) -> Unit ) {
        val coin = coinList[position]
//        coinList.removeAt(position)
//        notifyItemRemoved(position)
        getDeletedCoin.invoke(coin)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCoinBinding =
            ItemCoinBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(coinList[position])
    }

}
