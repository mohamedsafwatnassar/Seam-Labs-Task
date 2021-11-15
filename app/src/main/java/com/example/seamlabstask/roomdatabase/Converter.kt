package com.example.seamlabstask.roomdatabase

import androidx.room.TypeConverter
import com.example.seamlabstask.ui.model.ArticlesItem
import com.example.seamlabstask.ui.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converter {

    @TypeConverter
    fun fromListToString(articlesItem: List<ArticlesItem>): String? {
        val gson = Gson()
        return gson.toJson(articlesItem)
    }

    @TypeConverter
    fun fromStringToList(articlesItem: String?): List<ArticlesItem> {
        val type: Type = object : TypeToken<ArticlesItem>() {}.type
        return Gson().fromJson(articlesItem, type)
    }

    @TypeConverter
    fun fromSource(source: Source): String? {
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun toSource(source: String?): Source {
        val type: Type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(source, type)
    }
}