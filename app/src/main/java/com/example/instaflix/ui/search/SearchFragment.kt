package com.example.instaflix.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
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
import com.example.instaflix.databinding.FragmentSearchBinding
import com.example.instaflix.helper.MovieAdapter
import com.parse.ParseUser
import org.json.JSONArray
import org.json.JSONException
import java.net.URLEncoder

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    var dataArray = ArrayList<Movie>()
    private var searchResult: JSONArray? = null
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var pageNum: Int = 1
    private var textInput: String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //buttons for the fragment
        val searchbarButton: ImageButton = requireActivity().findViewById<ImageButton>(R.id.imagebutton_search)

        var bundle = requireActivity().getIntent().getExtras()
        val user: ParseUser = bundle?.get("user") as ParseUser

        searchbarButton.setOnClickListener {
            onClickSearchBarButton()
        }
    }

    private fun onClickSearchBarButton() {

        /* wipe any existing data the recyclerview data has */
        dataArray.removeAll(dataArray)

        Log.d("waitTime", "Now waiting for 2 seconds for getDiscoverJSONArray to finish...")

        /* Initialize Recyclerview, LayoutManager, and Adapters */
        mRecyclerView = view?.findViewById(R.id.searchRecyclerView)
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = LinearLayoutManager(context)

        /* Initialize the LayoutManager*/
        mLayoutManager = LinearLayoutManager(activity)

        val editText_search = requireActivity().findViewById<EditText>(R.id.textView_searchbar)
        textInput = editText_search.text.toString()

        getResultJSONArray()

        Handler().postDelayed({
            //traverse in result array
            for (i in 0..19) {
                try {
                val movieObj = searchResult?.getJSONObject(i)
                Log.d("movieObj", "movieObj num $i has the title: ${movieObj?.getString("title")}")

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
            mRecyclerView?.adapter = MovieAdapter(dataArray) { position -> onListItemClick(position) }

        }, 2000) //2 sec
    }

    private fun onListItemClick(position: Int) {

        val intent = Intent(activity, MovieDisplayActivity::class.java)
        val bundle = requireActivity().getIntent().getExtras()
        val user: ParseUser = bundle?.get("user") as ParseUser
        intent.putExtra("user", user)

        val selectedMovie = dataArray.get(position)

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
    private fun getResultJSONArray() {

        val encodedString = URLEncoder.encode(textInput, "utf-8")

        val movies_url = "https://api.themoviedb.org/3/search/movie?" +
                "api_key=${getString(R.string.tmdb_app_key)}" +
                "&language=en-US" +
                "&query=$encodedString" +
                "&page=$pageNum" +
                "&include_adult=false"

        Log.d("movies_url", "The movie url is below:\n $movies_url")

        /* Volley Request for getting JSON response */
        val requestQueue = Volley.newRequestQueue(activity)

        val request = JsonObjectRequest(
            Request.Method.GET,movies_url, null,
            /*Response.Listener<JSONObject>*/ {
                    response ->
                Log.d("json", "Success!!")
                searchResult = response.getJSONArray("results")

            },
            /*Response.ErrorListener*/ {
                Log.e("json", "Error on retrieving the JSONArray response")
            })

        requestQueue.add(request)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}