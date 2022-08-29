package com.example.hackernews.data.datasource

import android.os.AsyncTask
import android.util.Log
import com.beust.klaxon.Klaxon
import com.example.hackernews.data.DatabaseHelper
import com.example.hackernews.data.TopStoriesEntity
import com.example.hackernews.models.NewsModel
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class GetNewsRemoteTask(private val onTaskDoneListener: OnNewsTaskDoneListener) :
    AsyncTask<Int, Void, NewsModel>() {

    companion object {
        const val TIMEOUT_VALUE = 100000
    }

    override fun doInBackground(vararg params: Int?): NewsModel? {
        try {
            val dataBaseNews = params[0]?.let { DatabaseHelper.database.newsDao().getNewsById(it) }

            if (dataBaseNews != null) {
                return dataBaseNews.toNewsModel()
            } else {
                val URL = "https://hacker-news.firebaseio.com/v0/item/${params[0]}.json"
                val mUrl = URL(URL)
                val httpConnection: HttpURLConnection = mUrl.openConnection() as HttpURLConnection
                httpConnection.requestMethod = "GET"
                httpConnection.useCaches = false
                httpConnection.allowUserInteraction = false
                httpConnection.connectTimeout = TIMEOUT_VALUE
                httpConnection.readTimeout = TIMEOUT_VALUE
                httpConnection.connect()
                val responseCode: Int = httpConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseString = IOUtils.toString(httpConnection.inputStream)

                    val responseData = Klaxon().parse<NewsModel>(responseString)

                    val newsEntity = responseData?.mapNewsModelToNewsEntity()
                    if (newsEntity != null) {
                        DatabaseHelper.database.topStoriesDao()
                            .insert(TopStoriesEntity(newsId = newsEntity.newsId))
                        DatabaseHelper.database.newsDao().insert(newsEntity)
                    }

                    return responseData
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(newsModel: NewsModel?) {
        super.onPostExecute(newsModel)
        Log.d("POSTEXECUTE", "$newsModel")
        if (newsModel != null) {
            onTaskDoneListener.onNewsTaskDone(newsModel)
        } else {
            onTaskDoneListener.onNewsTaskError()
        }
    }
}