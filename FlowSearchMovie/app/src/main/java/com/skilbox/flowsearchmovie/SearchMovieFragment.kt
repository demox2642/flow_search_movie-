package com.skilbox.flowsearchmovie

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilbox.flowsearchmovie.adapter.MovieListAdapter
import com.skilbox.flowsearchmovie.databinding.FragmentSearchMovieBinding
import com.skilbox.flowsearchmovie.vm.MovieViewModel
import kotlinx.coroutines.launch

class SearchMovieFragment : ViewBindingFragment<FragmentSearchMovieBinding>(FragmentSearchMovieBinding::inflate) {
    private val viewModel: MovieViewModel by viewModels()
    private var movieAdapter: MovieListAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        bindViewModel()

        binding.allMoviesFragment.setOnClickListener {
            findNavController().navigate(R.id.action_searchMovieFragment_to_allMoviesFragment)
        }

        lifecycleScope.launch {
            viewModel.search(
                binding.radioGroup.changeChoiceFlow(),
                binding.searchEditText.textChangedFlow()
            )
        }
    }

    private fun bindViewModel() {

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) { movieAdapter?.items = it }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.movieList.isVisible = isLoading.not()
        binding.progressBar.isVisible = isLoading
    }

    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(binding.movieList) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}
