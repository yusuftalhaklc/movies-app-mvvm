package com.example.moviesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MoviesModelAdapter
import com.example.moviesapp.util.downloadFromUrl
import com.example.moviesapp.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_feed.*

class DetailsFragment : Fragment() {
    private lateinit var viewModel:DetailsViewModel
    lateinit var adapter: MoviesModelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // ID = R.layout.fragment_name
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviesModelAdapter(arrayListOf(), this ,view ,"f2")

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        detailsRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        detailsRecyclerView.adapter = adapter

        arguments?.let {
            val id = DetailsFragmentArgs.fromBundle(it).movieID
            viewModel.getData(id)
            viewModel.getSimilar(id)
        }
        detailBackButton.setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }



        observeData()

    }

    fun observeData(){

        viewModel.similarLiveData.observe(viewLifecycleOwner, Observer {
            adapter.refreshList(it.results)
        })

        viewModel.detailLiveData.observe(viewLifecycleOwner, Observer {
            mainView.visibility = View.VISIBLE

            detailImageView.downloadFromUrl(it.poster_path)

            detailMovieTextView.text = it.title

            DetailRatingBar.rating = (it.vote_average.toFloat()/2)

            DetailRateBarTextView.text = it.vote_average.toString()

            overviewText.text = it.overview


        })
        viewModel.detailMoviesLoading.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it){
                    detailLoadingBar.visibility = View.VISIBLE
                    detailErrorTextView.visibility = View.GONE

                    mainView.visibility = View.GONE
                }
                else {
                    detailLoadingBar.visibility = View.GONE
                    detailScrollView.scrollTo(0,0)

                }
            }
        })
        viewModel.detailMoviesError.observe(viewLifecycleOwner, Observer {
            it?.let{
                if(it){
                    mainView.visibility = View.GONE

                    detailLoadingBar.visibility = View.GONE
                    detailErrorTextView.visibility = View.VISIBLE
                }
                else {
                    detailErrorTextView.visibility = View.GONE
                }
            }
        })
    }

}