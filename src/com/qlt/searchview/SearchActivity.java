package com.qlt.searchview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ListView;

import com.qlt.searchview.lib.MSearchView;

public class SearchActivity extends FragmentActivity implements OnClickListener, OnTouchListener {

	MSearchView mSearchView;
	ListView	mListView;
	List<String> mStrList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		mListView = (ListView) findViewById(R.id.search_result_listview);
		mListView.setOnTouchListener(this);
		mSearchView = (MSearchView) findViewById(R.id.cm_searchview);
		mSearchView.mBtCancelSearch.setVisibility(View.VISIBLE);
		mSearchView.mEtSearch.setCursorVisible(true);
		mSearchView.mBtCancelSearch.setOnClickListener(this);
		MyAdapter adapter = new MyAdapter(mListView, getApplicationContext(), android.R.layout.simple_list_item_1, mStrList);
		mSearchView.setFilterableAdapter(adapter);
		mListView.setAdapter(adapter);
	}


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.searchview_cancel_bt ) {
			setResult(200);
			exitWithAnim();
		}
	}


	private void exitWithAnim() {
		finish();
		overridePendingTransition(R.anim.activity_in, R.anim.activity_finish);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitWithAnim();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			exitWithAnim();
		}
		return true;
	}
}
