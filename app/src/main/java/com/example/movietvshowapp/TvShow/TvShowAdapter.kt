package com.example.movietvshowapp.TvShow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movietvshowapp.DetailShowActivity
import com.example.movietvshowapp.R
import com.example.movietvshowapp.data.entity.ShowData
import com.example.movietvshowapp.databinding.ItemsShowBinding

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.ShowViewHolder>() {

    private var listTvShow = ArrayList<ShowData>()

    fun setCourses(tvshows: List<ShowData>?) {
        if (tvshows == null) return
        this.listTvShow.clear()
        this.listTvShow.addAll(tvshows)
    }


    class ShowViewHolder(private val binding: ItemsShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvshow: ShowData) {
            with(binding) {
                tvItemTitle.text = tvshow.title
                tvItemDate.text = tvshow.realeseDate
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailShowActivity::class.java)
                    intent.putExtra(DetailShowActivity.EXTRA_COURSE, tvshow.movieId)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(tvshow.imagePath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val itemsAcademyBinding = ItemsShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val tvshow = listTvShow[position]
        holder.bind(tvshow)
    }

    override fun getItemCount():Int = listTvShow.size

}