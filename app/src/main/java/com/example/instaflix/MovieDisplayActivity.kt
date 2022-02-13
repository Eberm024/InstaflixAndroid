package com.example.instaflix

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.instaflix.data.Movie
import com.example.instaflix.databinding.ActivityHomeBinding
import com.parse.ParseUser
import com.squareup.picasso.Picasso

class MovieDisplayActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_display)

        val movieTitleTextView: TextView = findViewById<TextView>(R.id.textView_movie_display_title)
        val moviePosterImageView: ImageView = findViewById<ImageView>(R.id.img_movie_display_poster)
        val movieBackdropImageView: ImageView = findViewById<ImageView>(R.id.img_movieBackdrop)
        val movieDescriptionTextView: TextView = findViewById<TextView>(R.id.textView_movie_display_description)

        /* get the current array item that I passed with bundles */
        val bundle = this.getIntent().getExtras()
        // val dataArray:  ArrayList<Movie> = bundle?.get("MovieArray") as ArrayList<Movie>
        // val arrayPosition: Int = bundle.get("ArrayPosition") as Int

        // val currentMovie = dataArray.get(arrayPosition)

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