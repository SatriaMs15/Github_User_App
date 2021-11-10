package com.example.movietvshowapp.Movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietvshowapp.DetailShowActivity
import com.example.movietvshowapp.R
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.databinding.ItemsShowBinding
import com.squareup.picasso.Picasso


class MovieFavoriteAdapter : PagedListAdapter<ShowData, MovieFavoriteAdapter.ShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowData>() {
            override fun areItemsTheSame(oldItem: ShowData, newItem: ShowData): Boolean {
                return oldItem.movieId == newItem.movieId
            }
            override fun areContentsTheSame(oldItem: ShowData, newItem: ShowData): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var listMovie = ArrayList<ShowData>()

    fun setCourses(cours: List<ShowData>?) {
        if (cours == null) return
        this.listMovie.clear()
        this.listMovie.addAll(cours)
    }

    class ShowViewHolder(private val binding: ItemsShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(show: ShowData) {
            with(binding) {
                tvItemTitle.text = show.title
                tvItemDate.text = show.realeseDate
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailShowActivity::class.java)
                    intent.putExtra(DetailShowActivity.EXTRA_COURSE, show.movieId)
                    itemView.context.startActivity(intent)
                }
                var picasso = Picasso.get()
                show.imgId?.let { picasso.load(it).into(imgPoster) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val itemsAcademyBinding = ItemsShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount():Int = listMovie.size

}