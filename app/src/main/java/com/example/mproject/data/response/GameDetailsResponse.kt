package com.example.mproject.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameDetailsResponse(
    val id: Int,
    val title: String,
    val description: String,
    val minimum_system_requirements: SystemRequirements,
): Parcelable {

    @Parcelize
    data class SystemRequirements(
        val os: String,
        val processor: String,
        val memory: String,
        val graphics: String,
        val storage: String,
    ): Parcelable


}
