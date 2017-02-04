package org.recoapp.fragment;

import org.recoapp.activity.R;
import org.recoapp.adapter.ImagecodeListFragmentAdapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class ImagecodeMainFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_imagecode_main, container, false);
		
		final ImagecodeListFragmentAdapter adapter = new ImagecodeListFragmentAdapter(getChildFragmentManager());

    	final ViewPager pager = (ViewPager)rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        
        PageIndicator indicator = (CirclePageIndicator)rootView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        
        indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
		    	Log.d("imageCode", "fragment : " + arg0 + " / " + pager.getCurrentItem() + " / " + pager.getChildCount());
		    	((TextView)(rootView.findViewById(R.id.tabName))).setText(adapter.getPageTitle(arg0));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        TextView tabName = (TextView)rootView.findViewById(R.id.tabName);
    	tabName.setTextSize(1, 20);
    	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Bold.ttf");
    	tabName.setTypeface(tf);
    	        
		return rootView;
	}

}
