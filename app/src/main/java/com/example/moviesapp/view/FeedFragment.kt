package com.example.moviesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MoviesModelAdapter
import com.example.moviesapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {
    lateinit var viewModel:FeedViewModel
    lateinit var adapter:MoviesModelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // ID = R.layout.fragment_name
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviesModelAdapter(arrayListOf(),this, view)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        feedRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        feedRecyclerView.adapter = adapter

        observeData()



        feedSwiper.setOnRefreshListener {
            viewModel.refreshData()
            feedRecyclerView.visibility = View.GONE
            loadingBar.visibility = View.VISIBLE
            feedSwiper.isRefreshing = false
        }
        backButton.setOnClickListener{
            feedRecyclerView.visibility = View.GONE
            viewModel.minusPageNumber()
            viewModel.refreshData()

        }
        forwardButton.setOnClickListener{
            feedRecyclerView.visibility = View.GONE
            viewModel.addPageNumber()
            viewModel.refreshData()

        }


    }
    private fun observeData(){
        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            it?.let{
                pageCounter.text = "${it.page}/${it.total_pages}"
                adapter.refreshList(it.results)
                feedRecyclerView.visibility = View.VISIBLE
                errorTextView.visibility = View.GONE
                feedRecyclerView.scrollToPosition(0)

            }
        })
        viewModel.popularMoviesLoading.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it){
                    loadingBar.visibility = View.VISIBLE
                    errorTextView.visibility = View.GONE
                }
                else {
                    loadingBar.visibility = View.GONE
                }
            }
        })
        viewModel.popularMoviesError.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it){
                    feedRecyclerView.visibility = View.GONE

                    loadingBar.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                }
                else {
                    errorTextView.visibility = View.GONE
                }
            }
        })



    }

}