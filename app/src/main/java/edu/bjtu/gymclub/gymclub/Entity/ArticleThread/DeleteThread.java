package edu.bjtu.gymclub.gymclub.Entity.ArticleThread;

import java.util.ArrayList;

import edu.bjtu.gymclub.gymclub.Entity.ArticleDataBase;
import edu.bjtu.gymclub.gymclub.Entity.ArticleInfo;
import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;

public class DeleteThread extends Thread{
    ArticleDataBase articleDataBase;



    public DeleteThread(ArticleDataBase articleDataBase) {
        this.articleDataBase = articleDataBase;

    }

    @Override
    public void run() {
        ArrayList<ArticleInfo> articles = (ArrayList<ArticleInfo>) articleDataBase.articleIntro_dao().getAll();
        ArticleInfo[] a= new ArticleInfo[articles.size()];
        articles.toArray(a);
        articleDataBase.articleIntro_dao().delete(a);

    }
}
