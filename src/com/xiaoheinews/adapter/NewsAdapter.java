package com.xiaoheinews.adapter;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
 

 

import com.xiaoheinews.activity.R;
import com.xiaoheinews.bean.NewsBean;
import com.xiaoheinews.utill.LruImageCache;
import com.xiaoheinews.utill.StringUtil;
import com.xiaoheinews.utill.VolleyUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {

	private List<NewsBean> mList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	 
	
  
	
	public NewsAdapter(Context context, List<NewsBean> data) {
		mList = data;
		this.imageLoader=new ImageLoader(VolleyUtil.getQueue(context), new LruImageCache());
		mInflater = LayoutInflater.from(context);
		

		
	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		ImageContainer container;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item, null);
			viewHolder.ivIcon = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			viewHolder.tvTitle = (TextView) convertView
					.findViewById(R.id.iv_title);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.iv_content);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.ivIcon.setImageResource(R.drawable.ic_launcher);
		String url = mList.get(position).getIconUrl();
		viewHolder.ivIcon.setTag(url);
//		
//		new ImageLoder().showImageByThread(viewHolder.ivIcon,
//				url);
		
//		new MyImageLoder().showimageByAsyncTask(viewHolder.ivIcon, url);
		
		 setImage(viewHolder.ivIcon, url);
		viewHolder.tvTitle.setText(mList.get(position).getTitle());
		viewHolder.tvContent.setText(mList.get(position).getContent());

		return convertView;
	}
	
	void setImage(ImageView imageView, String imageUrl) {
		
		ImageContainer container;
		
		try {
			//如果当前ImageView上存在请求，先取消
			if(imageView.getTag()!=null)
			{
				container=(ImageContainer)imageView.getTag();
				container.cancelRequest();
			}
		} catch (Exception e) {

		}
		
		ImageListener listener=ImageLoader.getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ic_launcher);
		
		container= imageLoader.get(StringUtil.preUrl(imageUrl),listener);
		
		//在ImageView上存储当前请求的Container，用于取消请求
		imageView.setTag(container);
		
	}

	class ViewHolder {
		public TextView tvTitle;
		public TextView tvContent;
		public ImageView ivIcon;
	}

}
