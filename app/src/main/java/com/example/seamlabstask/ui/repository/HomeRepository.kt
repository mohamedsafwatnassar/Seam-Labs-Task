package com.example.seamlabstask.ui.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.seamlabstask.BuildConfig
import com.example.seamlabstask.apis.ApiManager
import com.example.seamlabstask.handleData.ErrorLiveData
import com.example.seamlabstask.roomdatabase.LocalDataBase
import com.example.seamlabstask.roomdatabase.dao.NewsDao
import com.example.seamlabstask.ui.model.ArticlesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRepository {

    private val apiManager = ApiManager().apis

    val newsSourceMutable: MutableLiveData<List<ArticlesItem>> = MutableLiveData()

    var handleData = ErrorLiveData<String>()

    fun getNewsSource() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiManager.getNewsSource("eg", BuildConfig.apiKey)
                Log.d("getNewsSource", "" + response.body()!!.articles)

                if (response.isSuccessful) {
                    Log.d("getNewsSourceIF", "" + response.body()!!.articles)
                    cacheNews(response.body()!!.articles)
                    newsSourceMutable.postValue(response.body()!!.articles)
                }
            } catch (error: Exception) {
                Log.d("getNewsSourceException", "" + error.localizedMessage)
                //handleData.postConnectionError("Something went wrong")
                getNewsFromCache()
            }
        }

    }

    private fun cacheNews(articles: List<ArticlesItem>) {
        Log.d("cacheNews", "" + articles)

        LocalDataBase.getInstance()!!.newsDao().addAllArticles(articles)
    }

    private fun getNewsFromCache() {
        val getArticlesList: List<ArticlesItem> =
            LocalDataBase.getInstance()!!.newsDao().getAllArticles()
        Log.d("getNewsFromCache", "" + getArticlesList)

        newsSourceMutable.postValue(getArticlesList)
        //handleData.postSuccess(getArticlesList)
    }
}