package com.example.pembeli.view.pembeli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pembeli.R
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.response.DataPembeliResponse
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_signup.setOnClickListener {
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
                    RetrofitClient.instance.postPembeli(usernamePembeli, passwordPembeli, namaPembeli,
                        telpPembeli, alamatPembeli).enqueue(object :
                        Callback<DataPembeliResponse.PembeliResponse> {
                        override fun onFailure(call: Call<DataPembeliResponse.PembeliResponse>, t: Throwable) {
                            Toast.makeText(this@SignupActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<DataPembeliResponse.PembeliResponse>,
                            response: Response<DataPembeliResponse.PembeliResponse>
                        ) {
                            edt_username_pembeli.text.clear()
                            edt_password_pembeli.text.clear()
                            edt_nama_pembeli.text.clear()
                            edt_telp_pembeli.text.clear()
                            edt_alamat_pembeli.text.clear()

                            Toast.makeText(this@SignupActivity, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
}