/* https://developer.android.com/guide/topics/ui/layout/recyclerview and
*    check https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList
*
*  JSONObjectRequest for triggering api requests based on URL. https://www.kompulsa.com/how-to-send-a-get-request-in-android/#:~:text=Another%20type%20of%20GET%20request%20you%E2%80%99re%20likely%20to,of%20a%20StringRequest%2C%20which%20is%20designed%20for%20strings.
*  JSON library for processing JSON format  https://www.tutorialspoint.com/android/android_json_parser.htm#:~:text=JSON%20-%20Parsing%20%20%20%20Sr.No%20,method%20returns%20...%20%203%20more%20rows%20
*  Android's page https://developer.android.com/training/volley/
*
* some help on different oncreates in fragments
* https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra#:~:text=The%20onCreate%20%28%29%20method%20in%20a%20Fragment%20is,doesn%27t%20involve%20the%20View%20hierarchy%20%28i.e.%20non-graphical%20initialisations%29.
*
*
*  also this is the request link in movieDB for discover:
* https://api.themoviedb.org/3/discover/movie?api_key=34872395426d9e0ba548d1d51cbd6c10&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate
*
* need to understand the ViewHolder which is the individual item viewHolder objects..
* need to create: Adapters, ListViewModels?, viewHolder? look on the recycler view doc at the top and below here:
*
* https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/app/src/main/java/com/example/recyclersample/flowerList
*
*
*  */
package com.example.instaflix.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.instaflix.R
import com.example.instaflix.databinding.FragmentHomeBinding

import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import com.example.instaflix.helper.RequestHelper

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var discoverResult: JSONArray? = null

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

        /*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        */

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            Response.Listener<JSONObject>{
            response ->
                Log.d("json", "Success!!")
                discoverResult = response.getJSONArray("results")

        },
        Response.ErrorListener {
            Log.e("json", "Error on retrieveing the JSONArray response")
        })
        //access the requestQueue
        requestQueue.add(request)



       /* Instantiate Adapters and any UI component */
        // val homeAdapter = HomeAdapter { movie -> adapterOnClick() }

        // val recyclerView: RecyclerView = getView().findViewById(R.id.homeRecyclerView)
        // recyclerView.adapter = homeAdapter

        /* add ListViewModel stuff */
        //the stuff that is in ListViewModel is a global variable


    }

    private fun adapterOnClick() {
        /*
        val intent = Intent(activity, movieDisplayActivity::class.java)
        startActivity(intent)
        */
        Toast.makeText(context, "itemOnCLick pressed", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}