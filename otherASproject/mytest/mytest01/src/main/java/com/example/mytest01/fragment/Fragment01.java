package com.example.mytest01.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mytest01.R;

/**
 * Created by backfire on 2016/11/3.
 */
public class Fragment01 extends  Fragment implements View.OnClickListener{
    private Button btn1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01, container, false);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
