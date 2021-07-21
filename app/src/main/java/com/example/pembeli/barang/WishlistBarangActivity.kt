package com.example.pembeli.barang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pembeli.R
import com.example.pembeli.adapter.WishlistAdapter
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.response.DataLoginResponse
import com.example.pembeli.response.DataWishlistResponse
import kotlinx.android.synthetic.main.activity_wishlist_barang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistBarangActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_PEMBELI_ID = "extra_pembeli_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_barang)

        showWishlist()

        refreshLayout.setOnRefreshListener {
            showWishlist()
            refreshLayout.isRefreshing = false
        }
    }

    private fun getId(): Int? {
        val detailBarangId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?
        return detailBarangId?.idPembeli
    }

    private fun showWishlist() {
        rv_wishlist.setHasFixedSize(true)
        rv_wishlist.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getWishlist(getId()).enqueue(object :
            Callback<DataWishlistResponse> {
            override fun onFailure(call: Call<DataWishlistResponse>, t: Throwable) {
                Toast.makeText(this@WishlistBarangActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataWishlistResponse>,
                response: Response<DataWishlistResponse>
            ) {
                val list = response.body()?.data
                val wishlistAdapter = list?.let { WishlistAdapter(it) }
                rv_wishlist.adapter = wishlistAdapter

                wishlistAdapter?.setOnItemClickCallback(object : WishlistAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DataWishlistResponse.WishlistResponse) {
//                        showSelectedFavorite(data)
                    }
                })
            }
        })
    }
}