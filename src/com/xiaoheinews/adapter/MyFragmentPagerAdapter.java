package com.xiaoheinews.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments; 
	private FragmentManager fm;
	
	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
	}

	  public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
	        super(fm);
			this.fm = fm;
	        this.fragments = fragments;
	    }
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	@Override
	public int getItemPosition(Object object) {
		 
		//return super.getItemPosition(object);
		return POSITION_NONE;
	}
	
	
	
	public void setFragments(ArrayList<Fragment> fragments) {
		if(this.fragments != null){
			FragmentTransaction ft = fm.beginTransaction();
			for(Fragment f:this.fragments){
				ft.remove(f);
			}
			ft.commit();
			ft=null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		  Object obj = super.instantiateItem(container, position);
		    return obj;
	}

}
