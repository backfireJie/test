package com.example.filmsteward;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AddFilmsActivity extends Activity {
	private Button btn_addFilms;
	private ListView lv_allFilms;
	MyDatabaseHelper dbHelper;
	ArrayList<Map<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();

		list = getAllFilms();
		showAllFilms(list);
		setListener();
	}

	private void findView() {
		btn_addFilms = (Button) findViewById(R.id.btn_addFilms);

		lv_allFilms = (ListView) findViewById(R.id.lv_allFilms);
		dbHelper = new MyDatabaseHelper(AddFilmsActivity.this, "myFilms.db",
				null, 1);
	}

	private void setListener() {
		btn_addFilms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent.setClass(AddFilmsActivity.this, FunctionActivity.class);
				startActivity(intent);
			}
		});
		lv_allFilms.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String idString = String.valueOf(id);
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("filmIdBySingle", idString);
				// TODO Auto-generated);
				intent.setClass(AddFilmsActivity.this, LookTheFilm.class);
				startActivity(intent);

			}
		});
		lv_allFilms.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final long theId = id;

				AlertDialog.Builder adBuilder = new Builder(
						AddFilmsActivity.this);
				adBuilder.setTitle("电影名");
				adBuilder.setItems(new String[] { "修改", "删除" },
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								switch (which) {
								case 0:
									String theIdToString = theId + "";
									Intent intent = new Intent();
									intent.putExtra("filmId", theIdToString);
									intent.setClass(AddFilmsActivity.this,
											FunctionActivity.class);
									startActivity(intent);

									break;
								case 1:
									String theIdString = String.valueOf(theId);
									int result = dbHelper
											.getReadableDatabase()
											.delete("filmPoster",
													"_id =?",
													new String[] { theIdString });
									if (result > 0)
										Toast.makeText(AddFilmsActivity.this,
												"删除成功", 5000).show();

									// deleteList(theId);
									break;

								}
							}

						});
				adBuilder.create().show();
				return true;
			}
		});

		/*
		 * lv_allFilms.setOnItemLongClickListener(new OnItemLongClickListener()
		 * {
		 * 
		 * @Override public boolean onItemLongClick(AdapterView<?> parent, View
		 * view, int position, long id) { // TODO Auto-generated method stub
		 * 
		 * deleteList(id); return true; }
		 * 
		 * });
		 */

	}

	public ArrayList<Map<String, String>> getAllFilms() {
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
				"select * from filmPoster", null);

		ArrayList<Map<String, String>> resultArrayList = new ArrayList<Map<String, String>>();

		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			String filmTextString = cursor.getString(3);
			map.put("filmName", cursor.getString(2));
			map.put("filmText", filmTextString);

			resultArrayList.add(map);
		}
		return resultArrayList;
	}

	public void showAllFilms(ArrayList<Map<String, String>> list) {
		SimpleAdapter adapter = new SimpleAdapter(AddFilmsActivity.this, list,
				R.layout.show_films_list, new String[] { "filmName",
						"filmText", }, new int[] { R.id.tv_filmName,
						R.id.tv_filmTextFragment });
		lv_allFilms.setAdapter(adapter);
		// lv_allFilms.notify();
	}

	public void deleteList(long id) {
		String theId = String.valueOf(id);
		int result = dbHelper.getReadableDatabase().delete("filmPoster",
				"_id =?", new String[] { theId });
		if (result > 0)
			Toast.makeText(AddFilmsActivity.this, "删除成功", 8000).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "关于");
		menu.add(0, 2, 2, "分类显示");
		menu.add(0, 3, 3, "返回");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			AlertDialog.Builder adb = new Builder(AddFilmsActivity.this);
			adb.setTitle("关于");
			adb.setMessage("手机电影管家HM_0.10");
			adb.setPositiveButton("确定", null);
			adb.show();
			break;
		case 2:
			break;
		case 3:
			AlertDialog.Builder adb2 = new Builder(AddFilmsActivity.this);
			adb2.setTitle("消息");
			adb2.setMessage("登陆界面？");
			adb2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 关闭列表中的所有Activity
					AddFilmsActivity.this.finish();

				}
			});
			adb2.setNegativeButton("取消", null);
			// 显示对话框
			adb2.show();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}