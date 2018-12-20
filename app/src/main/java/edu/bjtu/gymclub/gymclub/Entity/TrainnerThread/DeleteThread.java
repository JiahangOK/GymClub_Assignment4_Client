package edu.bjtu.gymclub.gymclub.Entity.TrainnerThread;

import java.util.ArrayList;

import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;

public class DeleteThread extends Thread{
    TrainerDataBase trainerDataBase;



    public DeleteThread(TrainerDataBase trainerDataBase) {
        this.trainerDataBase = trainerDataBase;

    }

    @Override
    public void run() {
        ArrayList<Trainer_Room> trainer_rooms = (ArrayList<Trainer_Room>) trainerDataBase.trainer_room_dao().getAll();
        Trainer_Room[] traniners= new Trainer_Room[trainer_rooms.size()];
        trainer_rooms.toArray(traniners);
        trainerDataBase.trainer_room_dao().delete(traniners);

    }
}
