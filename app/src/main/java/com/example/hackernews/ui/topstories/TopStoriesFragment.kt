package com.example.hackernews.ui.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentTopStoriesBinding
import com.example.hackernews.databinding.TopStoriesListItemBinding
import com.example.hackernews.extensions.disposeIfNotAlready
import com.example.hackernews.extensions.navigateSafely
import com.example.hackernews.extensions.observe
import com.example.hackernews.models.NewsModel
import com.example.hackernews.network.RxBus
import com.example.hackernews.network.rxmessages.ReloadTopStories
import io.reactivex.disposables.Disposable

class TopStoriesFragment : Fragment(), TopStoriesAdapter.OnTopStoriesItemListener {

    private val viewModel: TopStoriesViewModel by viewModels()
    private var views: FragmentTopStoriesBinding? = null
    private val topStoriesAdapter = TopStoriesAdapter(this)

    private var reloadSubscriber: Disposable? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentTopStoriesBinding.bind(view)

        initRecyclerView()

        observe(viewModel.topStoriesIdList) { listOfIds ->
            topStoriesAdapter.submitList(listOfIds)
        }

        observe(viewModel.topStoriesIdListError) { error ->
            if (error) {
                viewModel.getListOfIdsFromDb()
            }
        }

        observe(viewModel.topStoriesAdapterModel) {
            (views?.rvNews?.adapter as TopStoriesAdapter?)?.updateListOfNewsModel(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            if (viewModel.topStoriesIdList.value == null) {
                viewModel.getTopStories()
            }
        }

        initSubscriber()
    }

    private fun initSubscriber() {
        reloadSubscriber = RxBus.listen(ReloadTopStories::class.java).subscribe {
            if (it.reload) {
                viewModel.getTopStories()
            }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        views?.rvNews?.layoutManager = linearLayoutManager
        views?.rvNews?.adapter = topStoriesAdapter
    }

    override fun getNewsModel(newsModelId: Int?, position: Int) {
        if (newsModelId != null) {
            viewModel.getNewsFromTopStories(newsModelId, position)
        }
    }

    override fun onTopStoriesItemClicked(
        binding: TopStoriesListItemBinding,
        item: NewsModel
    ) {
        val extras = FragmentNavigatorExtras(
            binding.root to "news_root_transaction"
        )
        val topStoriesDirection = TopStoriesFragmentDirections.actionTopStoriesFragmentToNewsFragment(
            news = item
        )
        findNavController().navigateSafely(
            topStoriesDirection, extras
        )
    }

    override fun onDestroy() {
        reloadSubscriber?.disposeIfNotAlready()
        super.onDestroy()
    }
}