package com.example.filmsteward;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class LookTheFilm extends Activity {
	private Button btn_lBack, btn_lAalter;
	private TextView tv_lFilmName, tv_lFileText;
	private AddFilmsActivity afActivity;
	private String filmIdString;
	private String filmId;
	private boolean EDIT;
	MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.look_thefilm);
		findView();
		Intent intent = getIntent();
		filmId = intent.getStringExtra("filmId");
		if (filmId != null)
			EDIT = true;
		else
			EDIT = false;

		if (EDIT) {
			Cursor cursor = dbHelper.getReadableDatabase().query("filmPoster",
					new String[] { "filmName", "filmText" }, "_id = ?",
					new String[] { filmId }, null, null, null);
			while (cursor.moveToNext()) {
				tv_lFilmName.setText(cursor.getString(cursor
						.getColumnIndex("filmName")));
				tv_lFileText.setText(cursor.getString(cursor
						.getColumnIndex("filmText")));
			}
			cursor.close();
			dbHelper.close();
		}

	}

	private void findView() {
		btn_lBack = (Button) findViewById(R.id.btn_fBack);
		btn_lAalter = (Button) findViewById(R.id.btn_lAlter);
		tv_lFileText = (TextView) findViewById(R.id.tv_lFilmText);
		tv_lFilmName = (TextView) findViewById(R.id.tv_lFilmName);

	}
}
