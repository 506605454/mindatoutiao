package com.xiaoheinews.utill;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class MyImageLoder {
	
	private ImageView mImageView;
	private String mUrl;
//	private Handler handler =new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			if (mImageView.getTag().equals(mUrl)) {
//				mImageView.setImageBitmap((Bitmap) msg.obj);
//			}
//			
//		}
//	       
//	};
//	
//	public void showImageByThread(ImageView imageView,final String url) {
//		mImageView =imageView;
//		mUrl = url;
//		new Thread(){
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				super.run();
//				Bitmap bitmap =getBitmapFromUrl(url);
//				Message message =Message.obtain();
//				message.obj= bitmap;
//				handler.sendMessage(message);
//			}
//		}.start();
//	}

	
	public Bitmap getBitmapFromUrl(String urlString) {
		Bitmap bitmap;
		InputStream is = null;
			try {
				URL url = new URL(urlString);
				HttpURLConnection   connection =  (HttpURLConnection) url.openConnection();
				is =new BufferedInputStream(connection.getInputStream());
				bitmap = BitmapFactory.decodeStream(is);
				connection.disconnect();
				 
				return bitmap;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return null;
		
	}
	
	public void showimageByAsyncTask(ImageView imageView,String url) {
		
		new NewsAsyncTask(imageView,url).execute(url);
		
		
	}
	
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {

		
		private ImageView mImageView;
		private String mUrl;
		public NewsAsyncTask(ImageView imageView,String url) {
			mImageView = imageView;
			mUrl = url;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			 
			return getBitmapFromUrl(params[0]);
		}
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			 
			super.onPostExecute(bitmap);
			if (mImageView.getTag().equals(mUrl)) {
				
			}
			mImageView.setImageBitmap(bitmap);
		}
	}
	
}
