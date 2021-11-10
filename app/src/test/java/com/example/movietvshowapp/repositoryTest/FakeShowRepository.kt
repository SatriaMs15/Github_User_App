package com.example.movietvshowapp.repositoryTest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.DataSourceGet
import com.example.movietvshowapp.data.LocalRepository
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.source.remote.RemoteDataSource
class FakeShowRepository private constructor(private val remoteDataSource: RemoteDataSource,private val localRepository: LocalRepository):DataSourceGet {


    override fun getAllMovie(): LiveData<List<ShowData>> {
        val MovieResults = MutableLiveData<List<ShowData>>()
        remoteDataSource.getMovie(object : RemoteDataSource.LoadMovieCallback {
            override fun onMovieReceived(courseRespons: List<ShowData>) {
                val showList = ArrayList<ShowData>()
                for (response in courseRespons) {
                    val course = ShowData(
                        response.movieId,
                        response.title,
                        response.description,
                        response.realeseDate,
                        response.imagePath,
                        response.showType,
                        response.showfav
                    )
                    showList.add(course)
                }
                MovieResults.postValue(showList)
            }
        })
        return MovieResults
    }

    override fun getALLTvShow(): LiveData<List<ShowData>> {
        val TvShowResults = MutableLiveData<List<ShowData>>()
        remoteDataSource.getTvshow(object : RemoteDataSource.LoadTvShowCallback {
            override fun onTvShowReceived(courseResponses: List<ShowData>) {
                val showList = ArrayList<ShowData>()
                for (response in courseResponses) {
                    val course = ShowData(
                        response.movieId,
                        response.title,
                        response.description,
                        response.realeseDate,
                        response.imagePath,
                        response.showType,
                        response.showfav
                    )
                    showList.add(course)
                }
                TvShowResults.postValue(showList)
            }
        })
        return TvShowResults
    }


    override fun getDetailShowMovie(showId: String): LiveData<ShowData> {
        val MovieResult = MutableLiveData<ShowData>()
        remoteDataSource.getMovie(object : RemoteDataSource.LoadMovieCallback {
            override fun onMovieReceived(courseRespons: List<ShowData>) {
                lateinit var course: ShowData
                for (response in courseRespons) {
                    if (response.movieId == showId) {
                        course = ShowData(
                            response.movieId,
                            response.title,
                            response.description,
                            response.realeseDate,
                            response.imagePath,
                            response.showType,
                            response.showfav
                        )
                    }
                }
                MovieResult.postValue(course)
            }
        })
        return MovieResult
    }

    override fun getDetailShowTvShow(showId: String): LiveData<ShowData> {
        val TvShowResult = MutableLiveData<ShowData>()
        remoteDataSource.getTvshow(object : RemoteDataSource.LoadTvShowCallback {
            override fun onTvShowReceived(courseResponses: List<ShowData>) {
                lateinit var course: ShowData
                for (response in courseResponses) {
                    if (response.movieId == showId) {
                        course = ShowData(
                            response.movieId,
                            response.title,
                            response.description,
                            response.realeseDate,
                            response.imagePath,
                            response.showType,
                            response.showfav
                        )
                    }
                }
                TvShowResult.postValue(course)
            }
        })
        return TvShowResult
    }

    override fun favMovies(): LiveData<PagedList<ShowData>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localRepository.favMovieList(), config).build()
    }

    override fun favTv(): LiveData<PagedList<ShowData>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localRepository.favTvshowList(), config).build()
    }

    override fun Insert(shows: List<ShowData>) {
        return localRepository.getInsert(shows)
    }

    override fun getShowByIds(id: String): LiveData<ShowData> {
        return localRepository.getShowById(id)
    }

    override fun Delete(id: String) {
        return localRepository.getDelete(id)
    }
}