package com.example.instaflix.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R
import com.squareup.picasso.Picasso
import org.json.JSONObject

/**
 * RecyclerViewHolder class
 * contains UI information about one item in RecyclerView.
 * This class can be in a separate class or inside RecyclerViewAdapter class
 * */
class HomeViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val movieTitleTextView: TextView = itemView.findViewById(R.id.textView_home_item_title)
    val movieImageView: ImageView = itemView.findViewById(R.id.imageView_home_item)
    val movieSynopsisTextView: TextView = itemView.findViewById(R.id.textView_home_item_synopsis)

} //end of RecyclerViewHolder class