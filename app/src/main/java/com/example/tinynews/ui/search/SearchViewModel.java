package com.example.tinynews.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.tinynews.model.NewsResponse;
import com.example.tinynews.repository.NewRepository;

public class SearchViewModel extends ViewModel{
    private final NewRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>(); //container for the changing

    public SearchViewModel(NewRepository newRepository) {
        this.repository = newRepository;
    }

    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }

    public LiveData<NewsResponse> searchNews() {
        //In most of time, I can directly use repository.getTopHeadlines(country) to get the latest content
        //but the logic is country first then get the top headline
        //this method does not need to have order requirement
        return Transformations.switchMap(searchInput, repository::getEverything); //everytime countryInput changes, repo changes
    }

}
