package com.example.tablayouttest

import ListBedebah
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment() {
    private lateinit var viewOfLayout: View
    private lateinit var loadingProgressBar: ProgressBar

    val Services = ApiConfig.getApiService();

    private var param1: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("fragment1Container")
        }
        Log.e("DataBundle",param1.toString().replace("\"", ""))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_following, container, false)

        loadingProgressBar = viewOfLayout.findViewById(R.id.loadingProgressBar)
        startLoading()

        var recyclerViews = viewOfLayout.findViewById<RecyclerView>(R.id.recyclerView_following)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        if (recyclerViews != null) {
            recyclerViews.layoutManager = layoutManager
            recyclerViews.itemAnimator = DefaultItemAnimator()
        }

        val followersData = Services.getFollowers(param1.toString().replace("\"", ""));
        followersData.enqueue(object : Callback<List<IndividualResponse>> {

            override fun onResponse(
                call: Call<List<IndividualResponse>>,
                response: Response<List<IndividualResponse>>
            ) {
                if (response.isSuccessful) {
                    val respondBody = response.body()
                    if (respondBody != null) {
                        Log.d("SUKSES Followers", respondBody.toString())

                        recyclerViews.adapter = ListBedebah(respondBody){}

                        stopLoading()
                    }
                } else {
                    Log.e("ERR", "onFailure: ${response.toString()}")

                    stopLoading()
                }
            }

            override fun onFailure(call: Call<List<IndividualResponse>>, t: Throwable) {
                Log.e("FAILED TO RETRIEVE", t.message.toString())
                Log.e("FAILED TO RETRIEVE", call.toString())
            }
        })

        return viewOfLayout
    }

    private fun startLoading() {
        loadingProgressBar.visibility = View.VISIBLE
    }
    private fun stopLoading() {
        loadingProgressBar.visibility = View.GONE
    }

}