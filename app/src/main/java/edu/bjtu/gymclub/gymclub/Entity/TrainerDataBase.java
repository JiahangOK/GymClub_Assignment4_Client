package edu.bjtu.gymclub.gymclub.Entity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Trainer_Room.class}, version = 1,exportSchema = false)
public abstract class TrainerDataBase extends RoomDatabase {
    public abstract Trainer_Room_Dao trainer_room_dao();
}
