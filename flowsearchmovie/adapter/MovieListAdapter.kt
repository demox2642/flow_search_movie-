package com.skilbox.flowsearchmovie.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skilbox.flowsearchmovie.RemoteMovie

class MovieListAdapter : AsyncListDifferDelegationAdapter<RemoteMovie>(MovieDiffUtilCallBack()) {

    init {
        delegatesManager.addDelegate(MovieDelegateAdapter())
    }

    class MovieDiffUtilCallBack : DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }
    }
}
