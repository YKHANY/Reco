package org.recoapp.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ssu.reco2.ScanMainActivity;

import org.recoapp.fragment.ImagecodeDetailFragment;
import org.recoapp.util.FileDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.ImagecodeDetailDAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CodeDetailActivity extends FragmentActivity {

	DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    ImageView lvDrawerList;
    ArrayAdapter<String> adtDrawerList;
    String[] menuItems = new String[]{"TextFragment", "ImageFragment"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		
		// Navigation drawer : menu lists
        lvDrawerList = (ImageView) findViewById(R.id.lv_activity_main);

        // Navigation drawer : ActionBar Toggle
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, 0, R.drawable.action_item_01, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);

        actionBarSettings();

        ImagecodeDetailFragment fragment = new ImagecodeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
		
	}


    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	return super.onCreateOptionsMenu(menu);
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(dtToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public void actionBarSettings() {
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_main);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        ImageView action_item_btn_01 = (ImageView)actionBar.getCustomView().findViewById(R.id.action_item_btn_01);
        final LinearLayout action_item_btn_01_layout = (LinearLayout)actionBar.getCustomView().findViewById(R.id.action_item_btn_01_layout);
        action_item_btn_01_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (dlDrawer.isDrawerOpen(lvDrawerList)) {
		        	dlDrawer.closeDrawer(lvDrawerList);
				} else {
		        	dlDrawer.openDrawer(lvDrawerList);
				}
			}
		});
        action_item_btn_01_layout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					action_item_btn_01_layout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eaeaea")));
				if (event.getAction() == MotionEvent.ACTION_UP)
					action_item_btn_01_layout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
				return false;
			}
		});

        ImageView action_item_btn_02 = (ImageView)actionBar.getCustomView().findViewById(R.id.action_item_btn_02);
        final LinearLayout action_item_btn_02_layout = (LinearLayout)actionBar.getCustomView().findViewById(R.id.action_item_btn_02_layout);
        action_item_btn_02_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Intent scanMainView = new Intent(getApplicationContext(), ScanMainActivity.class);
				startActivity(scanMainView);
				finish();
			}
		});
        action_item_btn_02_layout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					action_item_btn_02_layout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eaeaea")));
				if (event.getAction() == MotionEvent.ACTION_UP)
					action_item_btn_02_layout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
				return false;
			}
		});

        ImageView action_item_btn_03 = (ImageView)actionBar.getCustomView().findViewById(R.id.action_item_btn_03);
        action_item_btn_03.setImageResource(R.drawable.action_item_04);
        final LinearLayout action_item_btn_03_layout = (LinearLayout)actionBar.getCustomView().findViewById(R.id.action_item_btn_03_layout);
        action_item_btn_03_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
	        	Log.d("imageCode", "imagecode_print");
			}
		});
        action_item_btn_03_layout.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN)
					action_item_btn_03_layout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eaeaea")));
				if (event.getAction() == MotionEvent.ACTION_UP)
					action_item_btn_03_layout.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
				return false;
			}
		});
	}
	
	public void onSlidingClick(View view) {
	}

	@Override
	public void onBackPressed() {
		imagecode_search(new Intent());
	}
	
	public void onMediaClick(View view) {
		FileDTO fileDTO = (FileDTO)view.getTag();
		Log.d("imagecode", fileDTO.toString());
		Intent intent = new Intent(getApplicationContext(), CodeDetailViewerActivity.class);
		intent.putExtra("fileDTO", fileDTO);
		startActivity(intent);
	}
	
	private void imagecode_search (final Intent intent) {
		final SharedPreferences prefs = getSharedPreferences("imagecode", Context.MODE_PRIVATE);
		final SharedPreferences.Editor ed = prefs.edit();
		final HashSet<String> values = (HashSet<String>) prefs.getStringSet("favoriteList", new HashSet<String>());
		Iterator<String> iter = values.iterator();

		ImagecodeDetailDAO imagecodeDetailDAO = new ImagecodeDetailDAO();
		ArrayList<ImagecodeDTO> imagecodeDTOList = imagecodeDetailDAO.selectImageCodeAll(getApplicationContext());

		if(imagecodeDTOList != null)
		{
			intent.putExtra("imagecodeDTOList", imagecodeDTOList);
			ArrayList<ImagecodeDTO> imagecodeDTOTagList = imagecodeDetailDAO.selectImageCodeTag(getApplicationContext());
			if(imagecodeDTOTagList != null){
				intent.putExtra("imagecodeDTOTagList", imagecodeDTOTagList);

				ArrayList<ImagecodeDTO> favoriteDTOList = new ArrayList<ImagecodeDTO>();
				for (int i=0; i<imagecodeDTOList.size(); i++) {
					while (iter.hasNext()) {
						String temp = iter.next();
						if (imagecodeDTOList.get(i).getImagecode_code().equals(temp)) {
							favoriteDTOList.add(imagecodeDTOList.get(i));
						}
					}
					iter = values.iterator();
				}
				Log.d("imagecode", favoriteDTOList.size()+"");
				intent.putExtra("favoriteDTOList", favoriteDTOList);
				setResult(1029, intent);
				finish();
			}

		}
	}
}
