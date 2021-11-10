package com.example.movietvshowapp

import android.content.Context
import com.example.movietvshowapp.ViewModel.ViewModelFactory.Companion.getInstance
import com.example.movietvshowapp.data.LocalRepository
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.ShowRepository.Companion.getInstance
import com.example.movietvshowapp.data.room.ShowDatabase
import com.example.movietvshowapp.data.room.ShowDatabase.Companion.getInstance
import com.example.movietvshowapp.data.source.remote.RemoteDataSource
import com.example.movietvshowapp.data.source.remote.RemoteDataSource.Companion.getInstance
import com.example.movietvshowapp.utils.DataHelper

object Injection {
    fun provideRepository(context: Context): ShowRepository {

        val database = ShowDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(DataHelper(context))
        val localDataSource = LocalRepository.getInstance(database.showDao())
        return ShowRepository.getInstance(remoteDataSource,localDataSource)
    }
}