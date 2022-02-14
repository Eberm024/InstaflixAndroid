
package com.example.instaflix.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R
import com.example.instaflix.data.Movie
import com.squareup.picasso.Picasso

class HomeAdapter(private val mList: List<Movie>, private val onItemClicked: (position: Int) -> Unit):
    RecyclerView.Adapter<HomeViewHolder>() {


    //create and inflate view and return HomeViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).
                inflate(R.layout.movie_item, parent, false)

        return HomeViewHolder(view, onItemClicked)
    }

    //gets current item
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val itemViewHolder = mList[position]

        //Binding data
        holder.movieTitleTextView.text = itemViewHolder.title
        holder.movieDescriptionTextView.text = itemViewHolder.overview

        //binding data for the image
        val basePath = "https://image.tmdb.org/t/p/w185/"
        val posterPath = itemViewHolder.posterPath
        val fullPath = basePath + posterPath

        if (posterPath != "") {
            Picasso.get().load(fullPath).into(holder.movieImageView)
        } else {
            holder.movieImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
        }


    }

    override fun getItemCount(): Int {

        return mList.size
    }

}



