package com.example.pembeli.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataBarangResponse(
    val data: ArrayList<BarangResponse>?
) : Parcelable {
    @Parcelize
    data class BarangResponse(
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
        val stokBarang: Int?,
        @SerializedName("foto_barang")
        val fotoBarang: String?
    ) :Parcelable
}