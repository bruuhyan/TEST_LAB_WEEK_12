package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test_lab_week_12.api.MovieService
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieRepository(private val movieService: MovieService) {
    private val apiKey = "6dc6cb9dd7e60e2827af4228638dd37b"
    // fetch movies from the API
    // returns Flow<List<Movie>>
    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            val response = movieService.getPopularMovies(apiKey)
            emit(response.results)
        }
            .flowOn(Dispatchers.IO)
            .map { movies ->
                // SORTING (descending by popularity)
                movies.sortedByDescending { it.popularity }
            }
    }
}