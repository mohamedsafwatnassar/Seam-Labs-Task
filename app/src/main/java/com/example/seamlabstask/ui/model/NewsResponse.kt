package com.example.seamlabstask.ui.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("totalResults")
    var totalResults: Int? = null,

    @field:SerializedName("articles")
    var articles: List<ArticlesItem>,

    @field:SerializedName("status")
    var status: String? = null
)

@Entity
data class ArticlesItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @field:SerializedName("publishedAt")
    var publishedAt: String? = null,

    @field:SerializedName("author")
    var author: String? = null,

    @field:SerializedName("urlToImage")
    var urlToImage: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("source")
    var source: Source? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("url")
    var url: String? = null,

    @field:SerializedName("content")
    var content: String? = null,
)

data class Source(

    @field:SerializedName("id")
    var id: Any? = "",

    @field:SerializedName("name")
    var name: String,
)
