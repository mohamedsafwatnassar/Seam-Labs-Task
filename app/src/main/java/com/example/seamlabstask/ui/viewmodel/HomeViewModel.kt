package com.example.seamlabstask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.seamlabstask.handleData.ErrorLiveData
import com.example.seamlabstask.ui.model.ArticlesItem
import com.example.seamlabstask.ui.repository.HomeRepository

class HomeViewModel : ViewModel() {

    private val homeRepo = HomeRepository()

    val handleData: ErrorLiveData<List<ArticlesItem>> = homeRepo.handleData

    var articleItem = ArticlesItem()

    fun getNewsSource() {
        homeRepo.getNewsSource()
    }

}