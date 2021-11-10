package com.example.movietvshowapp.Favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietvshowapp.Movie.MovieFavoriteAdapter
import com.example.movietvshowapp.R
import com.example.movietvshowapp.TvShow.TvShowAdapter
import com.example.movietvshowapp.ViewModel.TvShowViewModel
import com.example.movietvshowapp.ViewModel.ViewModelFactory
import com.example.movietvshowapp.ViewModel.favViewModel
import com.example.movietvshowapp.databinding.FragmentTVBinding
import com.example.movietvshowapp.databinding.FragmentTvFavBinding
import kotlin.math.E

class TvFavFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTvFavBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_t_v, container, false)
        fragmentTvShowBinding = FragmentTvFavBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity().application,requireActivity())
            val viewModel = ViewModelProvider(this,factory)[favViewModel::class.java]

            val tvShowAdapter = MovieFavoriteAdapter()
            fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
            viewModel.getTvShowFavorite().observe(viewLifecycleOwner,{Show -> E
                fragmentTvShowBinding.progressBar.visibility = View.GONE
                tvShowAdapter.setCourses(Show)
                //tvShowAdapter.notifyDataSetChanged()
                tvShowAdapter.submitList(Show)
            })
            with(fragmentTvShowBinding.rvTvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}