package com.example.tablayouttest

import ListBedebah
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.ayush.retrofitexample.QuotesApi
//import com.ayush.retrofitexample.RetrofitHelper

class HomeScreen : AppCompatActivity() {
    private lateinit var contentListAdapter: ListBedebah

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

//        contentListAdapter = ListBedebah("buat api ntar"){onClickAdapter(it["File Name"].toString())
//        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation =LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = contentListAdapter
    }

    private fun onClickAdapter(toString: String) {
        TODO("Not yet implemented")
    }
}