package edu.bjtu.gymclub.gymclub.Entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticleIntro_Dao {
    @Query("SELECT * FROM ArticleInfo")
    List<ArticleInfo> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArticleInfo[] articles);



    @Delete
    void delete(ArticleInfo[] articles);
}
