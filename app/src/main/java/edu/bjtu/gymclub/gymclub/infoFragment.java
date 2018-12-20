package edu.bjtu.gymclub.gymclub;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.bjtu.gymclub.gymclub.Adapter.ArticleAdapter;
import edu.bjtu.gymclub.gymclub.Entity.ArticleDataBase;
import edu.bjtu.gymclub.gymclub.Entity.ArticleInfo;
import edu.bjtu.gymclub.gymclub.Entity.ArticleThread.DeleteThread;
import edu.bjtu.gymclub.gymclub.Entity.ArticleThread.GetAllThread;
import edu.bjtu.gymclub.gymclub.Entity.ArticleThread.InsertThread;
import edu.bjtu.gymclub.gymclub.Entity.Config;

import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class infoFragment extends Fragment {


    private List<ArticleInfo> mData;
    private ArticleAdapter adapter;
    private RecyclerView recyclerView;


    @SuppressLint("ValidFragment")
    public infoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, null);
        recyclerView = view.findViewById(R.id.article_recycler);
        mData = new ArrayList<>();
        if (isNetworkAvalible(getContext())) {
            String url_test = "http://" + Config.HOST + ":8080";
            String url = "http://" + Config.HOST + ":8080/articles";
            HttpURLConnection urlConn = null;
            boolean isConn = false;
            try {
                urlConn = (HttpURLConnection) new URL(url_test).openConnection();
                urlConn.setConnectTimeout(1000);
                if (urlConn.getResponseCode() == 200) {
                    isConn = true;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConn.disconnect();
            }
            if (isConn == false) {
                System.out.println("没连接上");
                //读缓存
                ArticleDataBase articleDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                        ArticleDataBase.class, "article.db").build();

                GetAllThread getAllThread = new GetAllThread(articleDataBase);
                getAllThread.setCallback(infoFragment.this);
                getAllThread.start();
                try {
                    getAllThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter = new ArticleAdapter(getActivity(), mData);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ArticleInfo articleInfo, int position) {
                        //打开文章详细内容
                        Intent intent = new Intent(getActivity(), ArticleActivity.class);
                        intent.putExtra("article_name", articleInfo.getArticle_name());
                        intent.putExtra("article_content", articleInfo.getActicle_content());
                        startActivity(intent);

                    }
                });
            } else {
                System.out.println("连接上了");
                //有网
                initArticals(url);
            }
        } else {
            //没网，读缓存
            ArticleDataBase articleDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                    ArticleDataBase.class, "article.db").build();
            GetAllThread getAllThread = new GetAllThread(articleDataBase);
            getAllThread.setCallback(infoFragment.this);
            getAllThread.start();
            try {
                getAllThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            adapter = new ArticleAdapter(getActivity(), mData);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ArticleInfo articleInfo, int position) {
                    //打开文章详细内容
                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                    intent.putExtra("article_name", articleInfo.getArticle_name());
                    intent.putExtra("article_content", articleInfo.getActicle_content());
                    startActivity(intent);

                }
            });
        }


        return view;
    }

    public void setmData(List<ArticleInfo> mData) {
        this.mData = mData;
    }

    public static boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private void initArticals(String url) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println("连接失败！");

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String res = null;
                        try {
                            res = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String jsoninfo = null;

                        System.out.println("article获取到的res：" + res);

                        jsoninfo = res;


                        JSONObject jsonObject;
                        String artical_name = null;
                        String artical_content = null;
                        try {
                            jsonObject = new JSONObject(jsoninfo);
                            JSONArray data = jsonObject.getJSONArray("article_info");


                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                artical_name = jsonObject1.getString("article_name");
                                artical_content = jsonObject1.getString("article_content");
                                mData.add(new ArticleInfo(artical_name, artical_content));
                            }
                            //存入数据库
                            ArticleDataBase articleDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                                    ArticleDataBase.class, "article.db").build();
                            DeleteThread deleteThread = new DeleteThread(articleDataBase);
                            deleteThread.start();//清空表
                            deleteThread.join();
                            ArticleInfo[] articleInfos = new ArticleInfo[mData.size()];
                            mData.toArray(articleInfos);
                            InsertThread insertThread = new InsertThread(articleDataBase, articleInfos);
                            insertThread.start();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        adapter = new ArticleAdapter(getActivity(), mData);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        adapter.setOnItemClickListener(new ArticleAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(ArticleInfo articleInfo, int position) {
                                //打开文章详细内容
                                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                                intent.putExtra("article_name", articleInfo.getArticle_name());
                                intent.putExtra("article_content", articleInfo.getActicle_content());
                                startActivity(intent);

                            }
                        });


                    }
                });


            }
        });

    }


}
