package com.example.pembeli.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPembeliResponse (
    @SerializedName("id_pembeli")
    val idPembeli: Int?,
    @SerializedName("username_pembeli")
    val usernamePembeli: String?,
    @SerializedName("password_pembeli")
    val passwordPembeli: String?,
    @SerializedName("nama_pembeli")
    val namaPembeli: String?,
    @SerializedName("no_telp_pembeli")
    val telpPembeli: String?,
    @SerializedName("alamat_pembeli")
    val alamatPembeli: String?
): Parcelable