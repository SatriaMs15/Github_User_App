package com.example.movietvshowapp.repositoryTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.movietvshowapp.ViewModel.favViewModelTest
import com.example.movietvshowapp.data.LocalRepository
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.data.source.remote.RemoteDataSource
import com.example.movietvshowapp.utils.DataDummy2
import com.example.movietvshowapp.utils.LiveDataTestUtil
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.*

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.doAnswer
import org.mockito.Mock
import org.mockito.Mockito
import java.util.concurrent.Executors

class FakeShowRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localRepository: LocalRepository

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalRepository::class.java)
    private val show = mock(FakeShowRepository::class.java)
    private val showRepository = ShowRepository(remote,local)

    private val movieResponses = DataDummy2.generateDummyMovies()
    private val courseId = movieResponses[0].movieId

    private val TvShowResponses = DataDummy2.generateDummyTvshow()
    private val courseId2 = TvShowResponses[0].movieId


    @Mock
    private lateinit var pagedList: PagedList<ShowData>

    @Test
    fun getAllMovie() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback)
                .onMovieReceived(movieResponses)
            null
        }.`when`(remote).getMovie(any())
        val courseEntities = LiveDataTestUtil.getValue(showRepository.getAllMovie())
        verify(remote).getMovie(any())
        assertNotNull(courseEntities)
        assertEquals(movieResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getALLTvShow() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowCallback)
                .onTvShowReceived(TvShowResponses)
            null
        }.`when`(remote).getTvshow(any())
        val courseEntities = LiveDataTestUtil.getValue(showRepository.getALLTvShow())
        verify<RemoteDataSource>(remote).getTvshow(any())
        assertNotNull(courseEntities)
        assertEquals(TvShowResponses.size.toLong(), courseEntities.size.toLong())
    }

    @Test
    fun getDetailShowMovie() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback)
                .onMovieReceived(movieResponses)
            null
        }.`when`(remote).getMovie(any())
        val resultCourse = LiveDataTestUtil.getValue(showRepository.getDetailShowMovie(courseId))
        verify<RemoteDataSource>(remote).getMovie(any())
        assertNotNull(resultCourse)
        assertEquals(movieResponses[0].title, resultCourse.title)
    }

    @Test
    fun getDetailShowTvShow() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowCallback)
                .onTvShowReceived(TvShowResponses)
            null
        }.`when`(remote).getTvshow(any())
        val resultCourse = LiveDataTestUtil.getValue(showRepository.getDetailShowTvShow(courseId2))
        verify<RemoteDataSource>(remote).getTvshow(any())
        assertNotNull(resultCourse)
        assertEquals(TvShowResponses[0].title, resultCourse.title)
    }

    @Test
    fun getfavTv() {
        val items = DataDummy2.generateDummyTvshow()
        val config = PagedList.Config.Builder()
            .setPageSize(items.size)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(items.size)
            .build()

        val pagedLists = PagedList.Builder(favViewModelTest.ListDataSource(items),config)
            .setNotifyExecutor (favViewModelTest.UiThreadExecutor())
            .setFetchExecutor (Executors.newSingleThreadExecutor())
            .build ()

        pagedList = pagedLists
        val courses = MutableLiveData<PagedList<ShowData>>()
        courses.value = pagedList

        `when`(show.favTv()).thenReturn(courses)
        val moduleEntities = show.favTv()
        assertNotNull(moduleEntities)
    }

    @Test
    fun getfavMovie() {
        val items = DataDummy2.generateDummyMovies()
        val config = PagedList.Config.Builder()
            .setPageSize(items.size)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(items.size)
            .build()

        val pagedLists = PagedList.Builder(favViewModelTest.ListDataSource(items),config)
            .setNotifyExecutor (favViewModelTest.UiThreadExecutor())
            .setFetchExecutor (Executors.newSingleThreadExecutor())
            .build ()

        pagedList = pagedLists
        val courses = MutableLiveData<PagedList<ShowData>>()
        courses.value = pagedList

        `when`(show.favMovies()).thenReturn(courses)
        val moduleEntities = show.favMovies()
        assertNotNull(moduleEntities)
    }

    @Test
    fun getShowId() {
        val movie = DataDummy2.generateDummyTvshow()[0]
        var lived = MutableLiveData<ShowData>()
        lived.value = movie
        `when`(show.getShowByIds(id = "Movie1")).thenReturn(lived)
        val courseEntities = show.getShowByIds(id = "Movie1")
        assertNotNull(courseEntities)
        assertEquals("Arrow", courseEntities.value?.title)
    }
}