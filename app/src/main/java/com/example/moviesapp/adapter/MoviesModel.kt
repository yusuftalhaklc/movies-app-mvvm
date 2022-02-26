package com.example.moviesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.model.Result
import com.example.moviesapp.util.downloadFromUrl
import com.example.moviesapp.view.FeedFragmentDirections
import com.example.moviesapp.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.feed_row.view.*

class MoviesModelAdapter (private val MoviesModelList: ArrayList<Result>,view:ViewModelStoreOwner,var fragmentView:View,var stat:String = "f1") :
    RecyclerView.Adapter<MoviesModelAdapter.MoviesModelViewHolder>() {

    private var viewModel :DetailsViewModel =ViewModelProvider(view).get(DetailsViewModel::class.java)

    class MoviesModelViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesModelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.feed_row, parent, false)
        return MoviesModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesModelViewHolder, position: Int) {

        if(stat == "f1"){
            holder.view.movieTextView.text = MoviesModelList[position].title
            holder.view.rateBarTextView.text = MoviesModelList[position].vote_average.toString()
            holder.view.ratingBar.rating = (MoviesModelList[position].vote_average.toFloat() / 2)

            if(MoviesModelList[position].backdrop_path !=null){
                holder.view.movieImageView.downloadFromUrl(MoviesModelList[position].backdrop_path)
            }
            holder.view.itemRow.setOnClickListener{
                val action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(MoviesModelList[position].id)
                Navigation.findNavController(fragmentView).navigate(action)
            }

        }
        else {
            holder.view.movieTextView.text = MoviesModelList[position].title
            holder.view.rateBarTextView.text = MoviesModelList[position].vote_average.toString()
            holder.view.ratingBar.rating = (MoviesModelList[position].vote_average.toFloat() / 2)

            if(MoviesModelList[position].backdrop_path !=null){
                holder.view.movieImageView.downloadFromUrl(MoviesModelList[position].backdrop_path)
            }
            holder.view.itemRow.setOnClickListener{
                viewModel.getSimilar(MoviesModelList[position].id)
                viewModel.getData(MoviesModelList[position].id)
            }


        }




    }

    override fun getItemCount(): Int {
        return MoviesModelList.size
    }

    fun refreshList(newList : List<Result>){
        MoviesModelList.clear()
        MoviesModelList.addAll(newList)
        notifyDataSetChanged()
    }


}