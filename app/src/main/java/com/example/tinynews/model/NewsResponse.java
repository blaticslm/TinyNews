package com.example.tinynews.model;

import java.util.List;
import java.util.Objects;

public class NewsResponse {
    public String status;
    public Integer totalResults;
    public List<Article> articles;
    public String message;
    public String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsResponse that = (NewsResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(totalResults, that.totalResults) &&
                Objects.equals(articles, that.articles) &&
                Objects.equals(message, that.message) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, totalResults, articles, message, code);
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
