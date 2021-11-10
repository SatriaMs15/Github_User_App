package com.example.movietvshowapp.utils

import android.content.Context
import com.example.movietvshowapp.data.entity.ShowData

class DataHelper(private val context: Context) {

    fun getMovie(): List<ShowData> {
        val list = ArrayList<ShowData>()
        lateinit var course: ShowData
        val coursesEntities = DataDummy2.generateDummyMovies()
        for (courseEntity in coursesEntities) {
            course = courseEntity
            list.add(course)
        }
        return list
    }

    fun getTvShow(): List<ShowData>{
        val list = ArrayList<ShowData>()
        lateinit var course: ShowData
        val coursesEntities = DataDummy2.generateDummyTvshow()
        for (courseEntity in coursesEntities) {
            course = courseEntity
            list.add(course)
        }
        return list
    }
}
