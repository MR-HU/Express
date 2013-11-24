package com.zjut.express.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * 快速查询结果页面(HTML5页面)
 * 
 * @author HuGuojun
 * @date 2013-11-24 上午12:37:25
 * @modify
 * @version 1.0.0
 */
public class SearchResultActivity extends SherlockActivity {

	private WebView webView;
	private String code, num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			code = bundle.getString("code");
			num = bundle.getString("num");
		}
		initView();
	}

	private void initView() {
		webView = (WebView) findViewById(R.id.webview_result);
		WebSettings sets = webView.getSettings();
		sets.setJavaScriptEnabled(true);
		sets.setSupportZoom(false);
		sets.setBuiltInZoomControls(false);
		String url = "http://m.kuaidi100.com/index_all.html?type=company&postid=order";
		url = url.replace("company", code).replace("order", num);
		webView.loadUrl(url);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {       
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {       
	        webView.goBack();       
	        return true;       
	    }       
	    return super.onKeyDown(keyCode, event);       
	}   
}


