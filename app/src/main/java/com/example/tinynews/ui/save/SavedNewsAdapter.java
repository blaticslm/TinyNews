package com.example.tinynews.ui.save;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinynews.R;
import com.example.tinynews.databinding.SavedNewsItemBinding;
import com.example.tinynews.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SaveNewsViewHolder> {

    interface ItemCallBack {
        void onOpenDetails(Article article);
        void onRemoveFavorite(Article article);
    }

    private ItemCallBack itemCallBack;
    private List<Article> articles = new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged(); //Update data
    }

    public void setItemCallBack(ItemCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }

    @NonNull
    @Override
    public SaveNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        return new SaveNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.saved_item_author_content_textView.setText(article.author);
        holder.saved_item_description_content_textView.setText(article.description);
        holder.favorite_icon.setOnClickListener(new View.OnClickListener() { //callback principle
            @Override
            public void onClick(View view) {
                itemCallBack.onRemoveFavorite(article);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCallBack.onOpenDetails(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class SaveNewsViewHolder extends RecyclerView.ViewHolder {
        TextView saved_item_author_content_textView;
        TextView saved_item_description_content_textView;
        ImageView favorite_icon;

        //itemView: Search_news_item
        //If holder does not have, the app will go through inexpensive DFS everytime the user slide --> accumulated cost is large
        //Thus this function is to find all data at once in itemView
        public SaveNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            saved_item_author_content_textView = binding.savedItemAuthorContent;
            saved_item_description_content_textView = binding.savedItemDescriptionContent;
            favorite_icon = binding.savedItemFavoriteImageView;
        }
    }
}
