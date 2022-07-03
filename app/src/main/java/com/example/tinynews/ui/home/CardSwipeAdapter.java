package com.example.tinynews.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinynews.R;
import com.example.tinynews.TinyNewsApplication;
import com.example.tinynews.databinding.SwipeNewsCardBinding;
import com.example.tinynews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder>{
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CardSwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_news_card, parent, false);
        return new CardSwipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardSwipeViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.title);
        holder.descriptionTextView.setText(article.description);
        try{
            Picasso.get().load(article.urlToImage).resize(400,400).into(holder.cardImageView);
        } catch(Exception e) {
            Picasso.get().load(TinyNewsApplication.UNIVERSAL_URL).into(holder.cardImageView);
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class CardSwipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView cardImageView;

        public CardSwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            SwipeNewsCardBinding binding = SwipeNewsCardBinding.bind(itemView);
            titleTextView = binding.swipeCardTitle;
            descriptionTextView = binding.swipeCardDescription;
            cardImageView = binding.swipeCardImageView;
        }
    }
}
