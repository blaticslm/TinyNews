package com.example.tinynews.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tinynews.model.Article;
import com.example.tinynews.repository.NewRepository;

import java.util.List;

public class SaveViewModel extends ViewModel {
    private final NewRepository repository;

    public SaveViewModel(NewRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Article>> getAllsavedArticles() {
        return repository.getAllSavedArticles();
    }

    public void deleteSavedArticle(Article article) {
        repository.deleteSavedArticle(article);
    }
}
