package com.zjut.express.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.ActionBar;
import com.zjut.express.activity.R;
import com.zjut.express.fragment.QuickSearchFragment;

/**
 * 主界面
 * 
 * @author HuGuojun
 * @date 2013-11-20 下午8:59:56
 * @modify
 * @version 1.0.0
 */
public class MainActivity extends BaseActivity {
	
	private Fragment content;
	private FragmentManager manager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlidingActionBarEnabled(true);
		
		if (savedInstanceState != null)
			content = getSupportFragmentManager().getFragment(savedInstanceState, "content");
		if (content == null)
			content = new QuickSearchFragment();	
		
		setContentView(R.layout.activity_main);
		manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.content_frame, content).commit();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "content", content);
	}

	public void changeContent(Fragment fragment) {
		content = fragment;
		manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
		ActionBar actionBar = getSupportActionBar();
		if (content instanceof QuickSearchFragment) {
			actionBar.setTitle("快速查询");
		}
	}

}
