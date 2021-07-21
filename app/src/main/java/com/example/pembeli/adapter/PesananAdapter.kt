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
import kotlinx.android.synthetic.main.item_pesanan.view.*
import kotlinx.android.synthetic.main.item_pesanan.view.photo_barang
import kotlinx.android.synthetic.main.item_pesanan.view.tv_nama_barang
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PesananAdapter(private val listBarang: ArrayList<DataPesananResponse.PesananResponse>)
    : RecyclerView.Adapter<PesananAdapter.ViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(PesananResponse: DataPesananResponse.PesananResponse){

            fun getId(): Int?{
                return PesananResponse.idPembeli
            }

            fun getIds(): Int?{
                return PesananResponse.idPesanan
            }

            with(itemView){
                Glide.with(itemView.context)
                    .load(PesananResponse.fotoBarang)
                    .into(photo_barang)

                tv_nama_barang.text = PesananResponse.namaBarang
                tv_deskripsi_barang.text = PesananResponse.deskripsiBarang

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(PesananResponse)
                }

                btn_set_batal.setOnClickListener {
                    RetrofitClient.instance.deletePesanan(getId(), getIds()).enqueue(object :
                        Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<Void>,
                            response: Response<Void>
                        ) {
                            Toast.makeText(context, "Data Berhasil Dibatalkan", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataPesananResponse.PesananResponse)
    }
}