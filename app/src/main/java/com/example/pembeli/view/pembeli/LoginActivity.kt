package com.example.pembeli.view.pembeli

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pembeli.MainActivity
import com.example.pembeli.R
import com.example.pembeli.api.RetrofitClient
import com.example.pembeli.response.DataLoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_signup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val usernamePembeli = edt_username_pembeli.text.toString()
            val passwordPembeli = edt_password_pembeli.text.toString()
            when {
                usernamePembeli.isEmpty() -> {
                    edt_username_pembeli.error = "Masih kosong"
                }
                passwordPembeli.isEmpty() -> {
                    edt_password_pembeli.error = "Masih kosong"
                }
                else -> {
                    RetrofitClient.instance.postLoginPembeli(usernamePembeli, passwordPembeli)
                        .enqueue(
                            object :
                                Callback<DataLoginResponse> {
                                override fun onFailure(
                                    call: Call<DataLoginResponse>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "${t.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onResponse(
                                    call: Call<DataLoginResponse>,
                                    response: Response<DataLoginResponse>
                                ) {

                                    if (response.body()?.idPembeli != null) {
                                        val intent =
                                            Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.putExtra(MainActivity.EXTRA_PEMBELI_ID, response.body())
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Username/Password Salah",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            })
                }
            }
        }
    }
}