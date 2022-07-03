package com.example.tinynews.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Article implements Serializable { //converting this entity to byte stream to transport between fragments
    public String author;
    public String title;
    public String description;
    public String publishedAt;
    public String content;

    @NonNull
    @PrimaryKey
    public String url;
    public String urlToImage;
    public boolean favorite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;

        return favorite == article.favorite &&
                Objects.equals(author, article.author) &&
                Objects.equals(title, article.title) &&
                Objects.equals(description, article.description) &&
                Objects.equals(publishedAt, article.publishedAt) &&
                Objects.equals(content, article.content) &&
                Objects.equals(url, article.url) &&
                Objects.equals(urlToImage, article.urlToImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, description, publishedAt, content, url, urlToImage, favorite);
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
