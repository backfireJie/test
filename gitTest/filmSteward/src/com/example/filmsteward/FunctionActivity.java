package com.example.filmsteward;

import java.security.PublicKey;
import java.util.Date;

import android.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class FunctionActivity extends Activity {
	private EditText et_filmsName, et_filmsText;
	private TextView tv_time;
	private Button btn_submit, btn_clear, btn_back;
	private Spinner sp_filmType;
	private ArrayAdapter arrayAdapter;
	private String filmsTypeString;
	private boolean EDIT = true;
	private String filmId;
	MyDatabaseHelper fDbHelper;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.function_activity);
		findView();
		Intent intent = getIntent();
		filmId = intent.getStringExtra("filmId");
		System.out.println("filmId------>" + filmId);
		if (filmId != null)
			EDIT = true;
		else
			EDIT = false;

		if (EDIT) {
			Cursor cursor = fDbHelper.getReadableDatabase().query("filmPoster",
					new String[] { "filmName", "filmText" }, "_id = ?",
					new String[] { filmId }, null, null, null);
			System.out.println(cursor.moveToFirst());
			while (cursor.moveToNext()) {
				String filmName = cursor.getString(cursor
						.getColumnIndex("filmName"));
				String filmText = cursor.getString(cursor
						.getColumnIndex("filmText"));
				et_filmsName.setText(filmName);
				et_filmsText.setText(filmText);
				
			}
			cursor.close();
			fDbHelper.close();
		}
		setListener();

	}

	private void findView() {
		et_filmsName = (EditText) findViewById(R.id.et_filmName);
		et_filmsText = (EditText) findViewById(R.id.et_context);
		tv_time = (TextView) findViewById(R.id.tv_time);
		btn_submit = (Button) findViewById(R.id.btn_fSubmit);
		btn_clear = (Button) findViewById(R.id.btn_fClear);
		btn_back = (Button) findViewById(R.id.btn_fBack);
		sp_filmType = (Spinner) findViewById(R.id.sp_filmType);
		fDbHelper = new MyDatabaseHelper(FunctionActivity.this, "myFilms.db",
				null, 1);

		chooseFilmType(sp_filmType);
	}

	private void chooseFilmType(View v) {
		arrayAdapter = ArrayAdapter.createFromResource(FunctionActivity.this,
				R.array.filmType, android.R.layout.simple_spinner_item);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp_filmType.setAdapter(arrayAdapter);
		sp_filmType.setOnItemSelectedListener(new SpinnerSelectedListener());
		sp_filmType.setVisibility(View.VISIBLE);

	}

	private void setListener() {
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String filmsNameString = et_filmsName.getText().toString();
				String filmsTextStrig = et_filmsText.getText().toString();

				if (!("".equals(filmsNameString))
						&& !("".equals(filmsTextStrig))) {
					insertFilm(fDbHelper.getReadableDatabase(),
							filmsNameString, filmsTextStrig);
					Toast.makeText(FunctionActivity.this, "保存成功",3000).show();
				} else {
					Toast.makeText(FunctionActivity.this, "写上影名、影评！", 5000)
							.show();
				}
			}
		});
		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String filmsNameString = et_filmsName.getText().toString();
				String filmsTextStrig = et_filmsText.getText().toString();
				if ("".equals(filmsNameString) && "".equals(filmsTextStrig)) {

				} else {
					et_filmsName.setText("");
					et_filmsText.setText("");
				}

			}
		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FunctionActivity.this.finish();
			}
		});

	}

	private void insertFilm(SQLiteDatabase db, String filmsName,
			String filmsText) {
		ContentValues values = new ContentValues();
		values.put("filmName", filmsName);
		values.put("filmText", filmsText);

		long id = db.insert("filmPoster", null, values);
		if (id <= 0) {
			Toast.makeText(FunctionActivity.this, "错误提示：保存失败", 5000).show();
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (fDbHelper != null) {
			fDbHelper.close();
		}
	}

	class SpinnerSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String typeString = arrayAdapter.getItem(position).toString();

			Toast.makeText(FunctionActivity.this, typeString, 4000).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}

}
