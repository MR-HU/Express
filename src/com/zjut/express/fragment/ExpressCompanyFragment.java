package com.zjut.express.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjut.express.activity.R;

/**
 * 快递大全页面
 * 
 * @author HuGuojun
 * @date 2013-11-20 下午9:00:20
 * @modify
 * @version 1.0.0
 */
@SuppressLint({ "ResourceAsColor", "DefaultLocale" })
public class ExpressCompanyFragment extends Fragment implements OnItemClickListener {
	
	private final String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", 
										"J", "K", "L", "M", "N", "O", "P", "Q", "R",
										"S", "T", "U", "V", "W", "X", "Y", "Z" };
	//最右边的索引条
	private LinearLayout layoutIndex;
	//索引条中的索引字母高度
	private int height;
	
	//快递公司代码
	private String[] code;
	//快递公司名字
	private String[] name;
	//将快递公司的代码和名字关联并以HashMap的形式存放于列表中
	private List<HashMap<String, String>> list;
	//数据源,将快递公司的代码以及代码中首字母所包含的索引字母整合在一起
	private List<String> metaData;
	//保存索引字母在数据源中的位置
	private HashMap<String, Integer> selector;
	
	private View view;
	private ListView listView;
	private IndexListAdapter adapter;
	private TextView showView;
	private boolean isMeasured = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		initView(inflater);
		measureHeight();
		initData();
		sortMetaData();
		initSelector();
		return view;
	}

	private void initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_express_company, null);
		layoutIndex = (LinearLayout) view.findViewById(R.id.layout_index);
		layoutIndex.setBackgroundColor(Color.parseColor("#00FFFFFF"));
		showView = (TextView) view.findViewById(R.id.txt_show);
		listView = (ListView) view.findViewById(R.id.listView_company);
		adapter = new IndexListAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	/** 
	 * 计算索引条的高度,用于计算每个索引字母的高度
	 */
	private void measureHeight() {
		ViewTreeObserver observer = layoutIndex.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
            	if (!isMeasured) {
	                int layoutHeight = layoutIndex.getMeasuredHeight();
	                height = layoutHeight / alphabet.length;
	                initIndexLayoutView();
	                isMeasured = true;
            	}
                return true;
            }
        });
	}

	private void initData() {
		list = new ArrayList<HashMap<String,String>>();
		code = getResources().getStringArray(R.array.express_company_code);
		name = getResources().getStringArray(R.array.express_company_name);
		for (int i = 0; i < code.length; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(code[i], name[i]);
			list.add(map);
		}
	}
	
	/**
	 * 将索引字母和快递公司代码整合并按首字母排序
	 */
	public void sortMetaData() {
		//获取快递公司代码首字母(作为数据中包含的索引字母),添加到Set中,Set将保证数据的唯一性,无重复数据
		Set<String> set = new TreeSet<String>();
		for (String codeValue : code) {
			set.add(String.valueOf(codeValue.charAt(0)));
		}
		metaData = new ArrayList<String>(code.length + set.size());
		//先将索引字母放到数据源中
		metaData.addAll(set);
		//再将公司代码放到数据源中完成整合
		metaData.addAll(Arrays.asList(code));
		//按照首字母排序
		Collections.sort(metaData);
	}
	
	private void initSelector() {
		selector = new HashMap<String, Integer>();
		//循环字母表,找出metaData所包含的索引字母的位置
		for (int j = 0; j < alphabet.length; j++) {
			for (int i = 0; i < metaData.size(); i++) {
				if (metaData.get(i).equals(alphabet[j].toLowerCase())) {
					selector.put(alphabet[j], i);
				}
			}
		}
	}
	
	/**
	 * 绘制索引条
	 */
	private void initIndexLayoutView() {
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, height);
		for (int i = 0; i < alphabet.length; i++) {
			final TextView show = new TextView(getActivity());
			show.setLayoutParams(params);
			show.setText(alphabet[i]);
			show.setPadding(10, 0, 10, 0);
			layoutIndex.addView(show);
		}
		layoutIndex.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float y = event.getY();
				int index = (int) (y / height);
				if (-1 < index && index < alphabet.length) {
					//根据接触的位置获得索引字母
					String key = alphabet[index];
					if (selector.containsKey(key)) {
						//获得索引字母在数据源中的位置,并让列表定位到该位置
						int position = selector.get(key);
						//如果ListView有标题Header
						if (listView.getHeaderViewsCount() > 0) {
							listView.setSelectionFromTop(position + listView.getHeaderViewsCount(), 0);
						} else {
							listView.setSelectionFromTop(position, 0);
						}
						showView.setVisibility(View.VISIBLE);
						showView.setText(alphabet[index]);
					}
				}
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					layoutIndex.setBackgroundColor(Color.parseColor("#606060"));
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					layoutIndex.setBackgroundColor(Color.parseColor("#00FFFFFF"));
					showView.setVisibility(View.GONE);
					break;
				}
				return true;
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Toast.makeText(getActivity(), adapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 根据公司代码获取公司名字
	 */
	private String getValueFromMapListByKey(String key) {
		String value = null;
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> map = list.get(i);
			if (map.containsKey(key)) {
				value = map.get(key);
				break;
			}
		}
		return value;
	}

	private class IndexListAdapter extends BaseAdapter {
		
		private static final int INDEX = 0;
		private static final int COMPANY = 1;
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			int type = getItemViewType(position);
			if (convertView == null) {
				holder = new ViewHolder();
				if (type == INDEX) {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.item_index, null);
				} else {
					convertView = getActivity().getLayoutInflater().inflate(R.layout.item_company, null);
				}
				holder.itemView = (TextView) convertView.findViewById(R.id.text_item);
				convertView.setTag(holder);
	        } else {
	        	holder = (ViewHolder) convertView.getTag();
			}
			if (type == INDEX) {
				holder.itemView.setText(metaData.get(position).toLowerCase());
			} else {
				holder.itemView.setText(getValueFromMapListByKey(metaData.get(position)));
			}
			return convertView;
		}

		@Override
		public int getItemViewType(int position) {
			String item = metaData.get(position);
			if (item.length() == 1) {
				return INDEX;
			} else {
				return COMPANY;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getCount() {
			return metaData == null ? 0 : metaData.size();
		}

		@Override
		public Object getItem(int position) {
			return metaData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public boolean isEnabled(int position) {
			//如果是字母索引,则列表项不能点击
			if (metaData.get(position).length() == 1) {
				return false;
			}
			return super.isEnabled(position);
		}
	}
	
	private final static class ViewHolder {
		TextView itemView;
	}

}
