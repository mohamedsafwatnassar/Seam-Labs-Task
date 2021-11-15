package com.example.seamlabstask.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seamlabstask.roomdatabase.dao.NewsDao
import com.example.seamlabstask.ui.model.ArticlesItem
import com.example.seamlabstask.ui.model.Source

@Database(entities = [ArticlesItem::class], version = 6, exportSchema = false)
@TypeConverters(Converter::class)
abstract class LocalDataBase() : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        private var INSTANCE: LocalDataBase? = null

        fun getInstance(): LocalDataBase? {
            if (INSTANCE == null)
                throw  NullPointerException("database is null")
            return INSTANCE
        }

        fun init(context: Context) {
            synchronized(LocalDataBase::class) {
                INSTANCE =
                    Room.databaseBuilder(context, LocalDataBase::class.java, "SeamLabsDataBase")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }
        }
    }

}




