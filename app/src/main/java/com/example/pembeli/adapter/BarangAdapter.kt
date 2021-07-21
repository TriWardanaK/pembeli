package com.example.pembeli.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pembeli.R
import com.example.pembeli.response.DataBarangResponse
import kotlinx.android.synthetic.main.item_barang.view.*

class BarangAdapter(private val listBarang: ArrayList<DataBarangResponse.BarangResponse>)
    : RecyclerView.Adapter<BarangAdapter.ViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listBarang.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBarang[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(BarangResponse: DataBarangResponse.BarangResponse){

            fun getIds(): Int? {
                return BarangResponse.idBarang
            }

            with(itemView){
                Glide.with(itemView.context)
                    .load(BarangResponse.fotoBarang)
                    .into(photo_barang)

                tv_nama_barang.text = BarangResponse.namaBarang
                tv_deskripsi_barang.text = BarangResponse.deskripsiBarang

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(BarangResponse)
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DataBarangResponse.BarangResponse)
    }
}
