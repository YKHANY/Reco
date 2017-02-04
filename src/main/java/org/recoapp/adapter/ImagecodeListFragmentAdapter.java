package org.recoapp.adapter;

import org.recoapp.fragment.ImagecodeListFragment;

import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ImagecodeListFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
	protected static final String[] CONTENT = new String[] { "ALL CODES", "FAVORITES", "SHARED CODES" };
    
    private int mCount = CONTENT.length;

    public ImagecodeListFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    	Log.d("imageCode", "adapter : " + position);
        return ImagecodeListFragment.getInstance(position);
    }

	@Override
	public int getCount() {
        return mCount;
	}

    @Override
    public CharSequence getPageTitle(int position) {
    	return ImagecodeListFragmentAdapter.CONTENT[position % CONTENT.length];
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