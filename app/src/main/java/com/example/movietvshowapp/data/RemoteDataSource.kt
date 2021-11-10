package com.example.movietvshowapp.data.source.remote

import android.os.Handler
import android.os.Looper
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.utils.DataHelper
import com.example.movietvshowapp.utils.EspressoIdlingResource

class RemoteDataSource private constructor(private val dataHelper: DataHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: DataHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    //fun getMovie(callback: LoadMovieCallback): List<MovieResponse> = dataHelper.getMovie()
    fun getMovie(callback: LoadMovieCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({ callback.onMovieReceived(dataHelper.getMovie())
            EspressoIdlingResource.decrement()}, SERVICE_LATENCY_IN_MILLIS)
    }

    //fun getTvshow(): List<TvShowResponse> = dataHelper.getTvShow()
    fun getTvshow(callback: LoadTvShowCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({ callback.onTvShowReceived(dataHelper.getTvShow())
            EspressoIdlingResource.decrement()}, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadMovieCallback {
        fun onMovieReceived(courseRespons: List<ShowData>)
    }

    interface LoadTvShowCallback {
        fun onTvShowReceived(courseResponses: List<ShowData>)
    }

}