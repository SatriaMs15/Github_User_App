package com.example.movietvshowapp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "showtites")
data class ShowData(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "realeseDate")
    var realeseDate: String,
    @ColumnInfo(name = "imagePath")
    var imagePath: Int? = null,
    @ColumnInfo(name = "showType")
    var showType: String,
    @ColumnInfo(name = "fav")
    var showfav: String? = null,
    @ColumnInfo(name = "imgid")
    var imgId: Int? = null
)
