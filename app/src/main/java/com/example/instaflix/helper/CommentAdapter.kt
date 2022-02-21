package com.example.instaflix.helper

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.ProgressBarDialogFragment
import com.example.instaflix.R
import com.example.instaflix.data.Comment
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
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

        /* Binding data */
        holder.commentTextTextView.text = itemViewHolder.text
        holder.commentUsernameTextView.text = itemViewHolder.author.username.toString()

        val user = ParseUser.getCurrentUser()
        val profilePicture = user.get("profilePicture") as ParseFile
        if (profilePicture != null) {
            Picasso.get().load(profilePicture.url).into(holder.commentImageView)
        } else {
            holder.commentImageView.setImageResource(R.drawable.account_profile)
        }

    }

    override fun getItemCount(): Int {

        return mList.size
    }

}