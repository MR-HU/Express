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
 * ��ݴ�ȫҳ��
 * 
 * @author HuGuojun
 * @date 2013-11-20 ����9:00:20
 * @modify
 * @version 1.0.0
 */
@SuppressLint({ "ResourceAsColor", "DefaultLocale" })
public class ExpressCompanyFragment extends Fragment implements OnItemClickListener {
	
	private final String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", 
										"J", "K", "L", "M", "N", "O", "P", "Q", "R",
										"S", "T", "U", "V", "W", "X", "Y", "Z" };
	//���ұߵ�������
	private LinearLayout layoutIndex;
	//�������е�������ĸ�߶�
	private int height;
	
	//��ݹ�˾����
	private String[] code;
	//��ݹ�˾����
	private String[] name;
	//����ݹ�˾�Ĵ�������ֹ�������HashMap����ʽ������б���
	private List<HashMap<String, String>> list;
	//����Դ,����ݹ�˾�Ĵ����Լ�����������ĸ��������������ĸ������һ��
	private List<String> metaData;
	//����������ĸ������Դ�е�λ��
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
	 * �����������ĸ߶�,���ڼ���ÿ��������ĸ�ĸ߶�
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
	 * ��������ĸ�Ϳ�ݹ�˾�������ϲ�������ĸ����
	 */
	public void sortMetaData() {
		//��ȡ��ݹ�˾��������ĸ(��Ϊ�����а�����������ĸ),��ӵ�Set��,Set����֤���ݵ�Ψһ��,���ظ�����
		Set<String> set = new TreeSet<String>();
		for (String codeValue : code) {
			set.add(String.valueOf(codeValue.charAt(0)));
		}
		metaData = new ArrayList<String>(code.length + set.size());
		//�Ƚ�������ĸ�ŵ�����Դ��
		metaData.addAll(set);
		//�ٽ���˾����ŵ�����Դ���������
		metaData.addAll(Arrays.asList(code));
		//��������ĸ����
		Collections.sort(metaData);
	}
	
	private void initSelector() {
		selector = new HashMap<String, Integer>();
		//ѭ����ĸ��,�ҳ�metaData��������������ĸ��λ��
		for (int j = 0; j < alphabet.length; j++) {
			for (int i = 0; i < metaData.size(); i++) {
				if (metaData.get(i).equals(alphabet[j].toLowerCase())) {
					selector.put(alphabet[j], i);
				}
			}
		}
	}
	
	/**
	 * ����������
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
					//���ݽӴ���λ�û��������ĸ
					String key = alphabet[index];
					if (selector.containsKey(key)) {
						//���������ĸ������Դ�е�λ��,�����б�λ����λ��
						int position = selector.get(key);
						//���ListView�б���Header
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
	 * ���ݹ�˾�����ȡ��˾����
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
			//�������ĸ����,���б���ܵ��
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
