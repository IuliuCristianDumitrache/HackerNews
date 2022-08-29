package com.example.hackernews.data.datasource

import android.os.AsyncTask
import android.util.Log
import org.apache.commons.io.IOUtils
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class GetTopStoriesRemoteTask(private val onTaskDoneListener: OnTopStoriesTaskDoneListener) :
    AsyncTask<String, Void, ArrayList<Int>>() {

    companion object {
        const val TIMEOUT_VALUE = 100000
        const val URL = "https://hacker-news.firebaseio.com/v0/topstories.json"
    }

    override fun doInBackground(vararg params: String?): ArrayList<Int>? {
        try {
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
                val jsonArray = JSONArray(responseString)
                val listData = arrayListOf<Int>()

                for (i in 0 until jsonArray.length()) {
                    listData.add(jsonArray.getInt(i))
                }

                return listData
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(list: ArrayList<Int>?) {
        super.onPostExecute(list)
        Log.d("POSTEXECUTE", "$list")
        if (list != null) {
            onTaskDoneListener.onTaskDone(list)
        } else {
            onTaskDoneListener.onError()
        }
    }
}