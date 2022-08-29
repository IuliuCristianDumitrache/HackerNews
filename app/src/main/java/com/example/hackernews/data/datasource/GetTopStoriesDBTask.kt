package com.example.hackernews.data.datasource

import android.os.AsyncTask
import android.util.Log
import com.example.hackernews.data.DatabaseHelper

class GetTopStoriesDBTask(private val onTaskDoneListener: OnTopStoriesTaskDoneListener) :
    AsyncTask<String, Void, ArrayList<Int>>() {

    override fun doInBackground(vararg params: String?): ArrayList<Int>? {
        try {
            val listOfIds = arrayListOf<Int>()
            DatabaseHelper.database.topStoriesDao().getTopStories().forEach {
                listOfIds.add(it)
            }
            return listOfIds
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