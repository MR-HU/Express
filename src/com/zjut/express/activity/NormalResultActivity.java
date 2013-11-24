package com.zjut.express.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.zjut.express.adapter.ResultAdapter;
import com.zjut.express.entity.SearchResult;

/**
 * 快速查询结果页面(普通页面)
 * 
 * @author HuGuojun
 * @date 2013-11-24 下午12:20:15
 * @modify
 * @version 1.0.0
 */
public class NormalResultActivity extends SherlockActivity {
	
	private String json;
	private boolean isSigned;
	private ListView listView;
	private ResultAdapter adapter;
	private List<SearchResult> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal_result);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(R.string.search_result);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			json = bundle.getString("json");
		}
		initData();
		initView();
	}

	private void initData() {
		data = new ArrayList<SearchResult>();
		try {
			JSONObject jObject = new JSONObject(json);
			//"state"字段为3表示订单已经签收
			isSigned = jObject.optString("state").equals("3") ? true : false;
			JSONArray jArray = jObject.getJSONArray("data");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject object = jArray.getJSONObject(i);
				SearchResult result = new SearchResult();
				result.setTime(object.opt("time"));
				result.setContent(object.opt("context"));
				data.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initView() {
		adapter = new ResultAdapter(this);
		adapter.setList(data);
		adapter.setSigned(isSigned);
		listView = (ListView) findViewById(R.id.result_listview);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
}
