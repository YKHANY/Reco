package org.recoapp.activity;

import android.app.ActionBar;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ssu.reco2.ScanMainActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.recoapp.fragment.ImagecodeListFragment;
import org.recoapp.fragment.ImagecodeMainFragment;
import org.recoapp.util.FileDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.ImagecodeDetailDAO;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

	DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;

    ImageView lvDrawerList;

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

        ImagecodeMainFragment fragment = new ImagecodeMainFragment();
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
        final LinearLayout action_item_btn_03_layout = (LinearLayout)actionBar.getCustomView().findViewById(R.id.action_item_btn_03_layout);
        action_item_btn_03_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent photoSelectView = new Intent(getApplicationContext(), PhotoSelectActivity.class);
				startActivityForResult(photoSelectView, 1030);
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

	public void onImagecodeDetail(View view) {
		ImagecodeDTO imagecodeDTO = (ImagecodeDTO)view.getTag();
		Log.d("imagecode", imagecodeDTO.toString());

		final Intent codeDetailView = new Intent(getApplicationContext(), CodeDetailActivity.class);
		codeDetailView.putExtra("imagecodeDTO", imagecodeDTO);


        // get access to ImageCodeDetail
        ImagecodeDetailDAO imagecodeDetail = new ImagecodeDetailDAO();
        JSONArray jsonArray = imagecodeDetail.selectImageCodeDetail(getApplicationContext(),imagecodeDTO);

        if(jsonArray != null){
            try {

                ArrayList<FileDTO> fileDTOImageList = new ArrayList<FileDTO>();
                ArrayList<FileDTO> fileDTOVideoList = new ArrayList<FileDTO>();
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if (obj.getString("file_type").equals("image")) {
                        fileDTOImageList.add(new FileDTO(obj.getString("file_code"), obj.getString("member_code"), obj.getString("imagecode_code"),
                                obj.getString("file_latest_modifydate"), obj.getString("file_type"), obj.getString("file_name"),
                                obj.getString("file_url"), obj.getString("file_thumb_url")));
                    } else {
                        fileDTOVideoList.add(new FileDTO(obj.getString("file_code"), obj.getString("member_code"), obj.getString("imagecode_code"),
                                obj.getString("file_latest_modifydate"), obj.getString("file_type"), obj.getString("file_name"),
                                obj.getString("file_url"), obj.getString("file_thumb_url")));
                    }
                }
                Log.d("imagecode", "fileDTOImageList " + fileDTOImageList.size()+"");
                Log.d("imagecode", "fileDTOVideoList " + fileDTOVideoList.size()+"");
                codeDetailView.putExtra("fileDTOImageList", fileDTOImageList);
                codeDetailView.putExtra("fileDTOVideoList", fileDTOVideoList);

                startActivityForResult(codeDetailView, 1029);

            } catch (Exception e) {
                Log.d("imagecode", e.getMessage());
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
        }
	}

	public void onSlidingClick(View view) {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == 1029) {
			if (intent != null) {
				ImagecodeListFragment.getInstance(0).refreshList(intent);
				ImagecodeListFragment.getInstance(1).refreshList(intent);
			}
		} else if (resultCode == 1030) {
			if (intent != null) {
				ImagecodeListFragment.getInstance(0).refreshList(intent);
				ImagecodeListFragment.getInstance(1).refreshList(intent);
			}
		}
	}

}
