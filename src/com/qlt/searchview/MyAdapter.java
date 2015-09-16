package com.qlt.searchview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.qlt.searchview.lib.IFilterableAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyAdapter extends ArrayAdapter<String> implements IFilterableAdapter{

	ListView	mListView;
	Context context;
	List<String> objects;
	public MyAdapter(ListView	mListView, Context context, int textViewResourceId,
			List<String> objects) {
		super(context, textViewResourceId, objects);
		this.mListView = mListView;
		this.context = context;
		this.objects = objects;
		if (objects == null) {
			objects = new ArrayList<String>();
		}
	}


	@Override
	public void setFilterString(String filterString) {
		if (!TextUtils.isEmpty(filterString)) {
			objects.addAll(getData());
			List<String> searchResult = new ArrayList<String>();
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).contains(filterString) || filterString.contains(objects.get(i))) {
					searchResult.add(objects.get(i));
				}
			}
			objects.clear();
			objects.addAll(searchResult);
			mListView.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));
		} else {
			objects.clear();
			mListView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
		}
		notifyDataSetChanged();
	}
	
	private List<String> getData(){
		String[] mStrArr = { "111", "112", "113", "222", "223", "333", "332","456" };
		List<String> mStrList = new ArrayList<String>();
		mStrList.addAll(Arrays.asList(mStrArr));
		return mStrList;
	}

}
