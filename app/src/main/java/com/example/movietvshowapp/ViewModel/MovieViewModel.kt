package com.example.movietvshowapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.ShowRepository

class MovieViewModel(private val showRepository: ShowRepository): ViewModel() {
    fun getCourses(): LiveData<List<ShowData>> = showRepository.getAllMovie()
}