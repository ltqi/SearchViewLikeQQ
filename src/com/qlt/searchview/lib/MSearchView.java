package com.qlt.searchview.lib;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.qlt.searchview.R;

public class MSearchView extends RelativeLayout {

	public static int SEARCH_BY_EDIT = 1;
	public static int SEARCH_BY_CTRL = 2;

	public Button mBtCancelSearch;
	public ImageButton mBtDelWords;
	public EditText mEtSearch;

	private IFilterableAdapter mFilterableAdapter = null;
	private int searchMode = SEARCH_BY_EDIT;
	
	public MSearchView(Context context) {
		super(context);
		initView(context);
	}

	public MSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void setFilterableAdapter(IFilterableAdapter filterableAdapter) {
		mFilterableAdapter = filterableAdapter;
	}

	public void setSearchMode(int mode) {
		searchMode = mode;
	}

	public int getSearchMode() {
		return searchMode;
	}

	public interface FooterListViewHandler {
		void callBack();
	}

	private void executeFilter() {
		if (getSearchMode() == SEARCH_BY_EDIT && mFilterableAdapter != null)
			mFilterableAdapter.setFilterString(mEtSearch.getText().toString());
	}

	private void initView(final Context context) {
		LayoutInflater.from(context).inflate(R.layout.searchview_layout, this);

		mBtCancelSearch = (Button) findViewById(R.id.searchview_cancel_bt);
		mBtCancelSearch.setVisibility(View.GONE);
		mBtCancelSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBtCancelSearch.setVisibility(View.GONE);
				mEtSearch.setText("");
				mEtSearch.clearFocus();
				mEtSearch.setCursorVisible(false);
				executeFilter();
				InputMethodManager imm = (InputMethodManager) getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
			}
		});

		mBtDelWords = (ImageButton) findViewById(R.id.searchview_del_words);
		mBtDelWords.setVisibility(View.GONE);
		mBtDelWords.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mEtSearch.setText("");
				executeFilter();
			}
		});

		mEtSearch = (EditText) findViewById(R.id.searchview_et);
		mEtSearch.clearFocus();
		mEtSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {//open inputmd, show cursor & cancel bt.
				Window window = ((Activity)context).getWindow();
				WindowManager.LayoutParams params = window.getAttributes();
				 if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					 mEtSearch.setCursorVisible(true);
					 mBtCancelSearch.setVisibility(View.VISIBLE);
					 window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
						    params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
				 }
			}
		});
		mEtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					mBtCancelSearch.setVisibility(View.VISIBLE);
//				} else {
//					mBtCancelSearch.setVisibility(View.GONE);
//				}
			}
		});
		mEtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i,
					int i2, int i3) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2,
					int i3) {
			}

			@Override
			public void afterTextChanged(Editable editable) {
				String inputWords = editable.toString();
				if (TextUtils.isEmpty(inputWords)) {
					mBtDelWords.setVisibility(View.GONE);
				} else {
					mBtDelWords.setVisibility(View.VISIBLE);
				}
				executeFilter();
			}
		});
//		mSearchText.setOnKeyListener(new EditText.OnKeyListener() {
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				if (event.getAction() == KeyEvent.ACTION_DOWN
//						&& event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
//					mSearchText.onKeyDown(keyCode, event);
//					executeFilter();
//					return true;
//				}
//				return false;
//			}
//		});
	}
}
