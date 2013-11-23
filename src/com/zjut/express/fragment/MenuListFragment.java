package com.zjut.express.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zjut.express.activity.MainActivity;
import com.zjut.express.activity.R;

/**
 * 侧滑菜单的菜单项设置
 * 
 * @author HuGuojun
 * @date 2013-11-20 上午9:14:56
 * @modify
 * @version 1.0.0
 */
@SuppressLint("Recycle")
public class MenuListFragment extends Fragment implements OnItemClickListener {
	
	private String[] titles;
	private TypedArray icons;
	private ListView listView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.slide_menu_list, null);
		listView = (ListView) view.findViewById(R.id.slide_menu_listview);
		listView.setOnItemClickListener(this);
		return view; 
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		titles = getResources().getStringArray(R.array.slide_menu_items);
		icons = getResources().obtainTypedArray(R.array.slide_menu_items_icons);
		SimpleAdapter adapter = new SimpleAdapter(getActivity());
		for (int i = 0; i < titles.length; i++) {
			MenuItem item = new MenuItem(titles[i], icons.getResourceId(i, 0));
			adapter.add(item);
		}
		listView.setAdapter(adapter);
	}

	public class SimpleAdapter extends ArrayAdapter<MenuItem> {

		public SimpleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.slide_menu_item, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.menu_item_icon);
			icon.setImageResource(getItem(position).icon);
			TextView title = (TextView) convertView.findViewById(R.id.menu_item_title);
			title.setText(getItem(position).title);
			return convertView;
		}
		
	}
	
	private class MenuItem {
		
		private String title;
		private int icon;
		
		public MenuItem(String title, int icon) {
			this.title = title;
			this.icon = icon;
		}
	}

	private void changeFragment(Fragment fragment) {
		if (getActivity() == null) {
			return;
		}
		if (getActivity() instanceof MainActivity) {
			MainActivity main = (MainActivity) getActivity();
			main.changeContent(fragment);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Fragment content = null;
		switch (position) {
		case 0:
			content = new QuickSearchFragment();
			break;
		case 1:
			content = new OwnerOrderFragment();
			break;
		case 2:
			content = new ExpressCompanyFragment();
			break;
		case 3:
			content = new HistorySearchFragment();
			break;
		case 4:
			content = new MoreOptionFragment();
			break;
		}
		if (content != null) {
			changeFragment(content);
		}
	}
	
}
