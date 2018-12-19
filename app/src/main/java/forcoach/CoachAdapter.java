package forcoach;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.bjtu.gymclub.gymclub.R;

public class CoachAdapter extends ArrayAdapter<Coach> {

    private int resourceId;
    public CoachAdapter(Context context1, int textViewResourceId, List<Coach> objects){
        super(context1,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Coach coach=getItem(position);           //获取当前项的实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView coachImage=(ImageView)view.findViewById(R.id.coach_image);
        TextView coachName=(TextView) view.findViewById(R.id.coach_name);
        TextView coachIntro=(TextView) view.findViewById(R.id.coach_intro);
        coachImage.setImageResource(coach.getImageId());
        coachName.setText(coach.getName());
        coachIntro.setText(coach.getIntro());
        return view;
    }
}
