package edu.bjtu.gymclub.gymclub;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.bjtu.gymclub.gymclub.Adapter.RecyclerViewAdapter;
import edu.bjtu.gymclub.gymclub.Entity.Config;
import edu.bjtu.gymclub.gymclub.Entity.TrainnerThread.DeleteThread;
import edu.bjtu.gymclub.gymclub.Entity.TrainnerThread.GetAllThread;
import edu.bjtu.gymclub.gymclub.Entity.TrainnerThread.InsertThread;
import edu.bjtu.gymclub.gymclub.Entity.Trainer;
import edu.bjtu.gymclub.gymclub.Entity.TrainerDataBase;
import edu.bjtu.gymclub.gymclub.Entity.Trainer_Room;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class runFragment extends Fragment {
    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    private List<Trainer> trainers;


    //记录上一次点的位置
    private int oldPosition = 0;

    // 存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.rotate1,
            R.drawable.rotate2,
            R.drawable.rotate3,
            R.drawable.rotate4,
            R.drawable.rotate5
    };

    // 存放图片的标题
    private String[] titles = new String[]{
            "Image 1",
            "Image 2",
            "Image 3",
            "Image 4",
            "Image 5"};

    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;


    private RecyclerView recyclerView;
    private List<Trainer> trainerList;
    private RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint("ValidFragment")
    public runFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_run, null);
        setView();

        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        //数据存储API(SharedPreferences)
        SharedPreferences sp = null;
        sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);//sp.getString()第二个参数是缺省值，如果SharedPreferences中不存在值就返回缺省值
        String username = sp.getString("USERNAME", ""); //获取sp里面存储的数据
        if (isNetworkAvalible(getContext())) {
            String url_test = "http://" + Config.HOST + ":8080";
            String url = "http://" + Config.HOST + ":8080/userTrainer";
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
                TrainerDataBase trainerDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                        TrainerDataBase.class, "trainer.db").build();

                GetAllThread getAllThread = new GetAllThread(trainerDataBase);
                getAllThread.setCallback(runFragment.this);
                getAllThread.start();
                try {
                    getAllThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recyclerViewAdapter = new RecyclerViewAdapter(trainers, getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerViewAdapter);
            } else {
                System.out.println("连接上了");
                //有网
                initTrainerData(url, username);
            }


        } else {
            //没网，读缓存
            TrainerDataBase trainerDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                    TrainerDataBase.class, "trainer.db").build();
            GetAllThread getAllThread = new GetAllThread(trainerDataBase);
            getAllThread.setCallback(runFragment.this);
            getAllThread.start();
            try {
                getAllThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recyclerViewAdapter = new RecyclerViewAdapter(trainers, getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewAdapter);
        }


        return mView;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
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

    //加载教练信息
    private void initTrainerData(String url, String username) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        System.out.println("username:" + username);
        formBuilder.add("username", username);
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

                        System.out.println("获取到res：" + res);
                        //获取当前fragment
//                        FragmentManager fragmentManager = getFragmentManager();
//                        runFragment runfragment = null;
//
//                        runfragment = (runFragment) fragmentManager.getFragments().get(0);
//                        runfragment.setJsoninfo(res);
                        jsoninfo = res;


                        trainerList = new ArrayList<>();

                        JSONObject jsonObject = null;
                        String trainer_image = null;
                        String trainer_name = null;
                        String trainer_intro = null;
                        String trainer_tel = null;
                        String trainer_email = null;

                        try {
                            jsonObject = new JSONObject(jsoninfo);
                            JSONArray data = jsonObject.getJSONArray("trainer_info");

                            List<Trainer_Room> trainers = new ArrayList<Trainer_Room>();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                trainer_image = jsonObject1.getString("trainer_image_url");
                                trainer_name = jsonObject1.getString("trainer_name");
                                trainer_intro = jsonObject1.getString("trainer_intro");
                                trainer_tel = jsonObject1.getString("trainer_tel");
                                trainer_email = jsonObject1.getString("trainer_email");
                                //添加教练
                                trainerList.add(new Trainer(trainer_name, trainer_image, trainer_intro, trainer_tel, trainer_email));
                                trainers.add(new Trainer_Room(trainer_name, trainer_image, trainer_intro, trainer_tel, trainer_email));
                            }

                            //存入数据库
                            TrainerDataBase trainerDataBase = Room.databaseBuilder(getActivity().getApplicationContext(),
                                    TrainerDataBase.class, "trainer.db").build();
                            DeleteThread deleteThread = new DeleteThread(trainerDataBase);
                            deleteThread.start();//清空表
                            deleteThread.join();
                            Trainer_Room[] trainer_rooms_array = new Trainer_Room[trainers.size()];
                            trainers.toArray(trainer_rooms_array);
                            InsertThread insertThread = new InsertThread(trainerDataBase, trainer_rooms_array);
                            insertThread.start();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        recyclerViewAdapter = new RecyclerViewAdapter(trainerList, getActivity());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(recyclerViewAdapter);


                    }
                });


            }
        });
    }


    private void setView() {
        mViewPaper = (ViewPager) mView.findViewById(R.id.vp);


        //显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }

        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));
        dots.add(mView.findViewById(R.id.dot_3));
        dots.add(mView.findViewById(R.id.dot_4));

        title = (TextView) mView.findViewById(R.id.title);
        title.setText(titles[0]);
        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_yes);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_no);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }


    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;

        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(images.get(position));
            return images.get(position);
        }
    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 2, 2, TimeUnit.SECONDS);
    }

    /**
     * 图片轮播任务     * @author liuyazhuang     *
     */
    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

}




