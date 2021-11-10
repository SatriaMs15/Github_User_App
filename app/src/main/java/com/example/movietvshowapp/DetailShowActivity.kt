package com.example.movietvshowapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movietvshowapp.ViewModel.DetailViewModel
import com.example.movietvshowapp.ViewModel.ViewModelFactory
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.databinding.ActivityDetailShowBinding
import com.example.movietvshowapp.databinding.ContentDetailShowBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.properties.Delegates


class DetailShowActivity: AppCompatActivity() {

    lateinit var fab : FloatingActionButton

    var statusFavorite : Boolean = false

    private var imageAvatar by Delegates.notNull<Int>()
    private var movieId: String = ""
    lateinit var type:String

    companion object {
        const val EXTRA_COURSE = "extra_course"
        private val TAG = DetailShowActivity::class.java.simpleName
    }

    private lateinit var detailContentBinding: ContentDetailShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailCourseBinding = ActivityDetailShowBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailCourseBinding.detailContent

        setContentView(activityDetailCourseBinding.root)

        setSupportActionBar(activityDetailCourseBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab = detailContentBinding.fabAdd

        val factory = ViewModelFactory.getInstance(application,this)
        val viewModel = ViewModelProvider(this,factory)[DetailViewModel::class.java]

        val actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val extras = intent.extras
        if (extras != null) {
            val showId = extras.getString(EXTRA_COURSE)
            if (showId != null) {
                activityDetailCourseBinding.progressBar.visibility= View.VISIBLE
                activityDetailCourseBinding.detailItem.visibility = View.INVISIBLE

                viewModel.setSelectedCourse(showId)
                if(showId.contains("Movie")){
                    viewModel.getDetailMovie().observe(this, { module ->
                        activityDetailCourseBinding.detailItem.visibility = View.VISIBLE
                        if (module != null) {
                            populateMovie(module)

                            imageAvatar = module.imagePath!!
                            movieId = module.movieId
                            type = "1"
                        }
                        viewModel.getStatusMovieFavorite(movieId).observe(this,{status ->
                            if (status != null) {
                                if (status.showfav == "1") {
                                    detailContentBinding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
                                    statusFavorite = true
                                } else {
                                    statusFavorite = false
                                }
                            }
                        })
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                    })
                }

                if(showId.contains("TvShow")){
                    viewModel.getDetailTvShow().observe(this, { module ->
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                        activityDetailCourseBinding.detailItem.visibility = View.VISIBLE
                        if (module != null) {
                            populateTvShow(module)
                            imageAvatar = module.imagePath!!
                            movieId = module.movieId
                            type = "2"
                        }
                        viewModel.getStatusMovieFavorite(movieId).observe(this,{status ->
                            if (status != null) {
                                if (status.showfav == "1") {
                                    detailContentBinding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
                                    statusFavorite = true
                                } else {
                                    statusFavorite = false
                                }
                            }
                        })
                        activityDetailCourseBinding.progressBar.visibility = View.GONE
                    })
                }

            }
        }

        val actionbar = supportActionBar
        actionbar!!.title = "Detail Show"
        actionbar.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener{
            statusFavorite = !statusFavorite
            setStatusFavorite(statusFavorite,type)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun populateTvShow(courseEntity: ShowData) {
        detailContentBinding.textTitle.text = courseEntity.title
        detailContentBinding.textDescription.text = courseEntity.description
        detailContentBinding.textDate.text = courseEntity.realeseDate

        Glide.with(this)
                .load(courseEntity.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailContentBinding.imagePoster)
    }

    private fun populateMovie(courseEntity: ShowData) {
        detailContentBinding.textTitle.text = courseEntity.title
        Log.d(TAG,courseEntity.title)
        detailContentBinding.textDescription.text = courseEntity.description
        detailContentBinding.textDate.text = courseEntity.realeseDate

        Glide.with(this)
                .load(courseEntity.imagePath)
                .transform(RoundedCorners(20))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(detailContentBinding.imagePoster)
    }

    private fun setStatusFavorite(statusFavorites:Boolean,type:String){
        val factorys = ViewModelFactory.getInstance(application,this)
        val viewModels = ViewModelProvider(this,factorys)[DetailViewModel::class.java]

        val datatitle = detailContentBinding.textTitle.text.toString().trim()
        val datarelease = detailContentBinding.textDate.text.toString().trim()
        val datadesc = detailContentBinding.textDescription.text.toString().trim()
        val datatype = type
        val dataFavorite = "1"
        val dataids = movieId
        val dataAvatar =  imageAvatar

        if (statusFavorites){

                val Data = ShowData(imgId = dataAvatar,movieId = dataids,title = datatitle,description = datadesc,realeseDate = datarelease,showType = datatype,showfav = dataFavorite)

                viewModels.setinsertShow(Data)
                viewModels.inserts()
                //Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                detailContentBinding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            viewModels.delete(movieId)
            detailContentBinding.fabAdd.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            //getHelper.deleteById(getName.text.toString().trim())
            //Toast.makeText(this, getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show()
        }
    }
}