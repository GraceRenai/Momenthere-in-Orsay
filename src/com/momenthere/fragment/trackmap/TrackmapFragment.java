package com.momenthere.fragment.trackmap;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.momenthere.AskForAddress;
import com.momenthere.AskForAddressListener;
import com.momenthere.HttpUtils;
import com.momenthere.R;
import com.momenthere.fragment.Message;
import com.momenthere.main.MainActivity;
import com.momenthere.main.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

/**
 * @author Xiuming XU (gracexuxiuming@gmail.com)
 */
public class TrackmapFragment extends Fragment implements Utility {
	private String baseURL = "http://" + base + "/servlet/TrackMapAction";
	// private String baseURL =
	// "http://localhost:8080/myhttp/servlet/TrackMapAction";
	// context
	private Activity mActivity;
	private String username;
	
	private String TAG = "Okay2";
	List<Message> list = null;
	private int nodeSize;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i("sha", "1");
		mActivity = activity;
	}

	// creates and returns the view hierarchy associated with the fragment.
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.postcard, container, false);
		Log.i("sha", "2");
		return rootView;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// replace the fragmentcontent

		Bundle extras = mActivity.getIntent().getExtras();
		username = extras.getString("username");
		Log.i("sha", "3");
		// TODO add codes....extras
		// username
		getData("wanglan");

	}

	private View.OnClickListener getImage = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, 0);
		}
	};

	// get json content
	public void getData(String username) {

		String path = "http://" + base + "/servlet/TrackmapAction?action_flag="
				+ username;
		Log.i(TAG, "CONNECT .....OKAY");
		String jsonString = HttpUtils.getJsonContent(path);
		Log.i(TAG, jsonString);
		Gson gson = new Gson();
		list = gson.fromJson(jsonString,
				new TypeToken<List<Message>>() {
				}.getType());

		nodeSize = list.size();
		Log.i(TAG,String.valueOf(nodeSize));
	}

}
