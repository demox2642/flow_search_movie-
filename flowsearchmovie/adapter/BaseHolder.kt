package com.skilbox.flowsearchmovie.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skilbox.flowsearchmovie.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movie_vie_for_list.*
import kotlinx.android.synthetic.main.movie_vie_for_list.view.*

abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    protected fun bindMainInfo(
        id: String,
        title: String,
        year: String,
        type: String,
        poster: String
    ) {
        movie_title.text = title
        movie_year.text = year
        movie_id.text = id
        movie_type.text = type

        Glide.with(movie_image)
            .load(poster)
            .placeholder(R.drawable.load_imege)
            .into(itemView.movie_image)
            .view
    }
}
