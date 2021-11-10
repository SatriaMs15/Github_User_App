package com.example.movietvshowapp.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movietvshowapp.ViewModel.MovieViewModel
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.utils.DataDummy2
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var showRepository: ShowRepository

    @Mock
    private lateinit var observer: Observer<List<ShowData>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(showRepository)
    }

    @Test
    fun getCourses() {
        val dummyCourses = DataDummy2.generateDummyMovies()
        val courses = MutableLiveData<List<ShowData>>()
        courses.value = dummyCourses

        `when`(showRepository.getAllMovie()).thenReturn(courses)
        val courseEntities = viewModel.getCourses()
        verify<ShowRepository>(showRepository).getAllMovie()
        assertNotNull(courseEntities)
        assertEquals(10, courseEntities.value?.size)

        viewModel.getCourses().observeForever(observer)
        verify(observer).onChanged(dummyCourses)
    }
}