package com.example.tablayouttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
//import com.ayush.retrofitexample.QuotesApi
//import com.ayush.retrofitexample.RetrofitHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = intent.getStringExtra("loginName").toString()

        val Services = ApiConfig.getApiService();
        val userData = Services.findUserDetailByUsername(currentUser);
        val textnama = findViewById<TextView>(R.id.name)
        val textnama2 = findViewById<TextView>(R.id.Nickname)
        val profilePicture = findViewById<ImageView>(R.id.gambar)
        val following = findViewById<TextView>(R.id.folowing_number)
        val followers = findViewById<TextView>(R.id.folowers_number)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val progressBar = findViewById<ProgressBar>(R.id.loadingProgressBar)
        sectionsPagerAdapter.setData(currentUser)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        userData.enqueue(object : retrofit2.Callback<IndividualResponse> {

            override fun onResponse(
                call: Call<IndividualResponse>,
                individualResponse: retrofit2.Response<IndividualResponse>
            ) {
                if (individualResponse.isSuccessful) {
                    val respondBody = individualResponse.body()
                    if (respondBody != null) {
                        Log.d("SUKSES", respondBody.toString())

                        textnama.text = respondBody.name
                        textnama2.text = respondBody.login
                        Glide.with(profilePicture.context).load(respondBody.avatarUrl).into(profilePicture)
                        following.text = respondBody.following.toString() + " Following"
                        followers.text = respondBody.followers.toString() + " Followers"

                        textnama.visibility = View.VISIBLE
                        textnama2.visibility = View.VISIBLE
                        followers.visibility = View.VISIBLE
                        following.visibility = View.VISIBLE
                        profilePicture.visibility = View.VISIBLE
                        viewPager.visibility = View.VISIBLE
                        tabs.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                } else {
                    Log.e("ERR", "onFailure: ${individualResponse.message()}")
                }
            }

            override fun onFailure(call: Call<IndividualResponse>, t: Throwable) {
                Log.e("FAILED TO RETRIEVE", t.message.toString())
                Log.e("FAILED TO RETRIEVE", call.toString())
            }
        })


        }
    fun back(view: View) {
        onBackPressed()
    }


}