package com.example.movietvshowapp.data.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.movietvshowapp.data.entity.ShowData

@Dao
interface ShowDao {

/*    @Query("SELECT * FROM showtites where showType = 1")
    fun getFavMovieList(): LiveData<List<ShowData>>*/

    @Query("SELECT * FROM showtites where showType = 1")
    fun getFavMovieList(): DataSource.Factory<Int, ShowData>

    @Query("SELECT * FROM showtites where showType = 2")
    fun getFavTvShowList(): DataSource.Factory<Int, ShowData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShow(courses: List<ShowData>)

    @Query("SELECT * FROM showtites WHERE movieId = :Id")
    fun getShowById(Id: String): LiveData<ShowData>

    @Query("DELETE FROM showtites WHERE movieId = :id")
    fun deleteById(id: String)
}