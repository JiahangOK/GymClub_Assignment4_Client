package edu.bjtu.gymclub.gymclub.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class ArticleInfo {
    @NonNull
    @PrimaryKey
    private String article_name;

    @ColumnInfo(name = "actical_content")
    private String acticle_content;

    public ArticleInfo(String article_name, String acticle_content) {
        this.article_name = article_name;
        this.acticle_content = acticle_content;
    }

    @NonNull
    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(@NonNull String article_name) {
        this.article_name = article_name;
    }

    public String getActicle_content() {
        return acticle_content;
    }

    public void setActicle_content(String acticle_content) {
        this.acticle_content = acticle_content;
    }
}
