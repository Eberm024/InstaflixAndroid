
package com.example.instaflix.ui.home

import android.content.Context
import android.provider.Settings.Global.getString

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instaflix.R
import java.lang.IllegalArgumentException
import javax.sql.DataSource

class HomeViewModel() : ViewModel() {


    //discovermovies in TMDB
    private val tmdbApiKey = 0 //can't retrieve string resources in ViewModel due to string resources being tied to context
    val discoverMovie_url =
        "https://api.themoviedb.org/3/discover/movie?" +
                "api_key=34872395426d9e0ba548d1d51cbd6c10" +
                "&language=en-US" +
                "&sort_by=popularity.desc" +
                "&include_adult=false" +
                "&include_video=false" +
                "&page=1" +
                "&with_watch_monetization_types=flatrate"


    private val _discoverMovieUrl = MutableLiveData<String>().apply {
        value = discoverMovie_url
    }

    val discoverMovieUrl: LiveData<String> = _discoverMovieUrl

    /* cannot use a Volley library inside of a ViewModel since the library needs context
    * and referencing a context inside a viewmodel causes memory leaks */

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}

class HomeViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(

            ) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

