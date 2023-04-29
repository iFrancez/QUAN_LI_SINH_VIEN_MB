package com.example.quan_li_sinh_vien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quan_li_sinh_vien.ActivityMajor;
import com.example.quan_li_sinh_vien.R;
import com.example.quan_li_sinh_vien.model.Majors;

import java.util.ArrayList;

public class adaptermajor extends BaseAdapter {

    private ActivityMajor context;

    private ArrayList<Majors> ArrayListMajor;

    public adaptermajor(ActivityMajor context, ArrayList<Majors> arrayListMajor) {
        this.context = context;
        ArrayListMajor = arrayListMajor;
    }

    @Override
    public int getCount() {
        return ArrayListMajor.size();
    }

    @Override
    public Object getItem(int i) {
        return ArrayListMajor.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listmajor,null);
        TextView TextViewMajorTitle = view.findViewById(R.id.TextViewMajorTitle);
        TextView TextViewMajorCode = view.findViewById(R.id.TextViewMajorCode);
        ImageButton imageDelete = view.findViewById(R.id.majordelete);
        ImageButton imageInformation = view.findViewById(R.id.majorinformation);
        ImageButton imageUpdate = view.findViewById(R.id.majorupdate);

        Majors majors = ArrayListMajor.get(i);

        TextViewMajorCode.setText(majors.getMajors_code());
        TextViewMajorTitle.setText(majors.getMajors_title());

        int id = majors.getId();

        //click delete
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteMajor(id);
            }
        });

        //click update
        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.updateMajor(id);
            }
        });

        //click infor
        imageInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.informationMajor(id);
            }
        });
        return view;

    }

    public void clear() {
        ArrayListMajor.clear();
        notifyDataSetChanged();
    }
}
