package edu.bjtu.gymclub.gymclub.Entity.ArticleThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.bjtu.gymclub.gymclub.Entity.ArticleDataBase;
import edu.bjtu.gymclub.gymclub.Entity.ArticleInfo;
import edu.bjtu.gymclub.gymclub.Entity.Trainer;
import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;
import edu.bjtu.gymclub.gymclub.infoFragment;
import edu.bjtu.gymclub.gymclub.runFragment;

public class GetAllThread extends Thread {
    ArticleDataBase articleDataBase;


    public GetAllThread(ArticleDataBase articleDataBase) {
        this.articleDataBase = articleDataBase;

    }

    private infoFragment callback;

    public void setCallback(infoFragment callback) {
        this.callback = callback;
    }

    public void run() {
        ArrayList<ArticleInfo> articles = (ArrayList<ArticleInfo>) articleDataBase.articleIntro_dao().getAll();
        Iterator<ArticleInfo> iterator = articles.iterator();

        infoFragment r = (infoFragment) callback;
        r.setmData(articles);
    }
}
