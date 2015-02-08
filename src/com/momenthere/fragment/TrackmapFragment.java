package com.momenthere.fragment;

import com.momenthere.AskForAddress;
import com.momenthere.AskForAddressListener;
import com.momenthere.R;
import com.momenthere.main.MainActivity;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * @author Xiuming XU (gracexuxiuming@gmail.com)
 */
public class TrackmapFragment extends Fragment {

	// context
	private Activity mActivity;
	private String username;

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
}
