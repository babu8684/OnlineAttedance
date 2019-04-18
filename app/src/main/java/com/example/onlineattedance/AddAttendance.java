package com.example.onlineattedance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddAttendance extends Activity {

	Button submit;
	EditText regno;
	EditText date;
	Spinner type;
	JSONObject json_data;
	HttpURLConnection con;
	String query, results;
	String department;
	ProgressDialog mProgressDialog;

	private String[] deptartment = new String[]{"Present", "Absent"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_attendance);
        date = findViewById(R.id.date);
        type = findViewById(R.id.type);
        regno = findViewById(R.id.regno);
        submit = (Button) findViewById(R.id.submit);

    }

	final class Create extends AsyncTask<String, Void, Void> {
		ProgressDialog mProgressDialog;


		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			mProgressDialog = new ProgressDialog(AddAttendance.this);
			mProgressDialog.setMessage("Creating record please wait..");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				Uri.Builder builder = new Uri.Builder().appendQueryParameter("regno", regno.getText().toString().trim()).appendQueryParameter("date", date.getText().toString().trim())
                        .appendQueryParameter("type",type.getPrompt().toString().trim());
				query = builder.build().getEncodedQuery();
				String url = "https://citrus-realignments.000webhostapp.com/addattendance/create.php";
				URL obj = new URL(url);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
				con.setRequestProperty("Accept-Language", "UTF-8");
				con.setDoOutput(true);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
				outputStreamWriter.write(query);
				outputStreamWriter.flush();
			} catch (Exception e) {
				android.util.Log.e("Fail 1", e.toString());
			}
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = in.readLine()) != null) {
					sb.append(line + "\n");
				}
				results = sb.toString();
			} catch (Exception e) {
				android.util.Log.e("Fail 2", e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			try {
				json_data = new JSONObject(results);
				int code = (json_data.getInt("code"));
				if (code == 1) {
					final AlertDialog.Builder alert = new AlertDialog.Builder(AddAttendance.this);
					alert.setTitle("Success");
					alert.setMessage("Student Record Created");
					alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(@NonNull DialogInterface dialog, int whichButton) {
							dialog.cancel();
						}
					});
					alert.show();
				} else {
					final AlertDialog.Builder alert = new AlertDialog.Builder(AddAttendance.this);
					alert.setTitle("Failed");
					alert.setMessage("Creating Student Failed");
					alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(@NonNull DialogInterface dialog, int whichButton) {
							dialog.cancel();
						}
					});
					alert.show();
				}
			} catch (Exception e) {
				Log.e("Fail 3", e.toString());
			}

			mProgressDialog.dismiss();
		}
	}
}

