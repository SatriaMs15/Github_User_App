package com.example.movietvshowapp.ViewModel

import android.app.Application
import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.example.movietvshowapp.ViewModel.DetailViewModel
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.room.ShowDao
import com.example.movietvshowapp.data.room.ShowDatabase
import com.example.movietvshowapp.utils.DataDummy2
import org.junit.Test
import androidx.test.core.app.ApplicationProvider.getApplicationContext

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModel2: DetailViewModel
    private val dummyMovie = DataDummy2.generateDummyMovies()[0]
    private val showId = dummyMovie.movieId

    private val dummyTvShow = DataDummy2.generateDummyTvshow()[0]
    private val showId2 = dummyTvShow.movieId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var showRepository: ShowRepository

    @Mock
    private lateinit var courseObserver: Observer<ShowData>

    @Mock
    private lateinit var courseObserver2: Observer<ShowData>


    @Before
    fun setUp() {

        viewModel = DetailViewModel(showRepository)
        viewModel.setSelectedCourse(showId)
        viewModel.setinsertShow(dummyMovie)

        viewModel2 = DetailViewModel(showRepository)
        viewModel2.setSelectedCourse(showId2)
        viewModel2.setinsertShow(dummyTvShow)
    }

    @Test
    fun getMovie() {
        val course = MutableLiveData<ShowData>()
        course.value = dummyMovie

        `when`(showRepository.getDetailShowMovie(showId)).thenReturn(course)
        viewModel.setSelectedCourse(dummyMovie.movieId)
        val courseEntities = viewModel.getDetailMovie().value
        verify(showRepository).getDetailShowMovie(showId)
        assertNotNull(courseEntities)
        assertEquals(dummyMovie.movieId, courseEntities?.movieId)
        assertEquals(dummyMovie.realeseDate, courseEntities?.realeseDate)
        assertEquals(dummyMovie.description, courseEntities?.description)
        assertEquals(dummyMovie.imagePath, courseEntities?.imagePath)
        assertEquals(dummyMovie.title, courseEntities?.title)
        viewModel.getDetailMovie().observeForever(courseObserver)
        verify(courseObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTvShow() {
        val course = MutableLiveData<ShowData>()
        course.value = dummyTvShow

        `when`(showRepository.getDetailShowTvShow(showId2)).thenReturn(course)
        viewModel2.setSelectedCourse(dummyTvShow.movieId)
        val courseEntitie = viewModel2.getDetailTvShow().value
        verify(showRepository).getDetailShowTvShow(showId2)
        assertNotNull(courseEntitie)

        assertEquals(dummyTvShow.movieId, courseEntitie?.movieId)
        assertEquals(dummyTvShow.realeseDate, courseEntitie?.realeseDate)
        assertEquals(dummyTvShow.description, courseEntitie?.description)
        assertEquals(dummyTvShow.imagePath, courseEntitie?.imagePath)
        assertEquals(dummyTvShow.title, courseEntitie?.title)

        viewModel2.getDetailTvShow().observeForever(courseObserver2)
        verify(courseObserver2).onChanged(dummyTvShow)
    }



}