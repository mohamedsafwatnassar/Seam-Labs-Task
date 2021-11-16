package com.example.seamlabstask.ui.repository

import com.example.seamlabstask.BuildConfig
import com.example.seamlabstask.apis.ApiManager
import com.example.seamlabstask.handleData.ErrorLiveData
import com.example.seamlabstask.roomdatabase.LocalDataBase
import com.example.seamlabstask.ui.model.ArticlesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeRepository {

    private val apiManager = ApiManager().apis

    var handleData = ErrorLiveData<List<ArticlesItem>>()

    fun getNewsSource() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiManager.getNewsSource("eg", BuildConfig.apiKey)
                if (response.isSuccessful) {
                    cacheNews(response.body()!!.articles)
                    handleData.postSuccess(response.body()!!.articles)
                } else {
                    handleData.postError("Something went wrong")
                    getNewsFromCache()
                }
            } catch (error: Exception) {
                handleData.postConnectionError("Internet Connection Failure")
                getNewsFromCache()
            }
        }

    }

    private fun cacheNews(articles: List<ArticlesItem>) {
        LocalDataBase.getInstance()!!.newsDao().addAllArticles(articles)
    }

    private fun getNewsFromCache() {
        val getArticlesList: List<ArticlesItem> =
            LocalDataBase.getInstance()!!.newsDao().getAllArticles()
        handleData.postSuccess(getArticlesList)
    }
}