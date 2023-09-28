package com.example.testapplication.Fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testapplication.Adapter.MovieListAdapter
import com.example.testapplication.ViewModel.HomeViewModel
import com.example.testapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment:BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var movieAdapter: MovieListAdapter
    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun setObservers() {
        bi.searchView.clearFocus()
        bi.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                    movieAdapter.filter.filter(newText)
                return true
            }
        })

homeViewModel.loadingState.observe(viewLifecycleOwner) { it ->
    when (it) {
        1 -> {
            lifecycleScope.launch {
                val movie =    homeViewModel.getAllResults()
                movieAdapter = MovieListAdapter(movie, requireContext())
                movieAdapter.notifyDataSetChanged()
                bi.recyclerView.adapter = movieAdapter
                bi.progressBar.visibility = View.INVISIBLE
                bi.recyclerView.visibility = View.VISIBLE

            }
        }
        2->{
            homeViewModel.movieList.observe(viewLifecycleOwner ){
                movieAdapter = MovieListAdapter(it.results, requireContext())
                movieAdapter.notifyDataSetChanged()
                bi.recyclerView.adapter = movieAdapter
                bi.progressBar.visibility = View.INVISIBLE
                bi.recyclerView.visibility = View.VISIBLE
            }
        }


    }
}
    }


    override fun init() {


    }
    override fun setEvents() {

    }


}