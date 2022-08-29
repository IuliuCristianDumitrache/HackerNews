package com.example.hackernews.ui.topstories

import androidx.recyclerview.widget.DiffUtil

class TopStoriesDiffCallback : DiffUtil.ItemCallback<Int>() {

    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem::class.java.simpleName == newItem::class.java.simpleName

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
        oldItem == newItem
}