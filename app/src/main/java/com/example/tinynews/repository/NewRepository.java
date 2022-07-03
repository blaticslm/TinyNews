package com.example.tinynews.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tinynews.TinyNewsApplication;
import com.example.tinynews.database.TinyNewsDatabase;
import com.example.tinynews.model.Article;
import com.example.tinynews.model.NewsResponse;
import com.example.tinynews.network.NewsApi;
import com.example.tinynews.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRepository {

    private final NewsApi newsApi;
    private TinyNewsDatabase database;

    public NewRepository() {
        newsApi =  RetrofitClient.newInstance().create(NewsApi.class);
        database = TinyNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) { //有关于多线程，一会注意重点总结

        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();

        newsApi.getTopHeadlines(country).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if(response.isSuccessful()) {
                    topHeadlinesLiveData.setValue(response.body());
                } else {
                    topHeadlinesLiveData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                topHeadlinesLiveData.setValue(null);
            }
        });

        return topHeadlinesLiveData; //MutableLiveData extends LiveData: execute first
    }

    public LiveData<NewsResponse> getEverything(String query) {

        MutableLiveData<NewsResponse> everythingLiveData = new MutableLiveData<>();

        newsApi.getEverything(query, 40).enqueue(new Callback<NewsResponse>() { //Multithreading
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    everythingLiveData.setValue(response.body());
                } else {
                    everythingLiveData.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                everythingLiveData.setValue(null);
            }
        });

        return everythingLiveData;
    }

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> success = new MutableLiveData<>();
        new FavoriteAsyncTask(database, success).execute(article);
        return success;
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles(); //Livedata helps me to transfer from background thread
    }

    public void deleteSavedArticle(Article article){
        //trying different ways to use AsyncTask
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                database.articleDao().deleteArticle(article);
            }
        });

    }

    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> { //important

        private final TinyNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinyNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }

        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }




}
