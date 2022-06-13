package com.abdullah996.bostatask.model.albums


import com.google.gson.annotations.SerializedName

data class AlbumsResponseItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?
)