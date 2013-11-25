package com.zjut.express.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.zjut.express.activity.NormalResultActivity;
import com.zjut.express.activity.R;
import com.zjut.express.activity.SearchResultActivity;
import com.zjut.express.adapter.CompanyAdapter;
import com.zjut.express.db.DBManager;
import com.zjut.express.entity.Company;
import com.zjut.express.entity.History;
import com.zjut.express.util.CalendaUtil;
import com.zjut.express.util.HttpPostRequest;
import com.zjut.express.util.Util;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

/**
 * 快速查询页面
 * 
 * @author HuGuojun
 * @date 2013-11-20 下午9:01:36
 * @modify
 * @version 1.0.0
 */
public class QuickSearchFragment extends Fragment implements OnClickListener {

	private String code = "";
	private String order = "";
	private String name = "";
	private List<Company> data;
	private Dialog dialog;
	private Button searchButton;
	private View view, dialoView;
	private ImageView chooseView;
	private EditText nameText, numText;
	private GetDataTask task;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Resources resources = getActivity().getResources();
		String[] codes = resources.getStringArray(R.array.express_company_code);
		String[] names = resources.getStringArray(R.array.express_company_name);
		data = new ArrayList<Company>();
		for (int i = 0; i < names.length; i++) {
			Company company = new Company();
			company.setCode(codes[i]);
			company.setName(names[i]);
			data.add(company);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_quick_search, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		chooseView = (ImageView) view.findViewById(R.id.btn_choose_company);
		nameText = (EditText) view.findViewById(R.id.edit_company_name);
		numText = (EditText) view.findViewById(R.id.edit_order_num);
		searchButton = (Button) view.findViewById(R.id.btn_quick_search);
		nameText.setOnClickListener(this);
		chooseView.setOnClickListener(this);
		searchButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_choose_company:
		case R.id.edit_company_name:
			createDialog();
	        registerEvent();
			break;
		case R.id.btn_quick_search:
			handleSearch();
			break;
		}
	}

	/**
	 * 创建Dialog,用于快递公司选择
	 */
	private void createDialog() {
		dialoView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_over_lay, null);
		dialog = new Dialog(getActivity(), R.style.company_list_dialog);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams attribute = window.getAttributes();
		//设置dialog距离左边x,距离上边y
		attribute.gravity = Gravity.TOP | Gravity.LEFT;
		attribute.x = 10; 
		attribute.y = Util.dip2px(getActivity(), 48);
		window.setAttributes(attribute);
		//设置dialog的宽高
		int width = Util.getScreenWidth(getActivity()) - 20;
		int height = Util.getScreenHeight(getActivity()) / 2 + 40;
		LayoutParams params = new LayoutParams(width, height);
		dialog.setContentView(dialoView, params);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
	
	/**
	 * 为Dialog中的列表注册点击事件
	 */
	private void registerEvent() {
		ListView listView = (ListView) dialoView.findViewById(R.id.listview_overlay);
		CompanyAdapter adapter = new CompanyAdapter(getActivity());
		adapter.addList(data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
				Company company = data.get(position);
				code = company.getCode();
				name = company.getName();
				nameText.setText(name);
				dialog.cancel();
			}
		});
	}
	
	private void handleSearch() {
		order = numText.getText().toString();
		if (code.equals("") || order.equals("")) {
			Toast.makeText(getActivity(), R.string.search_tip, Toast.LENGTH_LONG).show();
		} else {
			saveSearchRecord();
			if (task != null) task.cancel(true);
			task = new GetDataTask();
			task.execute();
		}
	}

	/**
	 * 保存当条查询记录
	 * @return void
	 */
	private void saveSearchRecord() {
		History record = new History();
		record.setDate(CalendaUtil.GetCurrentDate());
		record.setTime(CalendaUtil.GetCurrentTime());
		record.setCode(code);
		record.setOrder(order);
		record.setName(name);
		DBManager manager = new DBManager(getActivity());
		manager.saveOrUpdate(record);
		manager.close();
	}

	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String url = "http://api.kuaidi100.com/api?id=cd0c64583bf91a80&" +
					"com=company&nu=order&valicode=o&show=0&muti=1&order=desc";
			url = url.replace("company", code).replace("order", order);
			return HttpPostRequest.getDataFromWebServer(url);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject object = new JSONObject(result);
				int status = Integer.parseInt(object.optString("status"));
				//status为0表示物流单暂无结果,为1表示查询成功,为2表示接口异常
				switch (status) {
				case 0:
					Toast.makeText(getActivity(), object.optString("message"), Toast.LENGTH_LONG).show();
					break;
				case 1:
					Intent intente = new Intent(getActivity(), NormalResultActivity.class);
					intente.putExtra("json", result);
					startActivity(intente);
					break;
				case 2:
					Intent intent = new Intent(getActivity(), SearchResultActivity.class);
					intent.putExtra("code", code);
					intent.putExtra("order", order);
					startActivity(intent);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
