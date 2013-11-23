package com.zjut.express.fragment;

import com.zjut.express.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 我的快递单页面
 * 
 * @author HuGuojun
 * @date 2013-11-20 下午9:01:17
 * @modify
 * @version 1.0.0
 */
public class OwnerOrderFragment extends Fragment {

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_owner_order, null);
		return view;
	}

}
