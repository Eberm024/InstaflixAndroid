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
*  */
package com.example.instaflix.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.instaflix.LogInActivity
import com.example.instaflix.R
import com.example.instaflix.data.Movie
import com.example.instaflix.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var discoverResult: JSONArray? = null
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    /* fix this by creating HomeListViewModel */
    /*
    private val homeListViewModel by viewModels<HomeViewModel> {
        HomeListViewModelFactory(this)
    }
    */


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

        /* copy of original from line 84
        * _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root */

        /*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        */

        getDiscoverJSONArray()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("waitTime", "Now waiting for 2 seconds for getDiscoverJSONArray to finish...")
        Handler().postDelayed({
            /* Initialize Recyclerview, LayoutManager, and Adapters */
            mRecyclerView = view?.findViewById(R.id.homeRecyclerView)
            mRecyclerView?.setHasFixedSize(true)
            mRecyclerView?.layoutManager = LinearLayoutManager(context)

            /* Initialize the LayoutManager*/
            mLayoutManager = LinearLayoutManager(activity)

            //ArrayList of class viewModel
            val data = ArrayList<Movie>()

            //1 page = 20 movies ***
            for (i in 0..19) {
                val movieObj = discoverResult?.getJSONObject(i)
                Log.d("movieObj", "movieObj num $i has the title: ${movieObj?.getString("title")}")

                //insert the data
                data.add(Movie(
                    movieObj?.getInt("id"),
                    movieObj?.getString("title"),
                    movieObj?.getString("poster_path"),
                    movieObj?.getString("backdrop_path"),
                    movieObj?.getString("overview"),
                    movieObj?.getString("release_date"),
                )) //end of adding data

            }

            /* Fill the recyclerview */
            mRecyclerView?.adapter = HomeAdapter(data)
            mRecyclerView?.adapter?.notifyDataSetChanged()

        }, 2000) //2 sec

        //ok the new goal now is to find a way to add a new item into the recycler view list
        // as it is the itemCount is 0, so I have to go and add 1 item...


    }

    private fun adapterOnClick() {
        /*
        val intent = Intent(activity, movieDisplayActivity::class.java)
        startActivity(intent)
        */
        Toast.makeText(context, "itemOnCLick pressed", Toast.LENGTH_SHORT).show()
    }

    /**
     * Helper function that helps retrieving the JSONObject from TMDB servers
     * Runs the following things: Volley, JSONObjectRequest
     * Modifies the following things: discoverResult
     */
    private fun getDiscoverJSONArray() {

        // quick testing
        //val movies_url = homeViewModel.discoverMovie_url
        val movies_url =
            "https://api.themoviedb.org/3/discover/movie?" +
                    "api_key=${getString(R.string.tmdb_app_key)}" +
                    "&language=en-US" +
                    "&sort_by=popularity.desc" +
                    "&include_adult=false" +
                    "&include_video=false" +
                    "&page=1" +
                    "&with_watch_monetization_types=flatrate"

        Log.d("movies_url", "The movie url is below:\n $movies_url")

        /* Volley Request for getting JSON response */
        var discoverResultsObject = JSONObject()
        val requestQueue = Volley.newRequestQueue(activity)
        var requestText = "empty"



        val request = JsonObjectRequest(Request.Method.GET,movies_url, null,
            /*Response.Listener<JSONObject>*/ {
                    response ->
                Log.d("json", "Success!!")
                discoverResult = response.getJSONArray("results")

            },
            /*Response.ErrorListener*/ {
                Log.e("json", "Error on retrieveing the JSONArray response")
            })
        //access the requestQueue
        requestQueue.add(request)
    }

    /**
     * Not sure if this is needed, but it feels the data for the first time
     *
     */
    private fun initData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}