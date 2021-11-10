package com.example.movietvshowapp.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.room.ShowDao
import com.example.movietvshowapp.data.room.ShowDatabase
import com.example.movietvshowapp.data.source.remote.RemoteDataSource
import com.example.movietvshowapp.utils.DataHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepository private constructor(private val mshowDao :ShowDao) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: LocalRepository? = null

        fun getInstance(showDao: ShowDao): LocalRepository =
            instance ?: synchronized(this) {
                instance ?: LocalRepository(showDao).apply { instance = this }
            }
    }
/*    init {
        val db = ShowDatabase.getInstance(application)
        mShowDao = db.showDao()
    }*/
    fun favMovieList(): DataSource.Factory<Int, ShowData> = mshowDao.getFavMovieList()

    fun favTvshowList(): DataSource.Factory<Int, ShowData> = mshowDao.getFavTvShowList()

    fun getInsert(shows: List<ShowData>) = executorService.execute {mshowDao.insertShow(shows)}

    fun getShowById(id: String): LiveData<ShowData> = mshowDao.getShowById(id)

    fun getDelete(id: String) = executorService.execute {mshowDao.deleteById(id)}

}