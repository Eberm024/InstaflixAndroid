package com.example.instaflix.ui.home

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
class HomeViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val movieTitleTextView: TextView = itemView.findViewById(R.id.textView_home_item_title)
    val movieImageView: ImageView = itemView.findViewById(R.id.imageView_home_item)
    val movieSynopsisTextView: TextView = itemView.findViewById(R.id.textView_home_item_synopsis)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = adapterPosition
        onItemClicked(position)
    }

}