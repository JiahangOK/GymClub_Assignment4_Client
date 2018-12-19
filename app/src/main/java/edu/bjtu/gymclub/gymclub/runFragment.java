package edu.bjtu.gymclub.gymclub;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.bjtu.gymclub.gymclub.Adapter.RecyclerViewAdapter;
import edu.bjtu.gymclub.gymclub.Entity.Trainer;

@SuppressLint("ValidFragment")
public class runFragment extends Fragment {
    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;

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
    private String jsoninfo;

    private RecyclerView recyclerView;
    private List<Trainer> trainerList;
    private RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint("ValidFragment")
    public runFragment(String jsoninfo) {
        this.jsoninfo = jsoninfo;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_run, null);
        setView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());


        recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);

        initTrainerData();
        recyclerViewAdapter = new RecyclerViewAdapter(trainerList, getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        return mView;
    }

    //加载教练信息
    private void initTrainerData() {
        trainerList = new ArrayList<>();
//        ImageView iv_img = (ImageView)mView.findViewById(R.id.thumbnail_image_1);
//
//        //字符串转化为图片
//        Bitmap bitmap=null;
//        try {
//            byte[]bitmapArray;
//            bitmapArray= Base64.decode(imageStr, Base64.DEFAULT);
//            bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        MainInterfaceActivity mainInterfaceActivity = (MainInterfaceActivity)getActivity();
//        iv_img.setImageBitmap(bitmap);
        JSONObject jsonObject = null;
        String trainer_image = null;
        String trainer_name = null;
        String trainer_intro = null;
        String trainer_tel = null;
        String trainer_email = null;

        try {
            jsonObject = new JSONObject(jsoninfo);
            JSONArray data = jsonObject.getJSONArray("trainer_info");
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject1 = data.getJSONObject(i);
                trainer_image = jsonObject1.getString("trainer_image_url");
                trainer_name = jsonObject1.getString("trainer_name");
                trainer_intro = jsonObject1.getString("trainer_intro");
                trainer_tel = jsonObject1.getString("trainer_tel");
                trainer_email = jsonObject1.getString("trainer_email");
                //添加教练
                trainerList.add(new Trainer(trainer_name, trainer_image,trainer_intro,trainer_tel,trainer_email));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


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
