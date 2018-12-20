package edu.bjtu.gymclub.gymclub.Entity.TrainnerThread;

import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;

public class InsertThread extends Thread {
    TrainerDataBase trainerDataBase;
    Trainer_Room[] trainer_rooms_array;

    public InsertThread(TrainerDataBase trainerDataBase, Trainer_Room[] trainer_rooms_array) {
        this.trainerDataBase = trainerDataBase;
        this.trainer_rooms_array = trainer_rooms_array;
    }

    public void run() {
        trainerDataBase.trainer_room_dao().insertAll(trainer_rooms_array);
    }
}