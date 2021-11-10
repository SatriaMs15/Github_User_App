package com.example.movietvshowapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.movietvshowapp.data.entity.ShowData

interface DataSourceGet {
    fun getAllMovie(): LiveData<List<ShowData>>

    fun getALLTvShow(): LiveData<List<ShowData>>

    fun getDetailShowMovie(showId: String): LiveData<ShowData>

    fun getDetailShowTvShow(showId: String): LiveData<ShowData>

    fun favMovies(): LiveData<PagedList<ShowData>>

    fun favTv(): LiveData<PagedList<ShowData>>

    fun Insert(shows: List<ShowData>)

    fun getShowByIds(id: String): LiveData<ShowData>

    fun Delete(id: String)

}