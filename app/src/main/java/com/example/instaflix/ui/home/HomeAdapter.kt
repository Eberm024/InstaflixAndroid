/* combining these 2 ideas... mostly the first idea but with direct data sent from list provider (fragment calling it)
* Flower that goes to store in local datastore flower item:
* https://github.com/android/views-widgets-samples/blob/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList/FlowersAdapter.kt#L65
*
* CustomAdapter example:
* https://github.com/android/views-widgets-samples/blob/main/RecyclerView/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java#L76
*
*
* OK time to decide wheter to implement clicking in HomeAdapter (Flower Way) or HomeFragment (CustomAdapter way)
* whatever it is, Homefragment must give the dataSet information (Array) and the Adapter will handle the numerization
* at least for primitive datatypes automatically
*
* fuck this I will rewrite this class
*
* */
package com.example.instaflix.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaflix.R
import com.example.instaflix.data.Movie
import com.squareup.picasso.Picasso

class HomeAdapter(private val mList: List<Movie>):
    RecyclerView.Adapter<HomeViewHolder>() {


    //create and inflate view and return HomeViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).
                inflate(R.layout.home_item, parent, false)

        return HomeViewHolder(view)
    }

    //gets current item
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val itemViewHolder = mList[position]

        //Binding data
        holder.movieTitleTextView.text = itemViewHolder.title
        holder.movieSynopsisTextView.text = itemViewHolder.overview

        //binding data for the image
        val basePath = "https://image.tmdb.org/t/p/w185/"
        val posterPath = itemViewHolder.posterPath
        val fullPath = basePath + posterPath

        if (posterPath != "") {
            Picasso.get().load(fullPath).into(holder.movieImageView)
        } else {
            holder.movieImageView.setImageResource(R.drawable.account_profile) //add generic movie icon
        }

    }

    override fun getItemCount(): Int {

        return mList.size
    }

}



