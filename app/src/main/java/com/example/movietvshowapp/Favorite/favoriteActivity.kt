package com.example.movietvshowapp.Favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movietvshowapp.R
import com.example.movietvshowapp.SectionPage
import com.example.movietvshowapp.databinding.ActivityFavoriteBinding
import com.example.movietvshowapp.databinding.ActivityHomeBinding

class favoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityHomeBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        val sectionsPagerAdapter = SectionPageFavorite(this, supportFragmentManager)
        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.setupWithViewPager(activityHomeBinding.viewPager)
        supportActionBar?.elevation = 0f
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}