package com.example.filmsteward;

import java.security.PublicKey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LandingActivity extends Activity {
	private EditText et_nameEditText;
	private EditText et_pwdEditText;
	private Button btn_landingButton;
	private Button btn_exitButton, btn_register;
	MyDatabaseHelper dbHelper;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_activity);
		et_nameEditText = (EditText) findViewById(R.id.et_name);
		et_pwdEditText = (EditText) findViewById(R.id.et_pwd);
		btn_exitButton = (Button) findViewById(R.id.btn_exit);
		btn_landingButton = (Button) findViewById(R.id.btn_landing);
		btn_register = (Button) findViewById(R.id.btn_register);
		dbHelper = new MyDatabaseHelper(LandingActivity.this, "myFilms.db",
				null, 1);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				intent.setClass(LandingActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		btn_landingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nameString = et_nameEditText.getText().toString();
				String pwdString = et_pwdEditText.getText().toString();
				if (nameString.equals("") || pwdString.equals("")) {
					new AlertDialog.Builder(LandingActivity.this)
							.setTitle("错误").setMessage("影迷代号或通行密码不能为空")
							.setPositiveButton("确定", null).show();
				} else {
					if (isUser(nameString, pwdString)) {
						Intent intent = new Intent();
						intent.setClass(LandingActivity.this,
								AddFilmsActivity.class);
						startActivity(intent);
					}

				}

			}
		});

	}

	public boolean isUser(String name, String pwd) {
		String str = "select * from userMessage where name = ? and pwd = ?";
		Cursor cursor = dbHelper.getReadableDatabase().rawQuery(str,
				new String[] { name, pwd });
		if (cursor.getCount() <= 0) {
			new AlertDialog.Builder(LandingActivity.this).setTitle("错误")
					.setMessage("影迷代号或通行密码错误").setPositiveButton("确定", null)
					.show();
			return false;
		} else {
			new AlertDialog.Builder(LandingActivity.this).setTitle("正确")
					.setMessage("登录成功").setPositiveButton("确定", null).show();
			return true;
		}

	}

}
