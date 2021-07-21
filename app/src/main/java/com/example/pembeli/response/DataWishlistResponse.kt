package com.example.pembeli.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataWishlistResponse(
    val data: ArrayList<WishlistResponse>?
) : Parcelable {
    @Parcelize
    data class WishlistResponse(
        @SerializedName("id_wishlist")
        val idWishlist: Int?,
        @SerializedName("id_pembeli")
        val idPembeli: Int?,
        @SerializedName("id_barang")
        val idBarang: Int?,
        @SerializedName("nama_barang")
        val namaBarang: String?,
        @SerializedName("deskripsi_barang")
        val deskripsiBarang: String?,
        @SerializedName("harga_barang")
        val hargaBarang: Int?,
        @SerializedName("negara_asal")
        val negaraAsal: String?,
        @SerializedName("merk_barang")
        val merkBarang: String?,
        @SerializedName("masa_garansi")
        val masaGaransi: Int?,
        @SerializedName("berat_barang")
        val beratBarang: Int?,
        @SerializedName("stok_barang")
        val stokBarang: Int,
        @SerializedName("foto_barang")
        val fotoBarang: String?,
        @SerializedName("jumlah_beli")
        val jumlahBeli: Int?
    ) :Parcelable
}