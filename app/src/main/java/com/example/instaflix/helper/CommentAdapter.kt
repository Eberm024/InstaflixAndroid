package com.example.instaflix.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R
import com.example.instaflix.data.Comment
import com.squareup.picasso.Picasso

class CommentAdapter (private val mList: List<Comment>):
    RecyclerView.Adapter<CommentViewHolder>() {

    //create and inflate view and return MovieViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.comment_item, parent, false)

        return CommentViewHolder(view)
    }

    //gets current item
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val itemViewHolder = mList[position]

        //Binding data
        holder.commentTextTextView.text = itemViewHolder.text
        holder.commentUsernameTextView.text = itemViewHolder.author.username.toString()

        //binding data for the image
        /*
        val basePath = "https://image.tmdb.org/t/p/w185/"
        val posterPath = itemViewHolder
        val fullPath = basePath + posterPath

        //image profile
        if (posterPath != "") {
            Picasso.get().load(fullPath).into(holder.movieImageView)
        } else {
            holder.movieImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
        }
        */



    }

    override fun getItemCount(): Int {

        return mList.size
    }

}