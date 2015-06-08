package com.xiaoheinews.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
 
import com.xiaoheinews.activity.R;
import com.xiaoheinews.adapter.NewsAdapter;
import com.xiaoheinews.bean.NewsBean;
import com.xiaoheinews.utill.Constants;
import com.xiaoheinews.utill.ToolsUtil;
import com.xiaoheinews.utill.VolleyUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/***
 *  
 *
 */
public class newsFragment extends Fragment {
	
	
	
	/**
	 * 是否第一次加载
	 */
	private boolean init;

	/**
	 * newsBean的list
	 */
	List<NewsBean> newsBeanList;

	ListView newsListView;
	
	/**
	 * 创建视图的时候
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init = true;
 
		 
		View view = inflater.inflate(R.layout.list_news, null);
		newsListView = (ListView) view.findViewById(R.id.lv_list);
		return view;
	}

	/**
	 * 获取数据
	 */
	private void getservciesData() {
		
		
		// 网络获取json数据
		StringRequest stringRequest = new StringRequest(Constants.URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						newsBeanList = jsonToList(response);
						NewsAdapter adapter = new NewsAdapter(getActivity(), newsBeanList);
						newsListView.setAdapter(adapter);
						//Log.d("TAG", newsBeanList+"");
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		VolleyUtil.getQueue(getActivity()).add(stringRequest);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (getUserVisibleHint()) {
			getdata();
		}
	}

	/**
	 * 将json字符串转换为NewsEntity的list
	 * 
	 * @param jsonSting
	 * @return
	 */
	private ArrayList<NewsBean> jsonToList(String jsonSting) {
		JSONObject jsonObject;

		NewsBean newsBean;

		ArrayList<NewsBean> newsList = new ArrayList<NewsBean>();
		try {
			jsonObject = new JSONObject(jsonSting);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				newsBean = new NewsBean();
				newsBean.setIconUrl(jsonObject.getString("picSmall"));
				newsBean.setTitle(jsonObject.getString("name"));
				newsBean.setContent(jsonObject.getString("description"));
				newsList.add(newsBean);
			}
			return newsList;
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}

	private void getdata() {
		if (init) {
			init = false;
			// 从服务器加载数据
			getservciesData();
			
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		// 每次切换fragment时调用的方法
		if (isVisibleToUser) {
			getdata();
		}
	}
}
