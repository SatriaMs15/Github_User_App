package com.example.movietvshowapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.entity.ShowData

class TvShowViewModel(private val showRepository: ShowRepository): ViewModel() {
    fun getCourses(): LiveData<List<ShowData>> = showRepository.getALLTvShow()
}