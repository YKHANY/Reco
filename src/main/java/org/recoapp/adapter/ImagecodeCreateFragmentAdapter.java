package org.recoapp.adapter;

import org.recoapp.fragment.ImagecodeCreateFragment;
import org.recoapp.fragment.ImagecodeListFragment;

import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ImagecodeCreateFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private int mCount = 4;

    public ImagecodeCreateFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	if (position == 0) {
    		return ImagecodeCreateFragment.getInstance();
    	} else {
    		return ImagecodeCreateFragment.newInstance();
    	}
    }

	@Override
	public int getCount() {
        return mCount;
	}

    @Override
    public CharSequence getPageTitle(int position) {
    	return "1";
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}
