package com.example.movietvshowapp.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietvshowapp.data.LocalRepository
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.entity.ShowData

class favViewModel (private val showRepository: ShowRepository): ViewModel() {

    fun getMovieFavorite(): LiveData<PagedList<ShowData>> {
        var list = showRepository.favMovies()
        return list
    }

    fun getTvShowFavorite(): LiveData<PagedList<ShowData>> {
        var list2 = showRepository.favTv()
        return list2
    }


}