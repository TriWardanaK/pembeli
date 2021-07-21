package com.example.pembeli.api

import com.example.pembeli.response.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    //buku

    @GET("/barang")
    fun getBarang(): Call<DataBarangResponse>

    @GET("/barang/{id_barang}")
    fun getDetailBarang(@Path("id_barang") id: Int?): Call<DetailDataBarangResponse>

    //wishlist

    @GET("/pembeli/{id_pembeli}/wishlist")
    fun getWishlist(@Path("id_pembeli") id: Int?): Call<DataWishlistResponse>

    @FormUrlEncoded
    @POST("/pembeli/{id_pembeli}/wishlist")
    fun postWishlist(
        @Path("id_pembeli") id: Int?,
        @Field("id_pembeli") idPembeli: Int?,
        @Field("id_barang") idBarang: Int?,
        @Field("nama_barang") namaBarang: String?,
        @Field("harga_barang") hargaBarang: Int?,
        @Field("negara_asal") negaraAsal: String?,
        @Field("merk_barang") merkBarang: String?,
        @Field("masa_garansi") masaGaransi: Int?,
        @Field("berat_barang") beratBarang: Int?,
        @Field("stok_barang") stokBarang: Int?,
        @Field("foto_barang") fotoBarang: String?,
        @Field("deskripsi_barang") deskrpsiBarang: String?,
        @Field("jumlah_beli") jumlahBeli: Int?
    ): Call<DataWishlistResponse.WishlistResponse>

    @DELETE("/pembeli/{id_pembeli}/wishlist/{id_wishlist}")
    fun deleteWishlist(
        @Path("id_pembeli") idPembeli: Int?,
        @Path("id_wishlist") idWishlist: Int?,
    ): Call<Void>

    //pesanan

    @GET("/pembeli/{id_pembeli}/pesanan")
    fun getPesanan(@Path("id_pembeli") id: Int?): Call<DataPesananResponse>

    @FormUrlEncoded
    @POST("/pembeli/{id_pembeli}/pesanan")
    fun postPesanan(
        @Path("id_pembeli") id: Int?,
        @Field("id_wishlist") idWishlist: Int?,
        @Field("id_pembeli") idPembeli: Int?,
        @Field("id_barang") idBarang: Int?,
        @Field("nama_barang") namaBarang: String?,
        @Field("harga_barang") hargaBarang: Int?,
        @Field("negara_asal") negaraAsal: String?,
        @Field("merk_barang") merkBarang: String?,
        @Field("masa_garansi") masaGaransi: Int?,
        @Field("berat_barang") beratBarang: Int?,
        @Field("stok_barang") stokBarang: Int?,
        @Field("foto_barang") fotoBarang: String?,
        @Field("deskripsi_barang") deskrpsiBarang: String?,
        @Field("jumlah_beli") jumlahBeli: Int?
    ): Call<DataPesananResponse.PesananResponse>

    @DELETE("/pembeli/{id_pembeli}/pesanan/{id_pesanan}")
    fun deletePesanan(
        @Path("id_pembeli") idPembeli: Int?,
        @Path("id_pesanan") idPesanan: Int?,
    ): Call<Void>

    //member

    @GET("/pembeli/{id_pembeli}")
    fun getDetailPembeli(@Path("id_pembeli") id: Int?): Call<DetailPembeliResponse>

    @FormUrlEncoded
    @POST("/pembeli/")
    fun postPembeli(@Field("username_pembeli") username: String?,
                   @Field("password_pembeli") password: String?,
                   @Field("nama_pembeli") nama: String?,
                   @Field("no_telp_pembeli") telp: String?,
                   @Field("alamat_pembeli") alamat: String?,
    ): Call<DataPembeliResponse.PembeliResponse>

    @FormUrlEncoded
    @POST("/pembelilogin/")
    fun postLoginPembeli(@Field("username_pembeli") username: String?,
                        @Field("password_pembeli") password: String?,
    ): Call<DataLoginResponse>

    @FormUrlEncoded
    @PUT("/pembeli/{id_pembeli}")
    fun putPembeli(
        @Path("id_pembeli") id: String?,
        @Field("username_pembeli") username: String?,
        @Field("password_pembeli") password: String?,
        @Field("nama_pembeli") nama: String?,
        @Field("no_telp_pembeli") telp: String?,
        @Field("alamat_pembeli") alamat: String?,
    ): Call<DataPembeliResponse.PembeliResponse>

    @DELETE("/pembeli/{id_pembeli}")
    fun deletePembeli(@Path("id_pembeli") id: Int?): Call<Void>
}