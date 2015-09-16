package com.qlt.searchview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qlt.searchview.lib.MSearchView;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {

	MSearchView mSearchView;
	View mTopView;
	int y;

	ListView mListView;
	String[] mStrArr = { "111", "112", "113", "222", "223", "333", "332","456", "365" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTopView = findViewById(R.id.topview);

		mListView = (ListView) findViewById(R.id.search_listvew);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrArr);
		mListView.setAdapter(adapter);

		mSearchView = (MSearchView) findViewById(R.id.cm_searchview);
		mSearchView.mEtSearch.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		goSearchActivity();
	}

	int count = 0;
	private void goSearchActivity() {
		y = (int) mSearchView.getY();
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -y);
		animation.setFillAfter(true);
		animation.setDuration(600);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
				startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
			}
		});
		mSearchView.startAnimation(animation);
		mTopView.startAnimation(animation);
		mListView.startAnimation(animation);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
		animation.setDuration(500);
		animation.setFillAfter(true);
		mTopView.startAnimation(animation);
		mSearchView.startAnimation(animation);
		mListView.startAnimation(animation);
//		((ViewGroup)this.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0).startAnimation(animation);
		
	}

}
