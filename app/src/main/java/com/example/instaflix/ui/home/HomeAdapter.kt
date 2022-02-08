package com.example.instaflix.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R
import com.example.instaflix.data.Movie


class HomeAdapter(private val onClick: (Movie) -> Unit ) :
    ListAdapter<Movie, HomeAdapter.HomeViewHolder>(HomeDiffCallBack) {

    class HomeViewHolder(itemView: View, val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val movieTitleTextView: TextView =
            itemView.findViewById(R.id.textView_home_item_title)
        private val movieImageView: ImageView = itemView.findViewById(R.id.imageView_home_item)
        private val movieSynopsisTextView: TextView = itemView.findViewById(R.id.textView_home_item_synopsis)
        private var currentMovie: Movie? = null

        init {
            itemView.setOnClickListener {
                currentMovie?.let {
                    onClick(it)
                }
            }
        }

        /* Bind item's title, image, synopsis */
        fun bind(movie: Movie) {
            currentMovie = movie

            /* process movie title */
            movieTitleTextView.text = movie.title

            /* process movie image */
            if (movie.poster != null) {
                movieImageView.setImageResource(movie.poster)
            } else {
                movieImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
            }

            /* process movie synopsis */
            movieSynopsisTextView.text = movie.synopsis

        }

    }

    //create and inflate view and return HomeViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.home_item, parent, false)
        return HomeViewHolder(view, onClick)
    }

    //gets current item
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    //item object properties and comparison?
    object HomeDiffCallBack: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

}



