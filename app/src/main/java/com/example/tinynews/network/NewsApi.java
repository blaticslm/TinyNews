package com.example.tinynews.network;


import com.example.tinynews.model.NewsResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

public interface NewsApi {

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country);

    //https://newsapi.org/v2/everything?q=STH&from=2022-05-24&sortBy=publishedAt&apiKey=THE_TOKEN
    //Everything mentioned by STH
    //pageSize is how many content in one page
    @GET("everything")
    Call<NewsResponse> getEverything(@Query("q") String query, @Query("pageSize") int pageSize);



}
