package com.example.movietvshowapp

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import com.example.movietvshowapp.RecyclerViewItemCountAssertion.Companion.withItemCount
import com.example.movietvshowapp.utils.DataDummy2
import com.example.movietvshowapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest{

    private val dummyMovie = DataDummy2.generateDummyMovies()
    private val dummyTvShow = DataDummy2.generateDummyTvshow()

    private val dummyfavMovie1 = DataDummy2.generateDummyMovies()[0]
    private val dummyfavMovie2 = DataDummy2.generateDummyMovies()[1]
    private val dummyfavMovie = listOf(dummyfavMovie1,dummyfavMovie2)

    private val dummyfavtv1 = DataDummy2.generateDummyTvshow()[0]
    private val dummyfavtv2 = DataDummy2.generateDummyTvshow()[1]
    private val dummyfavtv = listOf(dummyfavtv1,dummyfavtv2)


    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)


    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovie() {
        onView((withId(R.id.rv_academy))).check(matches(isDisplayed()))
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(withText(dummyMovie[0].realeseDate)))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(withText(dummyMovie[0].description)))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))

        pressBack()
    }

    @Test
    fun loadTvShow() {
        onView(withText("TvShow")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("TvShow")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(withText(dummyTvShow[0].title)))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(withText(dummyTvShow[0].realeseDate)))
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))
        onView(withId(R.id.text_description)).check(matches(withText(dummyTvShow[0].description)))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
    }

    @Test
    fun favorite() {
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform((click()))
        onView(withContentDescription("Navigate up")).perform(click())

        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform((click()))
        onView(withContentDescription("Navigate up")).perform(click())

        onView(withText("TvShow")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform((click()))
        onView(withContentDescription("Navigate up")).perform(click())

        onView(withText("TvShow")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform((click()))
        onView(withContentDescription("Navigate up")).perform(click())

        onView(withId(R.id.favorite)).perform((click()))

        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyfavMovie.size))
        onView(withId(R.id.rv_academy)).check(withItemCount(2))
        onView(withId(R.id.rv_academy)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform((click()))
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withId(R.id.rv_academy)).check(withItemCount(1))

        onView(withText("TvShow")).perform(click())
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyfavtv.size))
        onView(withId(R.id.rv_tvshows)).check(withItemCount(2))
        onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_add)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add)).perform((click()))
        onView(withContentDescription("Navigate up")).perform(click())
        onView(withId(R.id.rv_tvshows)).check(withItemCount(1))
    }


}