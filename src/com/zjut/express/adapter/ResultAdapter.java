package com.zjut.express.adapter;

import java.util.Collections;
import java.util.List;

import com.zjut.express.activity.R;
import com.zjut.express.entity.SearchResult;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 普通查询结果数据适配器
 * 
 * @author HuGuojun
 * @date 2013-11-24 下午12:39:11
 * @modify
 * @version 1.0.0
 */
public class ResultAdapter extends BaseAdapter {
	
	private static final int TYPE_SINGLE = 0;
	private static final int TYPE_DOUBLE = 1;
	
	private List<SearchResult> data;
	private LayoutInflater inflater;
	private boolean isSigned;

	public ResultAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}
	
	public void setList(List<SearchResult> list) {
		Collections.reverse(list);
		this.data = list;
	}
	
	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			holder = new ViewHolder();
			if (type == TYPE_SINGLE) {
				convertView = inflater.inflate(R.layout.item_result_single, null);
			} else {
				convertView = inflater.inflate(R.layout.item_result_double, null);
			}
			holder.dateView = (TextView) convertView.findViewById(R.id.txt_result_date);
			holder.timeView = (TextView) convertView.findViewById(R.id.txt_result_time);
			holder.contentView = (TextView) convertView.findViewById(R.id.txt_result_content);
			holder.tagView = (ImageView) convertView.findViewById(R.id.image_result_tag);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if ((position == data.size() - 1) && isSigned) {
			holder.tagView.setBackgroundResource(R.drawable.result_circle_finish);
			holder.dateView.setTextColor(Color.RED);
			holder.timeView.setTextColor(Color.RED);
			holder.contentView.setTextColor(Color.RED);
		} else {
			holder.tagView.setBackgroundResource(R.drawable.result_circle_process);
			holder.dateView.setTextColor(Color.BLACK);
			holder.timeView.setTextColor(Color.BLACK);
			holder.contentView.setTextColor(Color.BLACK);
		}
		SearchResult result = data.get(position);
		String[] dateAndTime = result.getTime().toString().split(" ");
		holder.dateView.setText(dateAndTime[0]);
		holder.timeView.setText(dateAndTime[1]);
		holder.contentView.setText(result.getContent().toString());
		return convertView;
	}
	
	@Override
	public int getItemViewType(int position) {
		if (position % 2 == 0) {
			return TYPE_DOUBLE;
		} else {
			return TYPE_SINGLE;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private static final class ViewHolder {
		TextView dateView;
		TextView timeView;
		ImageView tagView;
		TextView contentView;
	}
}
