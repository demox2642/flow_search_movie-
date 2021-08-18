package com.skilbox.flowsearchmovie

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilbox.flowsearchmovie.adapter.MovieListAdapter
import com.skilbox.flowsearchmovie.databinding.FragmentAllMoviesBinding
import com.skilbox.flowsearchmovie.vm.MovieViewModel

class AllMoviesFragment : ViewBindingFragment<FragmentAllMoviesBinding>(FragmentAllMoviesBinding::inflate) {
    private val viewModel: MovieViewModel by viewModels()
    private var movieAdapter: MovieListAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        bindViewModel()
        viewModel.observeMovies()
    }

    private fun bindViewModel() {

        viewModel.allMovieList.observe(viewLifecycleOwner) { movieAdapter?.items = it }
    }

    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(binding.allMoviesFragmentmovieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
