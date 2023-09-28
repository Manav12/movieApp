package com.example.testapplication.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.Api.BASE_BACKDROP_IMAGE_URL
import com.example.testapplication.Constant.loadImageUrl
import com.example.testapplication.Model.Result
import com.example.testapplication.R
import java.util.Locale


class MovieListAdapter(private val popularData: List<Result>, private val context: Context) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>(), Filterable {

    private var filteredItemList: MutableList<Result> = ArrayList(popularData)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.moviePoster)
        val movieName: TextView = itemView.findViewById(R.id.movieName)
        val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_tile,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filteredItemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val getData = filteredItemList.get(position)
        holder.releaseDate.text = getData.release_date
        holder.movieName.text = getData.title
        holder.image.loadImageUrl(BASE_BACKDROP_IMAGE_URL + getData.poster_path)

        val bundle = Bundle()

        holder.itemView.setOnClickListener {
            bundle.putSerializable("USER", filteredItemList[position])
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                val filteredList = ArrayList<Result>()

                if (filterPattern.isEmpty()) {
                    filteredList.addAll(popularData)
                } else {
                    for (item in popularData) {
                        if (item.title.lowercase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                    if(filteredList.isEmpty()){
                        Toast.makeText(context, "No movie available", Toast.LENGTH_SHORT).show()
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItemList.clear()
                filteredItemList.addAll(results?.values as List<Result>)
                notifyDataSetChanged()
            }
        }
    }
}
