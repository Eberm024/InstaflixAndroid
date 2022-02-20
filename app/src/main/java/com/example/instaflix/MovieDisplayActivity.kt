/*
*  Parse operations: https://www.back4app.com/docs/android/data-objects/android-crud-tutorial
*
 */
package com.example.instaflix

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.data.Comment
import com.example.instaflix.helper.CommentAdapter
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.squareup.picasso.Picasso

class MovieDisplayActivity: AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataArray = ArrayList<Comment>()
    var queryResult: List<ParseObject>? = null
    var queryResultSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_display)

        movieDisplayTask()
        getComments()
        //stall
        Handler().postDelayed({
            commentDisplayTask()
        }, 3000)

    }

    private fun commentDisplayTask() {

        /* handle the data setups? */

        /* Initialize Recyclerview, LayoutManager, and Adapters */
        mRecyclerView = findViewById(R.id.commentsRecyclerView)
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)

        /* Initialize the LayoutManager*/
        mLayoutManager = LinearLayoutManager(this)

        //fill the data... one single element at the time
        //traverse through each queryResult
        val traverse = queryResultSize - 1
        Log.d("CommentTraverse", "traverse: $traverse")
        for (i in 0..traverse) {
            val movieId = queryResult?.get(i)?.getInt("movieId")
            Log.d("movieId", "movieId: $movieId")

            val commentText = queryResult?.get(i)?.getString("text")
            Log.d("commentText", "commentText: $commentText")

            val author = queryResult?.get(i)?.getParseUser("author")
            Log.d("author", "author: $author")  //I need to run some fetchIfNeeded call for the username
            //the Author is a pointer EX:  com.parse.ParseUser@8f20bc
        }

        //after loops
        /* Fill the recyclerview */
        //mRecyclerView?.adapter = CommentAdapter(dataArray)

    }

    /**
     * Helper function for querying from the Parse API the comments
     */
    private fun getComments() {
        val bundle = this.getIntent().getExtras()
        val currentMovieId: Int? = bundle?.get("CurrentMovieId") as Int?

        //alertDialog
        val progressBar = ProgressBarDialogFragment()
        progressBar.show(supportFragmentManager, "progressBar")
        val query = ParseQuery.getQuery<ParseObject>("MovieComments")
        //query.orderByDescending("createdAt")
        query.whereEqualTo("movieId", currentMovieId)

        query.findInBackground {objects, e ->
            progressBar.dismiss()
            if(e == null) {
                //store objects in global variables
                queryResult = objects
                queryResultSize = objects.size
                Log.d("ParseQuery", "objects.size ${objects.size}")
                Log.d("ParseQuery", "success on loading the Parse Object")
            }
            else {
                Log.e("ParseQuery", "error on query.findInBackground method: ${e.message}")
            }

        }

    }

    /**
     * This function is for the send button of a new user Comment.
     * The libraries that are used are: Parse (Back4App)
     * task to be done: update entry in Parse using API, update Recyclerview and reload it.
     */
    fun onClickSendCommentButton(view: View) {

        /* create parse object */
        val comment = ParseObject("MovieComments")

        /* fill the object */
        val bundle = this.getIntent().getExtras()
        val userComment = findViewById<TextView>(R.id.textView_comment_item_commentText)
        val user: ParseUser = bundle?.get("user") as ParseUser
        val currentMovieId: Int = bundle?.get("CurrentMovieId") as Int

        comment.put("movieId", currentMovieId)
        comment.put("text", userComment.text.toString())
        comment.put("author", user)

        /* saveInBackground (run the api call) */
        //alertDialog
        val progressBar = ProgressBarDialogFragment()
        progressBar.show(supportFragmentManager, "progressBar")
        // call the progressBarDialog
        comment.saveInBackground { e ->
            //dismiss the progressBarDialog
            progressBar.dismiss()

            if(e == null) {
                // add the new entry to the RecyclerView then reload the RecyclerView
                dataArray.add(Comment(
                    currentMovieId,
                    userComment.text.toString(),
                    user
                ))

            }
            else {
                //show the alert and log the error
                Log.e("SendComment_saveInBackground",
                    "error on saving data in saveInBackground method: ${e.message}")
            }
        }

    }

    private fun movieDisplayTask() {
        val movieTitleTextView: TextView = findViewById<TextView>(R.id.textView_movie_display_title)
        val moviePosterImageView: ImageView = findViewById<ImageView>(R.id.img_movie_display_poster)
        val movieBackdropImageView: ImageView = findViewById<ImageView>(R.id.img_movieBackdrop)
        val movieDescriptionTextView: TextView = findViewById<TextView>(R.id.textView_movie_display_description)

        /* get the current array item that I passed with bundles */
        val bundle = this.getIntent().getExtras()

        val movieTitle: String = bundle?.get("CurrentMovieTitle") as String
        val movieOverview: String = bundle.get("CurrentMovieOverview") as String
        val moviePoster: String = bundle.get("CurrentMoviePoster") as String
        val movieBackdrop: String = bundle.get("CurrentMovieBackdrop") as String

        /* modify content of the view items */
        movieTitleTextView.text = movieTitle
        movieDescriptionTextView.text = movieOverview

        val posterBasePath = "https://image.tmdb.org/t/p/w185/"
        val posterURL = posterBasePath + moviePoster
        val backdropBasePath = "https://image.tmdb.org/t/p/w300/"
        val backdropURL = backdropBasePath + movieBackdrop

        if (moviePoster != "") {
            Picasso.get().load(posterURL).into(moviePosterImageView)
        } else {
            moviePosterImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
        }

        if (movieBackdrop != "") {
            Picasso.get().load(backdropURL).into(movieBackdropImageView)
        } else {
            movieBackdropImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
        }
    }
}