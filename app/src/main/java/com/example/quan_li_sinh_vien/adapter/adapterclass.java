package com.example.quan_li_sinh_vien.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quan_li_sinh_vien.ActivityClass;
import com.example.quan_li_sinh_vien.R;
import com.example.quan_li_sinh_vien.model.ClassSub;

import java.util.ArrayList;

public class adapterclass extends BaseAdapter {
    private ActivityClass context;
    private ArrayList<ClassSub> ArrayListClass;

    public adapterclass(ActivityClass context, ArrayList<ClassSub> arrayListClass) {
        this.context = context;
        ArrayListClass = arrayListClass;
    }

    @Override
    public int getCount() {
        return ArrayListClass.size();
    }

    @Override
    public Object getItem(int i) {
        return ArrayListClass.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listclass,null);

        TextView txtName = view.findViewById(R.id.TextViewClassTitle);
        TextView txtCode = view.findViewById(R.id.TextViewCodeClass);

        ImageButton imgbtnDeleteClass = view.findViewById(R.id.classdelete);
        ImageButton imgbtnUpdateClass = view.findViewById(R.id.classupdate);
        ImageButton imgbtnInformation = view.findViewById(R.id.classinformation);

        ClassSub classSub = ArrayListClass.get(i);

        txtName.setText(classSub.getClass_name());
        txtCode.setText(classSub.getClass_code());

        int id = classSub.getId_class();

        //xoá lớp học
        imgbtnDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteClass(id);
            }
        });

        //xem thông tin lớp học
        imgbtnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.informationClass(id);
            }
        });

        //cập nhật thông tin lớp học
        imgbtnUpdateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.updateClass(id);
            }
        });
        return view;
    }

    public void clear() {
        ArrayListClass.clear();
        notifyDataSetChanged();
    }
}
