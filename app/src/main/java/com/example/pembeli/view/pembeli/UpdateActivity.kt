package com.example.pembeli.view.pembeli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.pembeli.R
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.response.DataLoginResponse
import com.example.pembeli.response.DataPembeliResponse
import com.example.pembeli.response.DetailPembeliResponse
import kotlinx.android.synthetic.main.activity_update.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PEMBELI_ID = "extra_pembeli_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        showPembeli()

        btn_update.setOnClickListener {
            val usernamePembeli = edt_username_pembeli.text.toString()
            val passwordPembeli = edt_password_pembeli.text.toString()
            val namaPembeli = edt_nama_pembeli.text.toString()
            val telpPembeli = edt_telp_pembeli.text.toString()
            val alamatPembeli = edt_alamat_pembeli.text.toString()
            when {
                usernamePembeli.isEmpty() -> {
                    edt_username_pembeli.error = "Masih kosong"
                }
                passwordPembeli.isEmpty() -> {
                    edt_password_pembeli.error = "Masih kosong"
                }
                namaPembeli.isEmpty() -> {
                    edt_nama_pembeli.error = "Masih kosong"
                }
                telpPembeli.isEmpty() -> {
                    edt_telp_pembeli.error = "Masih kosong"
                }
                alamatPembeli.isEmpty() -> {
                    edt_alamat_pembeli.error = "Masih kosong"
                }
                else -> {
                    RetrofitClient.instance.putPembeli(getId().toString(), usernamePembeli, passwordPembeli, namaPembeli,
                        telpPembeli, alamatPembeli).enqueue(object :
                        Callback<DataPembeliResponse.PembeliResponse> {
                        override fun onFailure(call: Call<DataPembeliResponse.PembeliResponse>, t: Throwable) {
                            Toast.makeText(this@UpdateActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataPembeliResponse.PembeliResponse>,
                            response: Response<DataPembeliResponse.PembeliResponse>
                        ) {
                            Toast.makeText(this@UpdateActivity, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    private fun getId(): Int? {
        val pembeliId = intent.getParcelableExtra(EXTRA_PEMBELI_ID) as DataLoginResponse?
        return pembeliId?.idPembeli
    }

    private fun showPembeli() {
        RetrofitClient.instance.getDetailPembeli(getId()).enqueue(object :
            Callback<DetailPembeliResponse> {
            override fun onFailure(call: Call<DetailPembeliResponse>, t: Throwable) {
                Toast.makeText(this@UpdateActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DetailPembeliResponse>,
                response: Response<DetailPembeliResponse>
            ) {
                val detail = response.body()
                edt_username_pembeli.setText(detail?.usernamePembeli)
                edt_password_pembeli.setText(detail?.passwordPembeli)
                edt_nama_pembeli.setText(detail?.namaPembeli)
                edt_telp_pembeli.setText(detail?.telpPembeli)
                edt_alamat_pembeli.setText(detail?.alamatPembeli)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_account -> {
                RetrofitClient.instance.deletePembeli(getId()).enqueue(object :
                    Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@UpdateActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        Toast.makeText(this@UpdateActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                        finishAffinity()
                        val intent = Intent(this@UpdateActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                })
                true
            }
            else -> true
        }
    }
}