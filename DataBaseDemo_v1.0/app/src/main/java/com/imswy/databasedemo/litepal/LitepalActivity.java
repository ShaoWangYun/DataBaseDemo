package com.imswy.databasedemo.litepal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.imswy.databasedemo.R;
import com.imswy.databasedemo.Utils;
import com.imswy.databasedemo.adapter.StudentAdapter;
import com.imswy.databasedemo.bean.Student;

import org.angmarch.views.NiceSpinner;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LitepalActivity extends AppCompatActivity {

    private EditText etName, etStudentID, etAge;
    private NiceSpinner spGender;
    private Button btnSave;
    private RecyclerView recyclerStudentData;
    private String Name, Gender = "男", StudentID;
    private int Age;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Student> list_studentdata = new ArrayList<>();
    private StudentAdapter studentAdapter;
    private Student student_current;
    private LitepalDBOperator litepalDBOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal);

        //控件绑定实例化
        etName = findViewById(R.id.et_name);
        etStudentID = findViewById(R.id.et_studentID);
        etAge = findViewById(R.id.et_age);
        spGender = findViewById(R.id.sp_gender);
        btnSave = findViewById(R.id.btn_save);
        recyclerStudentData = findViewById(R.id.recycler_studentData);
        studentAdapter = new StudentAdapter(list_studentdata);

        //初始化litepal数据库操作类
        litepalDBOperator = new LitepalDBOperator();

        //为按钮绑定点击事件
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnSave的点击事件
                Name = etName.getText().toString().trim();
                StudentID = etStudentID.getText().toString().trim();
                Age = Integer.valueOf(etAge.getText().toString().trim());
                if (!Name.isEmpty() && !StudentID.isEmpty() && !StudentID.isEmpty() && !etAge.getText().toString().isEmpty()) {
                    //如果当前的学号已经有相关的信息了，那么就更新数据
                    student_current = litepalDBOperator.isExit(StudentID);
                    if (student_current != null) {
                        if (student_current.getName().equals(Name) && student_current.getGender().equals(Gender) && student_current.getAge() == Age) {
                            Utils.showToast(LitepalActivity.this, "保存失败：学生信息重复", 1);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LitepalActivity.this);
                            builder.setTitle("警告");
                            builder.setCancelable(false);
                            builder.setMessage("你想要更新已有学生信息么？");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        //获取操作的position（便于局部刷新recyclerview）
                                        int updatePosition = getPositionFromList(student_current.getStudentID());
                                        //数据库操作更新学生表
                                        litepalDBOperator.updateStudent(new Student(Name,Gender,student_current.getStudentID(),Age));
                                        //更新显示,更新特定的item（局部刷新）
                                        Log.i("Litepal","updatePosition is : "+updatePosition);
                                        studentAdapter.notifyItemChanged(updatePosition);
                                        refreshRecyclerView();
                                        //提示用户更新成功
                                        Utils.showToast(LitepalActivity.this, "更新成功", 1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Utils.showToast(LitepalActivity.this, "更新失败", 1);
                                    }
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    } else {
                        try {
                            //数据库操作，为学生表添加一条数据
                            litepalDBOperator.addStudent(new Student(Name,Gender,StudentID,Age));
                            //更新UI显示
                            int addPosition = litepalDBOperator.findAllStudentData().size()-1;
                            studentAdapter.notifyItemInserted(addPosition);
                            refreshRecyclerView();
                            //提示用户保存成功
                            Utils.showToast(LitepalActivity.this, "保存成功", 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Utils.showToast(LitepalActivity.this, "保存失败", 1);
                        }
                    }
                } else {
                    Utils.showToast(LitepalActivity.this, "保存失败：请输入完整的信息", 1);
                }
            }
        });
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerStudentData.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerStudentData.setHasFixedSize(true);
        //为spinner设置数据以及相关选中事件
        LinkedList<String> spinnerGender = new LinkedList<>(Arrays.asList("男", "女"));
        spGender.attachDataSource(spinnerGender);
        spGender.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Gender = "男";
                        break;
                    case 1:
                        Gender = "女";
                        break;
                }
            }
        });
        refreshRecyclerView();
    }

    //刷新recyclerview
    private void refreshRecyclerView() {
        list_studentdata = litepalDBOperator.findAllStudentData();
        studentAdapter = new StudentAdapter(list_studentdata);
        recyclerStudentData.setAdapter(studentAdapter);

        //定义recyclerview的子项点击事件
        studentAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("Litepal","单点击事件");
                Student student = studentAdapter.getItem(position);
                etName.setText(student.getName());
                etStudentID.setText(student.getStudentID());
                etAge.setText(String.valueOf(student.getAge()));
                if (student.getGender().equals("男")) {
                    spGender.setSelectedIndex(0);
                } else {
                    spGender.setSelectedIndex(1);
                }
            }
        });
        //定义recyclerview的子项长按时间
        studentAdapter.setOnItemLongClickListener(new StudentAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                Log.i("Litepal","长按事件");
                AlertDialog.Builder builder = new AlertDialog.Builder(LitepalActivity.this);
                builder.setTitle("警告");
                builder.setCancelable(false);
                builder.setMessage("你想要删除学号为 " + studentAdapter.getItem(position).getStudentID() + " 的学生信息么？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            //获取position（便于局部刷新recyclerview）
                            int deleteposition = getPositionFromList(studentAdapter.getItem(position).getStudentID());
                            //删除学生操作
                            litepalDBOperator.deleteStudent(studentAdapter.getItem(position).getStudentID());
                            //刷新UI
                            studentAdapter.notifyItemRemoved(deleteposition);
                            refreshRecyclerView();
                            //提示用户
                            Utils.showToast(LitepalActivity.this, "删除成功", 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Utils.showToast(LitepalActivity.this, "删除失败", 1);
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    //根据studentID获取该条数据在list中的下标，便于对recyclerview进行局部刷新
    private int getPositionFromList(String studentID){
        List<Student> list = LitePal.findAll(Student.class);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getStudentID().equals(studentID)){
                return i;
            }
        }
        return -1;
    }
}
