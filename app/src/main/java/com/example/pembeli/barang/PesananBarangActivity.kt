package com.example.pembeli.barang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pembeli.R
import com.example.pembeli.adapter.PesananAdapter
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.response.DataLoginResponse
import com.example.pembeli.response.DataPesananResponse
import kotlinx.android.synthetic.main.activity_pesanan_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananBarangActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PEMBELI_ID = "extra_pembeli_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_barang)

        showPesanan()

        refreshLayout.setOnRefreshListener {
            showPesanan()
            refreshLayout.isRefreshing = false
        }
    }

    private fun getId(): Int? {
        val detailBarangId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?
        return detailBarangId?.idPembeli
    }

    private fun showPesanan() {
        rv_pesanan.setHasFixedSize(true)
        rv_pesanan.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getPesanan(getId()).enqueue(object :
            Callback<DataPesananResponse> {
            override fun onFailure(call: Call<DataPesananResponse>, t: Throwable) {
                Toast.makeText(this@PesananBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataPesananResponse>,
                response: Response<DataPesananResponse>
            ) {
                val list = response.body()?.data
                val pesananAdapter = list?.let { PesananAdapter(it) }
                rv_pesanan.adapter = pesananAdapter

                pesananAdapter?.setOnItemClickCallback(object : PesananAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DataPesananResponse.PesananResponse) {
//                        showSelectedFavorite(data)
                    }
                })
            }
        })
    }
}