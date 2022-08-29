package com.example.hackernews.ui.topstories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernews.data.datasource.OnNewsTaskDoneListener
import com.example.hackernews.data.datasource.OnTopStoriesTaskDoneListener
import com.example.hackernews.data.repository.TopStoriesRepository
import com.example.hackernews.models.NewsModel
import com.example.hackernews.models.TopStoriesAdapterModel
import kotlinx.coroutines.launch

class TopStoriesViewModel : ViewModel(), OnTopStoriesTaskDoneListener {

    val topStoriesIdList: MutableLiveData<ArrayList<Int>?> = MutableLiveData()
    val topStoriesAdapterModel: MutableLiveData<TopStoriesAdapterModel> = MutableLiveData()
    val topStoriesIdListError: MutableLiveData<Boolean> = MutableLiveData()
    val toastError: MutableLiveData<Boolean> = MutableLiveData()

    private val topStoriesRepository = TopStoriesRepository()

    fun getTopStories() {
        viewModelScope.launch {
            topStoriesRepository.fetchTopStories(this@TopStoriesViewModel)
        }
    }

    override fun onTaskDone(responseData: ArrayList<Int>?) {
        topStoriesIdList.postValue(responseData)
    }

    override fun onError() {
        topStoriesIdListError.postValue(true)
    }

    fun getNewsFromTopStories(id: Int, position: Int) {
        viewModelScope.launch {
            topStoriesRepository.fetchNewsFromTopStories(object : OnNewsTaskDoneListener {
                override fun onNewsTaskDone(responseData: NewsModel) {
                    topStoriesAdapterModel.value = TopStoriesAdapterModel(
                        id,
                        position,
                        responseData
                    )
                }

                override fun onNewsTaskError() {
                    topStoriesAdapterModel.value = TopStoriesAdapterModel(
                        id,
                        position,
                        NewsModel(isError = true)
                    )
                }
            }, id)
        }
    }

    fun getListOfIdsFromDb() {
        viewModelScope.launch {
            topStoriesRepository.fetchTopStories(object : OnTopStoriesTaskDoneListener {
                override fun onTaskDone(responseData: ArrayList<Int>?) {
                    topStoriesIdList.postValue(responseData)
                }

                override fun onError() {
                    toastError.postValue(true)
                }
            })
        }
    }
}