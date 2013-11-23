package com.zjut.express.adapter;

import java.util.List;

import com.zjut.express.activity.R;
import com.zjut.express.entity.Company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 填充快递公司信息到ListView的适配器
 * 
 * @author HuGuojun
 * @date 2013-11-23 下午11:02:10
 * @modify
 * @version 1.0.0
 */
public class CompanyAdapter extends BaseAdapter {
	
	private List<Company> data;
	private LayoutInflater inflater;
	
	public CompanyAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void addList(List<Company> data) {
		this.data = data;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.dialog_list_item, null);
			holder.nameView = (TextView) convertView.findViewById(R.id.dialog_txt_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameView.setText(data.get(position).getName());
		return convertView;
	}

	private static final class ViewHolder {
		TextView nameView;
	}
}
