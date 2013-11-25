package com.zjut.express.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zjut.express.activity.R;
import com.zjut.express.entity.History;

/**
 * 历史查询记录数据会配齐
 * 
 * @author HuGuojun
 * @date 2013-11-24 下午8:39:50
 * @modify
 * @version 1.0.0
 */
public class HistoryAdapter extends BaseAdapter {
	
	private List<History> data;
	private LayoutInflater inflater;
	
	public HistoryAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setList(List<History> data) {
		this.data = data;
	}
	
	public void deleteItem(History record) {
		data.remove(record);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_history, null);
			holder.dateView = (TextView) convertView.findViewById(R.id.txt_history_date);
			holder.timeView = (TextView) convertView.findViewById(R.id.txt_history_time);
			holder.orderView = (TextView) convertView.findViewById(R.id.txt_history_order);
			holder.nameView = (TextView) convertView.findViewById(R.id.txt_history_name);
			holder.deleteBtn = (Button) convertView.findViewById(R.id.delete_history_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final History record = data.get(position);
		holder.dateView.setText(record.getDate());
		holder.timeView.setText(record.getTime());
		holder.orderView.setText("单号:" + record.getOrder());
		holder.nameView.setText(record.getName());
		holder.deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(deleteListener != null) {
					deleteListener.onDelete(record);
				}
			}
		});
		return convertView;
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 :data.size();
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
		TextView orderView;
		TextView nameView;
		Button deleteBtn;
	}

	/**
	 * 回调接口,处理删除按钮事件
	 */
	public interface OnDeleteListener {
		void onDelete(History record);
	}
	
	private OnDeleteListener deleteListener;
	
	//提供给外部调用
	public void setOnDeleteListener(OnDeleteListener deleteListener) {
		this.deleteListener = deleteListener;
	}
	
}
