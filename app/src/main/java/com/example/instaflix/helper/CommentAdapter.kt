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

        //Binding data
        holder.commentTextTextView.text = itemViewHolder.text
        holder.commentUsernameTextView.text = itemViewHolder.author.username.toString()

        //binding data for the image
        /*
        val basePath = "https://image.tmdb.org/t/p/w185/"
        val posterPath = itemViewHolder
        val fullPath = basePath + posterPath
        */

        /* query for User */
        //val queryResult: ParseObject? = getAuthorObject(itemViewHolder.author)
        val user = ParseUser.getCurrentUser()
        //image profile
        //val profilePicture = queryResult?.getParseFile("profilePicture")
        val profilePicture = user.get("profilePicture") as ParseFile
        if (profilePicture != null) {
            Picasso.get().load(profilePicture.url).into(holder.commentImageView)
        } else {
            holder.commentImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
        }




    }

    /**
     * Helper function for querying from the Parse API the userObject
     */
    private fun getAuthorObject(author: ParseUser): ParseObject? {

        val query = ParseQuery.getQuery<ParseObject>("User")
        var objectResult: ParseObject? = null
        query.whereMatches("username", author.username)

        query.findInBackground {objects, e ->

            if(e == null) {
                //store objects in global variable
                //objectResult = objects[0] //index issues
                Log.d("ParseQuery", "success on loading the Parse Object")
            }
            else {
                Log.e("ParseQuery", "error on query.findInBackground method: ${e.message}")
            }

        }

        return objectResult

    }

    override fun getItemCount(): Int {

        return mList.size
    }

}