package com.example.seamlabstask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seamlabstask.handleData.ErrorLiveData
import com.example.seamlabstask.ui.model.ArticlesItem
import com.example.seamlabstask.ui.repository.HomeRepository

class HomeViewModel : ViewModel() {

    private val homeRepo = HomeRepository()

    val newsSourceLive: LiveData<List<ArticlesItem>> = homeRepo.newsSourceMutable
    val handleData: ErrorLiveData<String> = homeRepo.handleData

    fun getNewsSource() {
        homeRepo.getNewsSource()
    }

}