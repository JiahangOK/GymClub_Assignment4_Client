package edu.bjtu.gymclub.gymclub.Entity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;

@SuppressLint("ParcelCreator")
public class VideoInfo {
    private String video_name;
    private String video_url;
    private Bitmap video_img;

    public VideoInfo(String video_name, String video_url) {
        this.video_name = video_name;
        this.video_url = video_url;
        takePicture(200000);
    }





    public void takePicture(long time) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        // 设置数据源，有多种重载，这里用本地文件的绝对路径
        try {
            //根据url获取缩略图
            mmr.setDataSource(video_url, new HashMap());
            //获得第一帧图片
            setVideo_img(mmr.getFrameAtTime(time,MediaMetadataRetriever.OPTION_CLOSEST));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            mmr.release();
        }
    }

    public Bitmap getVideo_img() {
        return video_img;
    }

    public void setVideo_img(Bitmap video_img) {
        this.video_img = video_img;
    }

    public String getVideo_name() {
        return video_name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }



}
