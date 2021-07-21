package com.example.pembeli.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataLoginResponse(
    @SerializedName("id_pembeli")
    val idPembeli: Int?,
    @SerializedName("username_pembeli")
    val usernamePembeli: String?,
    @SerializedName("password_pembeli")
    val passwordPembeli: String?,
) : Parcelable