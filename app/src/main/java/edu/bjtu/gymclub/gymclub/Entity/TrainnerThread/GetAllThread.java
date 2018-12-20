package edu.bjtu.gymclub.gymclub.Entity.TrainnerThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.bjtu.gymclub.gymclub.Entity.Trainer;
import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;
import edu.bjtu.gymclub.gymclub.runFragment;

public class GetAllThread extends Thread {
    TrainerDataBase trainerDataBase;


    public GetAllThread(TrainerDataBase trainerDataBase) {
        this.trainerDataBase = trainerDataBase;

    }

    private runFragment callback;

    public void setCallback(runFragment callback) {
        this.callback = callback;
    }

    public void run() {
        ArrayList<Trainer_Room> trainer_rooms = (ArrayList<Trainer_Room>) trainerDataBase.trainer_room_dao().getAll();
        List<Trainer> trainers = new ArrayList<Trainer>();
        Iterator<Trainer_Room> iterator = trainer_rooms.iterator();
        while (iterator.hasNext()) {
            Trainer_Room trainer_room = iterator.next();
            trainers.add(new Trainer(trainer_room.getTrainer_name(), trainer_room.getTrainer_image(),
                    trainer_room.getTrainer_intro(), trainer_room.getTrainer_tel(), trainer_room.getTrainer_email()));
        }
        runFragment r = (runFragment) callback;
        r.setTrainers(trainers);
    }
}
