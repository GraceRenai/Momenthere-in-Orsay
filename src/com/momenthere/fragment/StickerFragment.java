package com.momenthere.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.momenthere.MyDialog;
import com.momenthere.MyDialogListener;
import com.momenthere.R;
import com.momenthere.main.MainActivity;
import com.momenthere.main.NavDrawerItem;
import com.momenthere.main.NavDrawerListAdapter;
import com.momenthere.main.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StickerFragment extends Fragment implements Utility{

	
	private String URL = "http://"+ base +"/servlet/InsertAction";

	private ImageButton button;
	public TextView text1, text2, text3, text4, text5, text6, text7, text8,
			textView1;
	public long date = new Date().getTime();
	public String info[][] = new String[8][4];
	public LocationManager locationManager;
	private String username;
	private ImageButton mode;

	private Activity mActivity;
	private String position = "orsay";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_stickerwall,
				container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		Bundle extras = mActivity.getIntent().getExtras();
		username = extras.getString("username");

		// ???
		// mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// Window myWindow = mActivity.getWindow();
		// myWindow.setFlags(flag, flag);
		// Toast.makeText(mActivity.getApplicationContext(),
		// "Welcome to MomentHere in Orsay", Toast.LENGTH_SHORT).show();
		// mActivity.setContentView(R.layout.activity_main);

		text1 = (TextView) mActivity.findViewById(R.id.text1);
		text2 = (TextView) mActivity.findViewById(R.id.text2);
		text3 = (TextView) mActivity.findViewById(R.id.text3);
		text4 = (TextView) mActivity.findViewById(R.id.text4);
		text5 = (TextView) mActivity.findViewById(R.id.text5);
		text6 = (TextView) mActivity.findViewById(R.id.text6);
		text7 = (TextView) mActivity.findViewById(R.id.text7);
		text8 = (TextView) mActivity.findViewById(R.id.text8);
		textView1 = (TextView) mActivity.findViewById(R.id.textViewUserName);

		SpannableString current_user = new SpannableString(" \n  Hi, "
				+ username);
		textView1.setText(current_user);
		Log.i("json", "4");
		init(position);
		Log.i("json", "5");
		updateView(text1, text2, text3, text4, text5, text6, text7, text8);

		button = (ImageButton) mActivity.findViewById(R.id.buttonMessage);
		mode = (ImageButton) mActivity.findViewById(R.id.imageButton1);

		mode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment postcard =  new PostcardFragment();
				FragmentManager fragmentManager = mActivity.getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.stickerwall, postcard).commit();
			}


		});

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog dialog = new MyDialog(mActivity, R.style.MyDialog,
						new MyDialogListener() {

							@Override
							public void onOkClick(String message)
									throws ClientProtocolException, IOException {
								Message new_guest = new Message();
								new_guest.name = username;
								new_guest.message = message;
								new_guest.time = new Timestamp(date).toString()
										.substring(0, 10);
								String time = new_guest.time;


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
								
								//Insert action

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

		String path = "http://"+base+"/servlet/MessageAction?action_flag="
				+ location;
		String jsonString = HttpUtils.getJsonContent(path);
		Gson gson = new Gson();
		List<Message> list = gson.fromJson(jsonString,
				new TypeToken<List<Message>>() {
				}.getType());

		for (int j = 0; j < list.size(); j++) {
			info[j][0] = list.get(j).message;
			info[j][1] = list.get(j).name;
			info[j][2] = list.get(j).time;
		}

	}

}
