package com.example.instaflix.helper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R

class CommentViewHolder (itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val commentUsernameTextView: TextView = itemView.findViewById(R.id.textView_comment_item_username)
    val commentImageView: ImageView = itemView.findViewById(R.id.imageView_comment_item_profile)
    val commentTextTextView: TextView = itemView.findViewById(R.id.textView_comment_item_commentText)

}