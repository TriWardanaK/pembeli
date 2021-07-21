package com.example.pembeli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pembeli.adapter.BarangAdapter
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.barang.PesananBarangActivity
import com.example.pembeli.barang.WishlistBarangActivity
import com.example.pembeli.response.DataBarangResponse
import com.example.pembeli.response.DataLoginResponse
import com.example.pembeli.view.pembeli.LoginActivity
import com.example.pembeli.view.pembeli.UpdateActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PEMBELI_ID = "extra_pembeli_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showBarang()

        refreshLayout.setOnRefreshListener {
            showBarang()
            refreshLayout.isRefreshing = false
        }
    }

    private fun showBarang() {
        rv_barang.setHasFixedSize(true)
        rv_barang.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getBarang().enqueue(object :
            Callback<DataBarangResponse> {
            override fun onFailure(call: Call<DataBarangResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DataBarangResponse>,
                response: Response<DataBarangResponse>
            ) {
                val list = response.body()?.data
                val barangAdapter = list?.let { BarangAdapter(it) }
                rv_barang.adapter = barangAdapter

                barangAdapter?.setOnItemClickCallback(object : BarangAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: DataBarangResponse.BarangResponse) {
                        showSelectedBarang(data)
                    }
                })
            }
        })
    }

    private fun showSelectedBarang(BarangResponse: DataBarangResponse.BarangResponse){
        val pembeliId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?

        val moveObject = Intent(this@MainActivity, DetailActivity::class.java)
        moveObject.putExtra(DetailActivity.EXTRA_BARANG_ID, BarangResponse)
        moveObject.putExtra(DetailActivity.EXTRA_PEMBELI_ID, pembeliId)
        startActivity(moveObject)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pesanan -> {
                val pembeliId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?

                val intent = Intent(this, PesananBarangActivity::class.java)
                intent.putExtra(PesananBarangActivity.EXTRA_PEMBELI_ID, pembeliId)
                startActivity(intent)
                true
            }

            R.id.wishlist -> {
                val pembeliId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?

                val intent = Intent(this, WishlistBarangActivity::class.java)
                intent.putExtra(WishlistBarangActivity.EXTRA_PEMBELI_ID, pembeliId)
                startActivity(intent)
                true
            }

            R.id.setting -> {
                val memberId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?

                val intent = Intent(this, UpdateActivity::class.java)
                intent.putExtra(UpdateActivity.EXTRA_PEMBELI_ID, memberId)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                finishAffinity()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }
}