package com.zjut.express.fragment;

import java.util.List;

import com.zjut.express.activity.R;
import com.zjut.express.activity.SearchResultActivity;
import com.zjut.express.adapter.HistoryAdapter;
import com.zjut.express.adapter.HistoryAdapter.OnDeleteListener;
import com.zjut.express.db.DBManager;
import com.zjut.express.entity.History;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 历史查询页面
 * 
 * @author HuGuojun
 * @date 2013-11-20 下午9:00:48
 * @modify
 * @version 1.0.0
 */
public class HistorySearchFragment extends Fragment implements OnItemClickListener {
	
	private View view;
	private ListView listView;
	private List<History> data;
	private HistoryAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBManager manager = new DBManager(getActivity());
		data = manager.query();
		manager.close();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_history_search, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (data == null || data.size() < 1) {
			Toast.makeText(getActivity(), 
					R.string.no_history_record, Toast.LENGTH_LONG).show();
		} else {
			adapter = new HistoryAdapter(getActivity());
			adapter.setList(data);
			adapter.setOnDeleteListener(new DeleteListener());
			listView = (ListView) view.findViewById(R.id.listView_history);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		}
	}
	
	private class DeleteListener implements OnDeleteListener {

		@Override
		public void onDelete(String order) {
			Toast.makeText(getActivity(), order, Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), SearchResultActivity.class);
		intent.putExtra("code", "shunfeng");
		intent.putExtra("num", "117839619182");
		startActivity(intent);
	}

}
