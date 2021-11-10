package com.example.movietvshowapp.TvShow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietvshowapp.ViewModel.TvShowViewModel
import com.example.movietvshowapp.ViewModel.ViewModelFactory
import com.example.movietvshowapp.databinding.FragmentTVBinding
import kotlin.math.E

class TVFragment : Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTVBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_t_v, container, false)
        fragmentTvShowBinding = FragmentTVBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity().application,requireActivity())
            val viewModel = ViewModelProvider(this,factory)[TvShowViewModel::class.java]

            val tvShowAdapter = TvShowAdapter()
            fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
            viewModel.getCourses().observe(viewLifecycleOwner, { courses -> E
                fragmentTvShowBinding.progressBar.visibility = View.GONE
                tvShowAdapter.setCourses(courses)
                tvShowAdapter.notifyDataSetChanged()
            })

            with(fragmentTvShowBinding.rvTvshows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}