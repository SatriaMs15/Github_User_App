package com.example.movietvshowapp.Favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietvshowapp.Movie.MovieAdapter
import com.example.movietvshowapp.Movie.MovieFavoriteAdapter
import com.example.movietvshowapp.ViewModel.ViewModelFactory
import com.example.movietvshowapp.ViewModel.favViewModel
import com.example.movietvshowapp.databinding.FragmentMovFavBinding
import kotlin.math.E

class MovFavFragment : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovFavBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_movie, container, false)
        fragmentMovieBinding = FragmentMovFavBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity().application,requireActivity())
            val viewModel = ViewModelProvider(this,factory)[favViewModel::class.java]

            val movieAdapter = MovieFavoriteAdapter()
            fragmentMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getMovieFavorite().observe(viewLifecycleOwner,{Show -> E
                fragmentMovieBinding.progressBar.visibility = View.GONE
                movieAdapter.setCourses(Show)
                movieAdapter.submitList(Show)
                //movieAdapter.notifyDataSetChanged()
            })
            /*viewModel.getCourses().observe(viewLifecycleOwner, { courses -> E
                fragmentMovieBinding.progressBar.visibility = View.GONE
                movieAdapter.setCourses(courses)
                movieAdapter.notifyDataSetChanged()
            })*/

            with(fragmentMovieBinding.rvAcademy) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}