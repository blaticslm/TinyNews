package com.example.tinynews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.tinynews.model.Article;
import com.example.tinynews.model.NewsResponse;
import com.example.tinynews.repository.NewRepository;

public class HomeViewModel extends ViewModel {

    private final NewRepository repository;
    public final MutableLiveData<String> countryInput = new MutableLiveData<>(); //container for the changing

    public HomeViewModel(NewRepository newRepository) {
        this.repository = newRepository;
    }

    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        //In most of time, I can directly use repository.getTopHeadlines(country) to get the latest content
        //but the logic is country first then get the top headline
        //this method does not need to have order requirement
        return Transformations.switchMap(countryInput, repository::getTopHeadlines); //everytime countryInput changes, repo changes
    }

    public void setfavoriteArticle(Article article) {
        repository.favoriteArticle(article);
    }


}
