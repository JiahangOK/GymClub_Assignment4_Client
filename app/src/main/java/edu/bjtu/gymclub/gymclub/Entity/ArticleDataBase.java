package edu.bjtu.gymclub.gymclub.Entity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;



@Database(entities = {ArticleInfo.class}, version = 1,exportSchema = false)
public abstract class ArticleDataBase extends RoomDatabase {
    public abstract ArticleIntro_Dao articleIntro_dao();
}
