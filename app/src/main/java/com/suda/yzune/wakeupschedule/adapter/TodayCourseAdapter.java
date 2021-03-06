package com.suda.yzune.wakeupschedule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.suda.yzune.wakeupschedule.R;
import com.suda.yzune.wakeupschedule.model.bean.Course;
import com.suda.yzune.wakeupschedule.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by YZune on 2017/9/9.
 */

public class TodayCourseAdapter extends RecyclerView.Adapter<TodayCourseAdapter.ViewHolder> {

    private List<Course> todayCourseList;

    static String str_time;
    static int chooseSchool;
    static List<String> timeList, end_timeList;
    private String[] startList = {"8:00", "9:00", "10:10", "11:10", "13:30", "14:30", "15:40", "16:40", "18:30", "19:30", "20:30"};
    private String[] endList = {"8:50", "9:50", "11:00", "12:00", "14:20", "15:20", "16:30", "17:30", "19:20", "20:20", "21:20"};

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseName, teacherName, room, classTime, odd, classWeek, timeDetail;

        public ViewHolder(View view) {
            super(view);
            Gson gson = new Gson();
            courseName = view.findViewById(R.id.cv_courseName);
            teacherName = view.findViewById(R.id.cv_teacher);
            room = view.findViewById(R.id.cv_room);
            classTime = view.findViewById(R.id.cv_time);
            odd = view.findViewById(R.id.cv_odd);
            classWeek = view.findViewById(R.id.cv_week);
            timeDetail = view.findViewById(R.id.cv_timeDetail);

            odd.setVisibility(View.GONE);
            classWeek.setVisibility(View.GONE);
            str_time = SharedPreferencesUtils.getStringFromSP(view.getContext(), "timeList", "");
            timeList = gson.fromJson(str_time, new TypeToken<List<String>>(){}.getType());
            end_timeList = gson.fromJson(SharedPreferencesUtils.getStringFromSP(view.getContext(),"endTimeList",""), new TypeToken<List<String>>(){}.getType());
            chooseSchool = SharedPreferencesUtils.getIntFromSP(view.getContext(), "chooseSchool");
        }
    }


    public TodayCourseAdapter(List<Course> coursesList) {
        todayCourseList = coursesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = todayCourseList.get(position);
        holder.courseName.setText(course.getName());
        holder.teacherName.setText("老师：" + course.getTeach());
        holder.room.setText("地点：" + course.getRoom());
        holder.classTime.setText("时间：第" + course.getStart() + " - " + (course.getStep() + course.getStart() - 1) + "节");
        //holder.timeDetail.setText(startList[course.getStart() - 1] + " - " + endList[course.getStart() + course.getStep() - 2]);
        //holder.classWeek.setText("第" + course.getStartWeek() + " - " + course.getEndWeek() + "周");
        if (chooseSchool == 1){
            holder.timeDetail.setText(startList[course.getStart() - 1] + " - " + endList[course.getStart() + course.getStep() - 2]);
        }
        else {
            if (str_time.equals("")){
                holder.timeDetail.setText("还未设置课程时间");
            }else {
                holder.timeDetail.setText(timeList.get(course.getStart() - 1) + " - " + end_timeList.get(course.getStart() + course.getStep() - 2));
            }
        }
    }

    @Override
    public int getItemCount() {
        return todayCourseList.size();
    }
}
