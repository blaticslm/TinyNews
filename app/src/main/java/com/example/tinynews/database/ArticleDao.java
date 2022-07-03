package com.example.tinynews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tinynews.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    //livedata deeply works with DB. It has ability to post from background thread to main/UI thread
    @Query("SELECT * FROM Article")
    LiveData<List<Article>> getAllArticles();

    @Insert
    void saveArticle(Article article);

    @Delete
    void deleteArticle(Article article);
}
