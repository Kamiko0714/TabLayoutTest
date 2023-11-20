package com.example.tablayouttest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.gson.Gson

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var currUser: String? = null

    fun setData(Data : String){
        currUser = Data
    }

    override fun createFragment(position: Int): Fragment {
        when (position)
        {
            0 -> {
                var fragment = FollowingFragment();

                var bundle : Bundle = Bundle();
                var gson : Gson = Gson();

                bundle.putString("fragment1Container", gson.toJson(currUser));
                fragment.setArguments(bundle);

                return fragment
            }
            1 -> {
                var fragment = FollowersFragment();

                var bundle : Bundle = Bundle();
                var gson : Gson = Gson();

                bundle.putString("fragment1Container", gson.toJson(currUser));
                fragment.setArguments(bundle);

                return fragment
            }
            else -> {
                return FollowingFragment()
            }
        }
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = FollowingFragment()
//            1 -> fragment = FollowersFragment()
//        }
//        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}