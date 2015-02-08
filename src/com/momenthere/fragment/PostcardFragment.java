package com.momenthere.fragment;

import com.momenthere.AskForAddress;
import com.momenthere.AskForAddressListener;
import com.momenthere.Postcard;
import com.momenthere.R;
import com.momenthere.main.MainActivity;

import android.app.Dialog;
import android.app.Fragment;
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
import android.app.Activity;
import android.content.Intent;

/**
 * @author Xiuming XU (gracexuxiuming@gmail.com)
 */
public class PostcardFragment extends Fragment {

	private ImageButton button1, button2, button3, button4, button5, navWall,
			sendButton;
	public ImageView postPreview;
	private EditText edit;

	private static final String IMAGE_UNSPECIFIED = "image/*";
	private String username;
	Uri uri = null;
	boolean flicker = false;
	// context
	private Activity mActivity;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i("sha","1");
		mActivity = activity;
	}

	// creates and returns the view hierarchy associated with the fragment.
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.postcard, container, false);
		Log.i("sha","2");
		return rootView;

	}

	public void onActivityCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// replace the fragmentcontent

		Bundle extras = mActivity.getIntent().getExtras();
		username = extras.getString("username");
		Log.i("sha","3");
		button1 = (ImageButton) mActivity.findViewById(R.id.takePhoto);
		button2 = (ImageButton) mActivity.findViewById(R.id.gallery);
		button3 = (ImageButton) mActivity.findViewById(R.id.textWrite);
		button4 = (ImageButton) mActivity.findViewById(R.id.audio);
		button5 = (ImageButton) mActivity.findViewById(R.id.video);
		navWall = (ImageButton) mActivity.findViewById(R.id.navWall);
		sendButton = (ImageButton) mActivity.findViewById(R.id.sendButton);
		edit = (EditText) mActivity.findViewById(R.id.postText);
		edit.setVisibility(View.INVISIBLE);
		postPreview = (ImageView) mActivity.findViewById(R.id.postcard);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);

			}

		});
		button2.setOnClickListener(getImage);

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = new AskForAddress(mActivity,
						R.style.AskForAddress, new AskForAddressListener() {
						});
				dialog.setContentView(R.layout.askforaddress);
				dialog.show();
			}

		});
		navWall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mActivity, MainActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
				mActivity.finish();
			}

		});
		postPreview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flicker == false) {
					postPreview.setImageDrawable(getResources().getDrawable(
							R.drawable.postcard3));
					flicker = true;
					edit.setEnabled(true);
					edit.setVisibility(View.VISIBLE);
				} else {
					if (uri != null) {
						postPreview.setImageURI(uri);
					} else {
						postPreview.setImageDrawable(getResources()
								.getDrawable(R.drawable.postcard1));
					}
					flicker = false;
					edit.setEnabled(false);
					edit.setVisibility(View.INVISIBLE);
				}

			}

		});



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
