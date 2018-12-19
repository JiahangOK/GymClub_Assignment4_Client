package edu.bjtu.gymclub.gymclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.bjtu.gymclub.gymclub.Adapter.VideoInfoAdapter;
import edu.bjtu.gymclub.gymclub.Entity.Trainer;
import edu.bjtu.gymclub.gymclub.Entity.VideoInfo;

@SuppressLint("ValidFragment")
public class walkFragment extends Fragment {
    private String jsoninfo;
    private List<VideoInfo> mData;
    private VideoInfoAdapter adapter;
    private RecyclerView recyclerView;


    @SuppressLint("ValidFragment")
    public walkFragment(String jsoninfo) {
        this.jsoninfo = jsoninfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_walk,null);
        recyclerView= view.findViewById(R.id.fm_net_recycler);
        mData=new ArrayList<>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsoninfo);
            JSONArray data = jsonObject.getJSONArray("video_info");
            String video_name=null;
            String video_url=null;

            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject1 = data.getJSONObject(i);
                video_name = jsonObject1.getString("video_name");
                video_url = jsonObject1.getString("video_url");


                mData.add(new VideoInfo(video_name, video_url));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        adapter=new VideoInfoAdapter(getActivity(),mData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(new VideoInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoInfo videoInfo,int position) {
                Intent intent = new Intent(getActivity(), VideoViewActivity.class);
                intent.putExtra("VIDEO_INFO", new Gson().toJson(videoInfo));
                intent.putExtra("VIDEO_SORT",position+"/"+mData.size());
                intent.putExtra("VIDEO_TYPE",0);

                startActivity(intent);

            }
        });
        return view;
    }
}
