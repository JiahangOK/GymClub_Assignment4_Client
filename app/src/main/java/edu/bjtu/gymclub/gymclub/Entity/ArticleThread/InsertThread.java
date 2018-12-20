package edu.bjtu.gymclub.gymclub.Entity.ArticleThread;

import edu.bjtu.gymclub.gymclub.Entity.ArticleDataBase;
import edu.bjtu.gymclub.gymclub.Entity.ArticleInfo;
import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;

public class InsertThread extends Thread {
    ArticleDataBase articleDataBase;
    ArticleInfo[] articleInfos;

    public InsertThread(ArticleDataBase articleDataBase, ArticleInfo[] articleInfos) {
        this.articleDataBase = articleDataBase;
        this.articleInfos = articleInfos;
    }

    public void run() {
        articleDataBase.articleIntro_dao().insertAll(articleInfos);
    }
}