package com.canbazdev.bitcointickerapp.data.model.coinDetail

import com.google.gson.annotations.SerializedName

data class ReposUrl(
    @SerializedName("bitbucket")
    val bitbucket: List<Any?>?,
    @SerializedName("github")
    val github: List<String?>?
)