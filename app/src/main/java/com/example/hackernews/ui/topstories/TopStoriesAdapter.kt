package com.example.hackernews.ui.topstories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.R
import com.example.hackernews.databinding.TopStoriesListItemBinding
import com.example.hackernews.models.NewsModel
import com.example.hackernews.models.TopStoriesAdapterModel
import com.example.hackernews.utils.DateUtils
import org.joda.time.DateTime

class TopStoriesAdapter(private val listener: OnTopStoriesItemListener?) :
    ListAdapter<Int, TopStoriesAdapter.TopStoriesViewHolder>(TopStoriesDiffCallback()) {

    val newsModelMap: HashMap<Int, NewsModel> = HashMap()

    override fun onBindViewHolder(holder: TopStoriesViewHolder, position: Int) {
        val newsModelId = getItem(position)
        holder.bind(newsModelId, position, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        val binding =
            TopStoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopStoriesViewHolder(binding)
    }

    inner class TopStoriesViewHolder(private val binding: TopStoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsModelId: Int?, position: Int, listener: OnTopStoriesItemListener?) {
            if (newsModelMap.containsKey(newsModelId)) {
                val item = newsModelMap[newsModelId]
                if (item != null) {
                    binding.progressBar.visibility = View.INVISIBLE
                    if (item.isError) {
                        clearHolder()
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.tvTitle.text =
                            binding.root.context.getString(R.string.error_getting_item)
                    } else {
                        ViewCompat.setTransitionName(binding.root, "${item.id}${item.time}")

                        binding.tvDate.text = DateUtils.formatDateUserFriendly(
                            DateTime(item.getTimeAsMillis()),
                            binding.root.context
                        )
                        binding.tvTitle.text = item.title

                        binding.root.setOnClickListener {
                            listener?.onTopStoriesItemClicked(binding, item)
                        }
                    }
                } else {
                    clearHolder()
                }
            } else {
                clearHolder()
                getNewsModel(newsModelId, position)
            }
        }

        private fun clearHolder() {
            binding.progressBar.visibility = View.VISIBLE
            binding.tvTitle.text = ""
            binding.tvDate.text = ""
            binding.root.setOnClickListener(null)
        }

        private fun getNewsModel(newsModelId: Int?, position: Int) {
            listener?.getNewsModel(newsModelId, position)
        }
    }

    interface OnTopStoriesItemListener {
        fun getNewsModel(newsModelId: Int?, position: Int)

        fun onTopStoriesItemClicked(
            binding: TopStoriesListItemBinding,
            item: NewsModel
        )
    }

    fun updateListOfNewsModel(topStoriesModel: TopStoriesAdapterModel) {
        newsModelMap[topStoriesModel.id] = topStoriesModel.newsModel
        notifyItemChanged(topStoriesModel.position)
    }
}