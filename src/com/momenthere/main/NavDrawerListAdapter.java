package com.momenthere.main;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author Xiuming XU (gracexuxiuming@gmail.com)
 */
public class NavDrawerListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	@Override
	public int getCount() {
		return navDrawerItems.size();
	}
	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}
	}

}
