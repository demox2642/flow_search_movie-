package com.skilbox.flowsearchmovie.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.skilbox.flowsearchmovie.MovieType
import com.skilbox.flowsearchmovie.RemoteMovie
import com.skilbox.flowsearchmovie.database.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import okhttp3.Call
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MovieViewModel : ViewModel() {
    private val repository = MoviesRepository()

    private var currentCall: Call? = null

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
    val movieList: LiveData<List<RemoteMovie>>
        get() = movieListLiveData

    private val allMovieListLiveData = MutableLiveData<List<RemoteMovie>>()
    val allMovieList: LiveData<List<RemoteMovie>>
        get() = allMovieListLiveData

    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    private var currentJob: Job? = null

    private var currentJobDB: Job? = null

    suspend fun search(queryFlow: Flow<MovieType>, movieTypeFlow: Flow<String>) {
        var title: String = ""
        var type: String = ""

        currentJob = combine(
            queryFlow,
            movieTypeFlow
        ) { query, type -> query to type }

            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                Log.e("VM Search", "search $it")

                isLoadingLiveData.postValue(true)
            }
            .mapLatest {
                title = it.second
                type = it.first.toString()
                repository.searchMovie(it.first, it.second)
            }
            .flowOn(Dispatchers.IO)
            .onEach { it ->
                isLoadingLiveData.value = false
                Log.e("Error", "i'm in onEach")
                Database.instance.withTransaction {
                    repository.loadMoviesToDB(it)
                }

                Log.e("VM Search on Ech", "search $it")
                movieListLiveData.postValue(it)
                Log.e("VM Search on Ech", "I'm hear movieListLiveData=${movieListLiveData.value?.size}")
            }
            .catch { it ->
                Log.e("Error start", "I'm hear movieListLiveData=${movieList.value?.size}")
                if (it !is SocketTimeoutException || it !is UnknownHostException) {
                    isLoadingLiveData.postValue(false)
                    Database.instance.withTransaction {
                        movieListLiveData.postValue(repository.getMovie(title, type))
                    }
                }
                Log.e("Error end 2", "I'm hear movieListLiveData=${movieList.value?.size}")
                Log.e("Error", "$it")
            }
            .launchIn(viewModelScope)
    }

    fun observeMovies() {
        currentJobDB = repository.observeMovies()
            .flowOn(Dispatchers.IO)
            .onEach { it ->
                Database.instance.withTransaction {
                    allMovieListLiveData.postValue(repository.getAllMovies())
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}
