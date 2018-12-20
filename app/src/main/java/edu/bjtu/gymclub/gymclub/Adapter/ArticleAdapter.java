package edu.bjtu.gymclub.gymclub.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.bjtu.gymclub.gymclub.Entity.ArticleInfo;
import edu.bjtu.gymclub.gymclub.R;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder>{

    private List<ArticleInfo> mData;
    private Context context;
    private LayoutInflater inflater;
    public ArticleAdapter(Context context, List<ArticleInfo> mData){
        this.context=context;
        this.mData=mData;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.article_item,parent,false);
        ArticleAdapter.MyViewHolder mvh=new ArticleAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.MyViewHolder holder, final int position) {
        holder.articleName.setText(mData.get(position).getArticle_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mData.get(position),position+1);
            }
        });
    }
    public interface OnItemClickListener{
        void onItemClick(ArticleInfo articleInfo, int position);
    }
    private ArticleAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ArticleAdapter.OnItemClickListener listener){
        this.listener=listener;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView articleName;


        public MyViewHolder(View itemView) {
            super(itemView);
            articleName=itemView.findViewById(R.id.article_title);
        }
    }

}
