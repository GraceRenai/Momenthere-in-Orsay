package com.momenthere.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.momenthere.HttpUtils;
import com.momenthere.Message;
import com.momenthere.MyDialog;
import com.momenthere.MyDialogListener;
import com.momenthere.Postcard;
import com.momenthere.R;
import com.momenthere.R.id;
import com.momenthere.R.layout;
import com.momenthere.R.style;
import com.momenthere.slidingmenu.model.NavDrawerItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// drawerlayout
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
    

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	
	

	private ImageButton button;
	public TextView text1, text2, text3, text4, text5, text6, text7, text8,
			textView1;
	public long date = new Date().getTime();
	public String info[][] = new String[8][4];
	public LocationManager locationManager;
	private String username;
	private ImageButton mode;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);

		Log.i("json", "1");

		Bundle extras = this.getIntent().getExtras();
		username = extras.getString("username");
		Log.i("json", "2");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);
		Toast.makeText(getApplicationContext(),
				"Welcome to MomentHere in Orsay", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.activity_main);

		Log.i("json", "3");

		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		text5 = (TextView) findViewById(R.id.text5);
		text6 = (TextView) findViewById(R.id.text6);
		text7 = (TextView) findViewById(R.id.text7);
		text8 = (TextView) findViewById(R.id.text8);
		textView1 = (TextView) findViewById(R.id.textViewUserName);

		SpannableString current_user = new SpannableString(" \n  Hi, "
				+ username);
		textView1.setText(current_user);
		Log.i("json", "4");
		init("orsay");
		Log.i("json", "5");
		updateView(text1, text2, text3, text4, text5, text6, text7, text8);

		button = (ImageButton) findViewById(R.id.buttonMessage);
		/**
		 * new
		 */
		mode = (ImageButton) findViewById(R.id.imageButton1);
		mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Postcard.class);
				intent.putExtra("username",username);
				startActivity(intent);
				finish();

			}

		});

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new MyDialog(MainActivity.this,
						R.style.MyDialog, new MyDialogListener() {

							@Override
							public void onOkClick(String message)
									throws ClientProtocolException, IOException {
								// TODO Auto-generated method stub
								// Toast.makeText(getApplicationContext(),
								// message, Toast.LENGTH_SHORT).show();
								Message new_guest = new Message();
								new_guest.name = username;
								new_guest.message = message;
								new_guest.time = new Timestamp(date).toString()
										.substring(0, 10);
								String time = new_guest.time;

								// Toast.makeText(getApplicationContext(),
								// new_guest.time, Toast.LENGTH_SHORT).show();

								new_guest.location = "orsay";

								NameValuePair pair1 = new BasicNameValuePair(
										"name", username);
								NameValuePair pair2 = new BasicNameValuePair(
										"message", message);
								NameValuePair pair3 = new BasicNameValuePair(
										"times", time);
								// NameValuePair pair3 = new
								// BasicNameValuePair("time,","2014-1-1");
								NameValuePair pair4 = new BasicNameValuePair(
										"location", "orsay");

								List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair1);
								pairList.add(pair2);
								pairList.add(pair3);
								pairList.add(pair4);

								String URL = "http://54.93.57.115:8080/myhttp/servlet/InsertAction";
								HttpEntity requestHttpEntity;
								requestHttpEntity = new UrlEncodedFormEntity(
										pairList);
								HttpPost httpPost = new HttpPost(URL);
								httpPost.setEntity(requestHttpEntity);
								HttpClient httpClient = new DefaultHttpClient();
								HttpResponse response = httpClient
										.execute(httpPost);
								HttpEntity httpEntity = response.getEntity();
								InputStream inputStream = httpEntity
										.getContent();
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(inputStream));

								int i = 0, j = 0, k = 0;
								for (i = 7; i > 0; i--) {
									for (j = 0; j < 3; j++) {
										k = i - 1;
										info[i][j] = info[k][j];
									}
								}
								info[0][0] = new_guest.message;
								info[0][1] = new_guest.name;
								info[0][2] = new_guest.time.toString()
										.substring(0, 10);

								updateView(text1, text2, text3, text4, text5,
										text6, text7, text8);

							}
						});
				dialog.setContentView(R.layout.dialog);
				dialog.show();
			}
		});
	}

	public void updateView(TextView text1, TextView text2, TextView text3,
			TextView text4, TextView text5, TextView text6, TextView text7,
			TextView text8) {

		text1.setText(info[0][0] + "\n\n" + info[0][1] + "\n" + info[0][2]);
		text2.setText(info[1][0] + "\n\n" + info[1][1] + "\n" + info[1][2]);
		text3.setText(info[2][0] + "\n\n" + info[2][1] + "\n" + info[2][2]);
		text4.setText(info[3][0] + "\n\n" + info[3][1] + "\n" + info[3][2]);
		text5.setText(info[4][0] + "\n\n" + info[4][1] + "\n" + info[4][2]);
		text6.setText(info[5][0] + "\n\n" + info[5][1] + "\n" + info[5][2]);
		text7.setText(info[6][0] + "\n\n" + info[6][1] + "\n" + info[6][2]);
		text8.setText(info[7][0] + "\n\n" + info[7][1] + "\n" + info[7][2]);

	}

	public void init(String location) {

		String path = "http://54.93.57.115:8080/myhttp2/servlet/MessageAction?action_flag="
				+ location;
		String jsonString = HttpUtils.getJsonContent(path);
		// List<Message> list = GsonTools.getMessages(jsonString,
		// Message.class);
		Gson gson = new Gson();
		List<Message> list = gson.fromJson(jsonString,
				new TypeToken<List<Message>>() {
				}.getType());

		/*
		 * Iterator<Message> it = list.iterator(); while(it.hasNext()){
		 * Log.i("json", "4.9: "+ it.next()); Log.i("json",
		 * "4.95: "+it.next().message); }
		 */

		// Message g = list.get(0);
		// Log.i("json", "4.0: "+g.getMessage());
		// System.out.println(list.get(i));// 利用get(int index)方法获得指定索引位置的对象
		// Toast.makeText(getApplicationContext(), list.get(j).toString(),
		// Toast.LENGTH_SHORT).show();
		for (int j = 0; j < list.size(); j++) {
			info[j][0] = list.get(j).message;
			info[j][1] = list.get(j).name;
			info[j][2] = list.get(j).time;
		}

		// Toast.makeText(getApplicationContext(), list.toString(),
		// Toast.LENGTH_LONG).show();
	}

}
