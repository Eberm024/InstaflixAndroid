package com.example.instaflix.helper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R

/**
 * RecyclerViewHolder class
 * contains UI information about one item in RecyclerView.
 * This class can be in a separate class or inside RecyclerViewAdapter class
 * */
class MovieViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val movieTitleTextView: TextView = itemView.findViewById(R.id.textView_movie_item_title)
    val movieImageView: ImageView = itemView.findViewById(R.id.imageView_movie_item)
    val movieDescriptionTextView: TextView = itemView.findViewById(R.id.textView_movie_item_description)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }

}