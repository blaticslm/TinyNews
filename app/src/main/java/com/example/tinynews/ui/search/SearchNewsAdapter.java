package com.example.tinynews.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tinynews.R;
import com.example.tinynews.TinyNewsApplication;
import com.example.tinynews.databinding.SearchNewsItemBinding;
import com.example.tinynews.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//recycler view
public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder>{

    interface ItemCallBack {
        void onOpenDetails(Article article);
    }

    private ItemCallBack itemCallBack;

    //1. Support data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged(); //Update data

    }

    public void setItemCallBack(ItemCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }

    //2. SearchNewsViewHolder
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(itemView);
    }

    //like a buffer
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTextView.setText(article.title);
        //this is the light weight gallery, focusing on picture cache
        try{
            Picasso.get().load(article.urlToImage).resize(200,200).into(holder.itemImageView);
        } catch(Exception e) {
            Picasso.get().load(TinyNewsApplication.UNIVERSAL_URL).resize(200,200).into(holder.itemImageView);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCallBack.onOpenDetails(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size(); //Render number
    }


    //3.Adapter overrides
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemTextView;

        //itemView: Search_news_item
        //If holder does not have, the app will go through inexpensive DFS everytime the user slide --> accumulated cost is large
        //Thus this function is to find all data at once in itemView
        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTextView = binding.searchItemText;
        }
    }

}
