package com.example.movietvshowapp.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietvshowapp.data.LocalRepository
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.ShowRepository

class DetailViewModel (private val showRepository: ShowRepository):ViewModel() {

    private lateinit var showId: String

    private var mShow = listOf<ShowData>()


    fun setSelectedCourse(showId: String) {
        this.showId = showId
    }

    fun getDetailMovie(): LiveData<ShowData> {
        val course: LiveData<ShowData> = showRepository.getDetailShowMovie(showId)
        return course
    }

    fun getDetailTvShow(): LiveData<ShowData> {
        val course: LiveData<ShowData> = showRepository.getDetailShowTvShow(showId)
        return course
    }

    fun setinsertShow(show: ShowData) {
        this.mShow = listOf(show)
    }

    fun inserts() = showRepository.Insert(mShow)

    fun delete(data:String) = showRepository.Delete(data)

    fun getStatusMovieFavorite(ids:String): LiveData<ShowData>{
        val status = showRepository.getShowByIds(ids)
        return status
    }


}