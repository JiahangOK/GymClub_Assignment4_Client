package edu.bjtu.gymclub.gymclub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.bjtu.gymclub.gymclub.Entity.VideoInfo;
import edu.bjtu.gymclub.gymclub.R;


public class VideoInfoAdapter extends RecyclerView.Adapter<VideoInfoAdapter.MyViewHolder> {


    private List<VideoInfo> mData;
    private Context context;
    private LayoutInflater inflater;
    public VideoInfoAdapter(Context context,List<VideoInfo> mData){
        this.context=context;
        this.mData=mData;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public VideoInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_obtain,parent,false);
        MyViewHolder mvh=new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(VideoInfoAdapter.MyViewHolder holder, final int position) {
        holder.tvName.setText(mData.get(position).getVideo_name());
        holder.ivImg.setImageBitmap(mData.get(position).getVideo_img());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mData.get(position),position+1);
            }
        });
    }
    public interface OnItemClickListener{
        void onItemClick(VideoInfo videoInfo,int position);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvName;


        public MyViewHolder(View itemView) {
            super(itemView);
            ivImg=itemView.findViewById(R.id.item_tv_img);
            tvName=itemView.findViewById(R.id.item_tv_name);
        }
    }
}