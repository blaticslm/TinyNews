package com.example.tinynews.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tinynews.model.Article;

@Database(entities = {Article.class},version = 1, exportSchema = false) //exportSchema is to set in folder
public abstract class TinyNewsDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
