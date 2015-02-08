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
import com.momenthere.fragment.PostcardFragment;
import com.momenthere.fragment.StickerFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.AdapterView;
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
	private String postion = "Orsay";

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// drawe layout
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
//we
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

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

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// Window myWindow = this.getWindow();
		// myWindow.setFlags(flag, flag);
		Toast.makeText(getApplicationContext(),
				"Welcome to MomentHere in Orsay", Toast.LENGTH_SHORT).show();
		Log.i("fragment", "okay2");

		init(postion );

	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new StickerFragment();
			break;
		case 1:
			fragment = new StickerFragment();
			break;
		 case 2:
			fragment = new PostcardFragment();
		// break;
		// case 3:
		// fragment = new CommunityFragment();
		// break;
		// case 4:
		// fragment = new PagesFragment();
		// break;
		// case 5:
		// fragment = new WhatsHotFragment();
		// break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			// replace the pre-fragment
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
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
