package com.example.tablayouttest

import ListBedebah
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


class HomeScreen : AppCompatActivity() {
    private lateinit var contentListAdapter: ListBedebah
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            otomatis(isDarkModeActive)
        }
        val btn = findViewById<ImageButton>(R.id.settingButton)
        btn.setOnClickListener{
//            Toast.makeText(this, "Coba tot", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        val favorite_btn = findViewById<ImageButton>(R.id.favButton)
        btn.setOnClickListener{
//            Toast.makeText(this, "Coba tot", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation =LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        val searchBTN = findViewById<Button>(R.id.searchButton)
        val searchBox = findViewById<EditText>(R.id.usernameEditText)

        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        val Services = ApiConfig.getApiService();
        val allUsers = Services.listAllUsers("1");

        searchBTN.setOnClickListener{
            val msg : String = searchBox.text.toString()
            if(msg.trim().isNotEmpty()) {
                startLoading()

                val searchUser = Services.searchUserDetailByLoginName(searchBox.text.toString());
                searchUser.enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        individualResponse: retrofit2.Response<SearchResponse>) {
                        if (individualResponse.isSuccessful) {
                            val respondBody = individualResponse.body()
                            if (respondBody != null) {
                                Log.d("SUKSES", respondBody.toString())
                                contentListAdapter = ListBedebah(respondBody.items as List<IndividualResponse>){onClickAdapter(it.login.toString()) }
                                recyclerView.adapter = contentListAdapter
                            }
                        } else {
                            Log.e("ERR", "onFailure: ${individualResponse.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        Log.e("FAILED TO RETRIEVE", t.message.toString())
                        Log.e("FAILED TO RETRIEVE", call.toString())
                    }
                })
            }else{
                allUsers.clone().enqueue(object : Callback<List<IndividualResponse>> {
                    override fun onResponse(
                        call: Call<List<IndividualResponse>>,
                        individualResponse: retrofit2.Response<List<IndividualResponse>>) {
                        if (individualResponse.isSuccessful) {
                            val respondBody = individualResponse.body()
                            if (respondBody != null) {
                                Log.d("SUKSES", respondBody.toString())
                                contentListAdapter = ListBedebah(respondBody){onClickAdapter(it.login.toString()) }
                                recyclerView.adapter = contentListAdapter
                                stopLoading()
                            }
                        } else {
                            Log.e("ERR", "onFailure: ${individualResponse.message()}")

                            stopLoading()
                        }
                    }

                    override fun onFailure(call: Call<List<IndividualResponse>>, t: Throwable) {
                        Log.e("FAILED TO RETRIEVE", t.message.toString())
                        Log.e("FAILED TO RETRIEVE", call.toString())

                        stopLoading()
                    }
                })
            }
        }


        allUsers.clone().enqueue(object : Callback<List<IndividualResponse>> {
            override fun onResponse(
                call: Call<List<IndividualResponse>>,
                individualResponse: retrofit2.Response<List<IndividualResponse>>) {
                if (individualResponse.isSuccessful) {
                    val respondBody = individualResponse.body()
                    if (respondBody != null) {
                        Log.d("SUKSES", respondBody.toString())
                        contentListAdapter = ListBedebah(respondBody){onClickAdapter(it.login.toString()) }
                        recyclerView.adapter = contentListAdapter
                    }
                } else {
                    Log.e("ERR", "onFailure: ${individualResponse.message()}")
                }
            }

            override fun onFailure(call: Call<List<IndividualResponse>>, t: Throwable) {
                Log.e("FAILED TO RETRIEVE", t.message.toString())
                Log.e("FAILED TO RETRIEVE", call.toString())

            }
        })
    }

    private fun onClickAdapter(classID :String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("loginName", classID)
        startActivity(intent)
    }
    private fun startLoading() {
        loadingProgressBar.visibility = View.VISIBLE
    }
    private fun stopLoading() {
        loadingProgressBar.visibility = View.GONE
    }
    fun otomatis (boolean: Boolean){
        if (boolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}