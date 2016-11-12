package com.example.filmsteward;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText et_rNameEditText, et_rPwdEditText, et_rPwdEditText2;
	private Button btn_okButton;
	MyDatabaseHelper dbHelper;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		dbHelper = new MyDatabaseHelper(RegisterActivity.this, "myFilms.db",
				null, 1);

		et_rNameEditText = (EditText) findViewById(R.id.et_rName);
		et_rPwdEditText = (EditText) findViewById(R.id.et_rPwd);
		et_rPwdEditText2 = (EditText) findViewById(R.id.et_rPwd2);
		btn_okButton = (Button) findViewById(R.id.btn_ok);
		btn_okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nameString = et_rNameEditText.getText().toString();
				String pwdString = et_rPwdEditText.getText().toString();
				if (!(nameString.equals(" ") && pwdString.equals(" "))) {

					if (addUser(dbHelper.getReadableDatabase(), nameString,
							pwdString)) {
						DialogInterface.OnClickListener myDialog = new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this,
										LandingActivity.class);
								startActivity(intent);

								RegisterActivity.this.onDestroy();
							}

						};
						/*
						 * new AlertDialog.Builder(RegisterActivity.this)
						 * .setTitle("注册成功").setMessage("注册成功")
						 * .setPositiveButton("确定", myDialog).show();
						 */

					} else {
						new AlertDialog.Builder(RegisterActivity.this)
								.setTitle("错误提示").setMessage("注册失败")
								.setPositiveButton("确定", null).show();
					}
				} else {
					new AlertDialog.Builder(RegisterActivity.this)
							.setTitle("错误提示").setMessage("账户或密码不能为空")
							.setPositiveButton("确定", null).show();
				}
			}
		});

	}

	public boolean addUser(SQLiteDatabase db, String name, String pwd) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("pwd", pwd);
		long id = db.insert("userMessage", null, values);
		if (id <= 0) {

			new AlertDialog.Builder(RegisterActivity.this).setTitle("错误提示")
					.setMessage("注册失败").setPositiveButton("确定", null).show();
			return false;
		} else {
			new AlertDialog.Builder(RegisterActivity.this).setTitle("消息提示")
					.setMessage("注册成功").setPositiveButton("确定", null).show();
			return true;
		}
		// long id = db.execSQL("insert into myFilms values(null,?,?)"
		// , new String[]{name,pwd});

	}

	public void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}
}
