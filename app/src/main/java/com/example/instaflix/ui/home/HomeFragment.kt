/* https://developer.android.com/guide/topics/ui/layout/recyclerview and
*    check https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList
*
*  JSONObjectRequest for triggering api requests based on URL. https://www.kompulsa.com/how-to-send-a-get-request-in-android/#:~:text=Another%20type%20of%20GET%20request%20you%E2%80%99re%20likely%20to,of%20a%20StringRequest%2C%20which%20is%20designed%20for%20strings.
*  JSON library for processing JSON format  https://www.tutorialspoint.com/android/android_json_parser.htm#:~:text=JSON%20-%20Parsing%20%20%20%20Sr.No%20,method%20returns%20...%20%203%20more%20rows%20
*  Android's page https://developer.android.com/training/volley/
*
*
* following this for recyclerview, adapter and data in Kotlin:
* https://www.geeksforgeeks.org/android-recyclerview-in-kotlin/
*
* ok now find a way for each list item to have an onClickListener button, fix some display bugs in the items
* https://www.avinsharma.com/android-recyclerview-onclick/
*
*  */
package com.example.instaflix.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.instaflix.MovieDisplayActivity
import com.example.instaflix.R
import com.example.instaflix.data.Movie
import com.example.instaflix.databinding.FragmentHomeBinding
import com.example.instaflix.helper.MovieAdapter
import com.parse.ParseUser
import org.json.JSONArray
import org.json.JSONException

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var discoverResult: JSONArray? = null
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    var dataArray = ArrayList<Movie>()
    private var pageNum: Int = 1

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* Handle any data in here */
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        getDiscoverJSONArray()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("waitTime", "Now waiting for 2 seconds for getDiscoverJSONArray to finish...")
        Handler().postDelayed({
            /* Initialize Recyclerview, LayoutManager, and Adapters */
            mRecyclerView = view.findViewById(R.id.homeRecyclerView)
            mRecyclerView?.setHasFixedSize(true)
            mRecyclerView?.layoutManager = LinearLayoutManager(context)

            /* Initialize the LayoutManager*/
            mLayoutManager = LinearLayoutManager(activity)

            //1 page = 20 movies ***
            for (i in 0..19) {
                try {
                    val movieObj = discoverResult?.getJSONObject(i)
                    Log.d("movieObj",
                        "movieObj num $i has the title: ${movieObj?.getString("title")}")

                    dataArray.add(Movie(
                        movieObj?.getInt("id"),
                        movieObj?.getString("title"),
                        movieObj?.getString("poster_path"),
                        movieObj?.getString("backdrop_path"),
                        movieObj?.getString("overview"),
                        movieObj?.getString("release_date"),
                    )) //end of adding data

                }
                catch (exception: JSONException) {
                    Log.e("movieObj", "Error adding movie, skipping faulty movie")
                }
            }

            /* Fill the recyclerview */
            mRecyclerView?.adapter = MovieAdapter(dataArray)
            { position -> onListItemClick(position) }

        }, 2000) //2 sec
    }


    private fun onListItemClick(position: Int) {

        val intent = Intent(activity, MovieDisplayActivity::class.java)
        val bundle = requireActivity().getIntent().getExtras()
        val user: ParseUser = bundle?.get("user") as ParseUser
        intent.putExtra("user", user)

        val selectedMovie = dataArray.get(position)

        intent.putExtra("CurrentMovieId", selectedMovie.id)
        intent.putExtra("CurrentMovieTitle", selectedMovie.title)
        intent.putExtra("CurrentMovieOverview", selectedMovie.overview)
        intent.putExtra("CurrentMovieBackdrop", selectedMovie.backdropPath)
        intent.putExtra("CurrentMoviePoster", selectedMovie.posterPath)
        intent.putExtra("CurrentMovieReleaseDate", selectedMovie.releaseDate)

        activity?.startActivity(intent)
    }

    /**
     * Helper function that helps retrieving the JSONObject from TMDB servers
     * Runs the following things: Volley, JSONObjectRequest
     * Modifies the following things: discoverResult
     */
    private fun getDiscoverJSONArray() {

        val movies_url =
            "https://api.themoviedb.org/3/discover/movie?" +
                    "api_key=${getString(R.string.tmdb_app_key)}" +
                    "&language=en-US" +
                    "&sort_by=popularity.desc" +
                    "&include_adult=false" +
                    "&include_video=false" +
                    "&page=$pageNum" +
                    "&with_watch_monetization_types=flatrate"

        Log.d("movies_url", "The movie url is below:\n $movies_url")

        /* Volley Request for getting JSON response */
        val requestQueue = Volley.newRequestQueue(activity)

        val request = JsonObjectRequest(Request.Method.GET,movies_url, null,
            /*Response.Listener<JSONObject>*/ {
                    response ->
                Log.d("json", "Success!!")
                discoverResult = response.getJSONArray("results")

            },
            /*Response.ErrorListener*/ {
                Log.e("json", "Error on retrieving the JSONArray response")
            })
        //access the requestQueue
        requestQueue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}