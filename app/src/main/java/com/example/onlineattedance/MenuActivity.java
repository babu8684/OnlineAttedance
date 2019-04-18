package com.example.onlineattedance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	Button addStudent;
	Button viewStudent;
	Button logout;
	Button attendance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		addStudent =(Button)findViewById(R.id.addstudent);
		viewStudent =(Button)findViewById(R.id.viewstudent);
		attendance = findViewById(R.id.attedance);
		logout =(Button)findViewById(R.id.logout);
		
		addStudent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent =new Intent(MenuActivity.this,AddStudent.class);
				startActivity(intent);
			}
		});

		viewStudent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent =new Intent(MenuActivity.this,viewstudent.class);
				startActivity(intent);
			}
		});
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent =new Intent(MenuActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

	}
}

