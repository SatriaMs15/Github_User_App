package com.example.movietvshowapp.ViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.movietvshowapp.ViewModel.TvShowViewModel
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.utils.DataDummy2
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var showRepository: ShowRepository

    @Mock
    private lateinit var observer: Observer<List<ShowData>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(showRepository)
    }


    @Test
    fun getCourses() {
        val dummyCourses = DataDummy2.generateDummyTvshow()
        val courses = MutableLiveData<List<ShowData>>()
        courses.value = dummyCourses

        Mockito.`when`(showRepository.getALLTvShow()).thenReturn(courses)
        val courseEntities = viewModel.getCourses()
        Mockito.verify<ShowRepository>(showRepository).getALLTvShow()
        assertNotNull(courseEntities)
        assertEquals(10, courseEntities.value?.size)

        viewModel.getCourses().observeForever(observer)
        verify(observer).onChanged(dummyCourses)
    }
}