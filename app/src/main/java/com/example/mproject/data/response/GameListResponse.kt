package com.example.mproject.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameListResponse(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String,
    val genre: String,
    val developer: String,
    val platform: String,

) : Parcelable
