package com.skilbox.flowsearchmovie.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skilbox.flowsearchmovie.R
import com.skilbox.flowsearchmovie.RemoteMovie

class MovieDelegateAdapter() :
    AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieDelegateAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.movie_vie_for_list))
    }

    class MovieViewHolder(view: View) : BaseHolder(view) {

        fun bind(movie: RemoteMovie) {
            bindMainInfo(id = movie.id, title = movie.title, year = movie.year, type = movie.type.toString(), poster = movie.poster)
        }
        override val containerView: View
            get() = itemView
    }

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean {
        return items[position] is RemoteMovie
    }

    override fun onBindViewHolder(
        item: RemoteMovie,
        holder: MovieViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
