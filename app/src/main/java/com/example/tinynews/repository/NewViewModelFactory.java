package com.example.tinynews.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tinynews.ui.home.HomeViewModel;
import com.example.tinynews.ui.save.SaveViewModel;
import com.example.tinynews.ui.search.SearchViewModel;

//this is for retaining the UI state
public class NewViewModelFactory implements ViewModelProvider.Factory {

    private final NewRepository repository;

    public NewViewModelFactory(NewRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(repository);
        } else if(modelClass.isAssignableFrom(SaveViewModel.class)){
            return (T) new SaveViewModel(repository);
        } else {
            throw new IllegalStateException("Unknown ViewModel");
        }
    }
}


