package com.example.movietvshowapp.ViewModel

import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.movietvshowapp.data.ShowRepository
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.utils.DataDummy2
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class favViewModelTest {

    @Mock
    private lateinit var showRepository: ShowRepository

    @Mock
    lateinit var viewModel: favViewModel

    @Mock
    private lateinit var pagedList: PagedList<ShowData>

    @Mock
    lateinit var observer: Observer<PagedList<ShowData>>

    @Mock
    lateinit var favoredObserver: Observer<PagedList<ShowData>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = favViewModel(showRepository)
    }

    @Test
    fun testGetMovieFavorite() {

        val items = DataDummy2.generateDummyMovies()
        val config = PagedList.Config.Builder()
            .setPageSize(items.size)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(items.size)
            .build()

        val pagedLists = PagedList.Builder(ListDataSource(items),config)
            .setNotifyExecutor (UiThreadExecutor())
            .setFetchExecutor (Executors.newSingleThreadExecutor())
            .build ()

        pagedList = pagedLists
        val courses = MutableLiveData<PagedList<ShowData>>()
        courses.value = pagedList

        `when`(showRepository.favMovies()).thenReturn(courses)
        val courseEntities = viewModel.getMovieFavorite().value
        verify<ShowRepository>(showRepository).favMovies()
        assertNotNull(courseEntities)
        assertEquals(10, courseEntities?.size)

        viewModel.getMovieFavorite().observeForever(observer)
        verify(observer).onChanged(pagedList)
    }

    @Test
    fun testGetTvShowFavorite() {
        val items = DataDummy2.generateDummyTvshow()
        val config = PagedList.Config.Builder()
            .setPageSize(items.size)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(items.size)
            .build()

        val pagedLists = PagedList.Builder(ListDataSource(items),config)
            .setNotifyExecutor (UiThreadExecutor())
            .setFetchExecutor (Executors.newSingleThreadExecutor())
            .build ()

        pagedList = pagedLists
        val courses = MutableLiveData<PagedList<ShowData>>()
        courses.value = pagedList

        `when`(showRepository.favTv()).thenReturn(courses)
        val courseEntities = viewModel.getTvShowFavorite().value
        verify<ShowRepository>(showRepository).favTv()
        assertNotNull(courseEntities)
        assertEquals(10, courseEntities?.size)

        viewModel.getTvShowFavorite().observeForever(observer)
        verify(observer).onChanged(pagedList)
    }

    class UiThreadExecutor: Executor {
        private val handler = Handler(Looper.getMainLooper ())
        override fun execute (command: Runnable) {
            handler.post (command)
        }
    }

    class ListDataSource (private val items: List<ShowData>): PageKeyedDataSource<Int, ShowData>() {
        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ShowData>) {
            callback.onResult (items, 0, items.size)
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShowData>) {
            TODO("Not yet implemented")
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShowData>) {
            TODO("Not yet implemented")
        }

    }
}