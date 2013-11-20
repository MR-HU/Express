package com.zjut.express.activity;

import com.actionbarsherlock.app.SherlockActivity;
import com.zjut.express.activity.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * »¶Ó­Ò³Ãæ
 * 
 * @author HuGuojun
 * @date 2013-11-19 ÏÂÎç3:24:54
 * @modify
 * @version 1.0.0
 */
public class WelcomeActivity extends SherlockActivity {
	
	private final static long DELAY_TIME = 5 * 1000;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		handler = new Handler();
	}

	@Override
	protected void onResume() {
		super.onResume();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, DELAY_TIME);
	}

}
