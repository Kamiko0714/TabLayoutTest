package com.example.tablayouttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablayouttest.adapter.Adapter_list
import com.example.tablayouttest.data.Row_data
import com.example.tablayouttest.model.Base_model

class Favorite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            Row_data("URL GAMBAR", "Shandy")
        )

        val adapter = Adapter_list(items) { data, item ->
            // Callback dipanggil ketika item diklik
            // Lakukan sesuatu dengan item pada posisi ini
//            Base_model.toast(this, data.url)
            Base_model.toast(this, data.text + " URL : " + data.url)

        }

        recyclerView.adapter = adapter

    }
}
