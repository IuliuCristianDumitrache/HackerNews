package com.example.hackernews.ui.news

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var views: FragmentNewsBinding? = null

    private val args by navArgs<NewsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentNewsBinding.bind(view)

        initUi()
        initListeners()
    }

    private fun initListeners() {
        views?.btnClose?.setOnClickListener {
            popBackStack()
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun initUi() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        args.news.url?.let { views?.webview?.loadUrl(it) }
    }
}