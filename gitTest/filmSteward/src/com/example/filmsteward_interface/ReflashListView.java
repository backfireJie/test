package com.example.filmsteward_interface;

import com.example.filmsteward.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class ReflashListView extends ListView {
	View header;

	public ReflashListView(Context context) {
		super(context);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public ReflashListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public ReflashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public void initView(Context context) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		layoutInflater.inflate(R.layout.header_layout, null);
		this.addHeaderView(header);
	}

}
