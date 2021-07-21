package com.example.pembeli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pembeli.R
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.response.DataPesananResponse
import com.example.pembeli.response.DataWishlistResponse
import kotlinx.android.synthetic.main.item_wishlist.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistAdapter(private val listBarang: ArrayList<DataWishlistResponse.WishlistResponse>)
    : RecyclerView.Adapter<WishlistAdapter.ViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(WishlistResponse: DataWishlistResponse.WishlistResponse){

            fun getId(): Int?{
                return WishlistResponse.idPembeli
            }

            fun getIds(): Int?{
                return WishlistResponse.idWishlist
            }

            with(itemView){
                Glide.with(itemView.context)
                    .load(WishlistResponse.fotoBarang)
                    .into(photo_barang)

                tv_nama_barang.text = WishlistResponse.namaBarang

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(WishlistResponse)
                }

                btn_set_pesan.setOnClickListener {
                    val jumlahBeli = tv_jumlah_beli.text.toString()

                    if (JumlahBeli(jumlahBeli) > WishlistResponse.stokBarang){
                        Toast.makeText(context, "Stok Barang Tidak Cukup", Toast.LENGTH_SHORT).show()
                    }else{
                        RetrofitClient.instance.postPesanan(getId(), WishlistResponse.idWishlist, WishlistResponse.idPembeli,
                        WishlistResponse.idBarang, WishlistResponse.namaBarang, WishlistResponse.hargaBarang,
                        WishlistResponse.negaraAsal, WishlistResponse.merkBarang, WishlistResponse.masaGaransi,
                        WishlistResponse.beratBarang, WishlistResponse.stokBarang, WishlistResponse.fotoBarang,
                        WishlistResponse.deskripsiBarang, JumlahBeli(jumlahBeli)).enqueue(object :
                            Callback<DataPesananResponse.PesananResponse> {
                            override fun onFailure(call: Call<DataPesananResponse.PesananResponse>, t: Throwable) {
                                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(
                                call: Call<DataPesananResponse.PesananResponse>,
                                response: Response<DataPesananResponse.PesananResponse>
                            ) {
                                Toast.makeText(context, "Data Berhasil Dipesan", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }

                btn_set_hapus.setOnClickListener {
                    RetrofitClient.instance.deleteWishlist(getId(), getIds()).enqueue(object :
                        Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                btn_tambah.setOnClickListener {
                    val number = tv_jumlah_beli.text.toString()

                    tv_jumlah_beli.text = IncrementNumber(number).toString()
                }

                btn_kurang.setOnClickListener {
                    val default = 1
                    val number = tv_jumlah_beli.text.toString()

                    if (DecrementNumber(number) < default){
                        Toast.makeText(context, "Minimal 1", Toast.LENGTH_SHORT).show()
                        tv_jumlah_beli.text = default.toString()
                    }else{
                        tv_jumlah_beli.text = DecrementNumber(number).toString()
                    }
                }
            }
        }
    }

    fun JumlahBeli(number: String): Int{
        val result = number.toInt()
        return result
    }

    fun IncrementNumber(number: String): Int{
        val result = number.toInt() + 1
        return result
    }

    fun DecrementNumber(number: String): Int{
        val result = number.toInt() - 1
        return result
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataWishlistResponse.WishlistResponse)
    }
}