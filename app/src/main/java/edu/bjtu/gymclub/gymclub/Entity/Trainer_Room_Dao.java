package edu.bjtu.gymclub.gymclub.Entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface  Trainer_Room_Dao {
    @Query("SELECT * FROM trainer_room")
    List<Trainer_Room> getAll();




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Trainer_Room[] trainers);



    @Delete
    void delete(Trainer_Room[] trainers);
}
