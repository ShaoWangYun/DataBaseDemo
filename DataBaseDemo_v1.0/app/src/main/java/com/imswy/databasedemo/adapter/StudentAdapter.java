package com.imswy.databasedemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imswy.databasedemo.R;
import com.imswy.databasedemo.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{

    public List<Student> students = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.textName.setText("姓名："+students.get(position).getName());
        viewHolder.textGender.setText("性别："+students.get(position).getGender());
        viewHolder.textStudentID.setText("学号："+students.get(position).getStudentID());
        viewHolder.textAge.setText(String.valueOf("年龄："+students.get(position).getAge()));

        if(onItemClickListener!=null){
            viewHolder.rlStudentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewHolder.itemView,position);
                }
            });
            viewHolder.rlStudentItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(viewHolder.itemView,position);
                    return false;
                }
            });
        }

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return students.size();
    }

    public Student getItem(int index) {
        return students.get(index);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textName,textStudentID,textGender,textAge;
        private RelativeLayout rlStudentItem;

        public ViewHolder(View view){
            super(view);
            textName = view.findViewById(R.id.text_name);
            textStudentID = view.findViewById(R.id.text_studentID);
            textGender = view.findViewById(R.id.text_gender);
            textAge = view.findViewById(R.id.text_age);
            rlStudentItem = view.findViewById(R.id.rl_studentItem);
        }
    }

}
