package com.example.seamlabstask.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seamlabstask.ui.model.ArticlesItem

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllArticles(articlesItemList: List<ArticlesItem>)

    @Query("select * from articlesItem")
    fun getAllArticles(): List<ArticlesItem>

}