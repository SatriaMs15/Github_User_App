package com.example.movietvshowapp.Movie

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietvshowapp.ViewModel.MovieViewModel
import com.example.movietvshowapp.ViewModel.ViewModelFactory
import com.example.movietvshowapp.databinding.FragmentMovieBinding
import kotlin.math.E

class MovieFragment() : Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_movie, container, false)
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity().application,requireActivity())
            val viewModel = ViewModelProvider(this,factory)[MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()
            fragmentMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getCourses().observe(viewLifecycleOwner, { courses -> E
                fragmentMovieBinding.progressBar.visibility = View.GONE
                movieAdapter.setCourses(courses)
                movieAdapter.notifyDataSetChanged()
            })

            with(fragmentMovieBinding.rvAcademy) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}