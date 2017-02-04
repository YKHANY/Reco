package org.recoapp.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import org.recoapp.adapter.ImagecodeCreateFragmentAdapter;
import org.recoapp.fragment.ImagecodeCreateFragment;
import org.recoapp.util.CodeCreateDTO;

public class CodeCreateActivity extends FragmentActivity {

	private DrawerLayout dlDrawer;
	private ActionBarDrawerToggle dtToggle;

	private ImageView lvDrawerList;
	private CodeCreateDTO codeCreateDTO;
    
	private boolean[] codeList = new boolean[21];
	private boolean[] colorList = new boolean[9];
    private int imagecodeId[] = {R.drawable.imagecode_01, R.drawable.imagecode_02, R.drawable.imagecode_03, R.drawable.imagecode_04, 
    		R.drawable.imagecode_05, R.drawable.imagecode_06, R.drawable.imagecode_07, R.drawable.imagecode_08, R.drawable.imagecode_09, 
    		R.drawable.imagecode_10, R.drawable.imagecode_11, R.drawable.imagecode_12, R.drawable.imagecode_13, R.drawable.imagecode_14,
    		R.drawable.imagecode_15, R.drawable.imagecode_16, R.drawable.imagecode_17, R.drawable.imagecode_18, R.drawable.imagecode_19,
    		R.drawable.imagecode_20, R.drawable.imagecode_21, R.drawable.imagecode_22, R.drawable.imagecode_23, R.drawable.imagecode_24,
    		R.drawable.imagecode_25, R.drawable.imagecode_26, R.drawable.imagecode_27, R.drawable.imagecode_28, R.drawable.imagecode_29,
    		R.drawable.imagecode_30, R.drawable.imagecode_31, R.drawable.imagecode_32, R.drawable.imagecode_33, R.drawable.imagecode_34,
    		R.drawable.imagecode_35, R.drawable.imagecode_36, R.drawable.imagecode_37, R.drawable.imagecode_38, R.drawable.imagecode_39,
    		R.drawable.imagecode_40, R.drawable.imagecode_41, R.drawable.imagecode_42, R.drawable.imagecode_43, R.drawable.imagecode_44,
    		R.drawable.imagecode_45, R.drawable.imagecode_46, R.drawable.imagecode_47, R.drawable.imagecode_48, R.drawable.imagecode_49,
    		R.drawable.imagecode_50, R.drawable.imagecode_51, R.drawable.imagecode_52, R.drawable.imagecode_53, R.drawable.imagecode_54,
    		R.drawable.imagecode_55, R.drawable.imagecode_56, R.drawable.imagecode_57, R.drawable.imagecode_58, R.drawable.imagecode_59,
    		R.drawable.imagecode_60, R.drawable.imagecode_61, R.drawable.imagecode_62, R.drawable.imagecode_63, R.drawable.imagecode_64,
    		R.drawable.imagecode_65
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		codeCreateDTO = (CodeCreateDTO)getIntent().getSerializableExtra("codeCreateDTO");
		setContentView(R.layout.activity_code_create);
		
		// Navigation drawer : menu lists
        lvDrawerList = (ImageView) findViewById(R.id.lv_activity_main);

        // Navigation drawer : ActionBar Toggle
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, 0, R.drawable.action_item_01, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);

        actionBarSettings();

		//  EditText settings for code_create.
        final EditText text2 = (EditText)findViewById(R.id.text2);
    	Typeface tf = Typeface.createFromAsset(getAssets(), "Gotham-Book.ttf");
        text2.setTypeface(tf);
        
        text2.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(text2.getWindowToken(), InputMethodManager.SHOW_FORCED);
				} else {
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(text2, InputMethodManager.SHOW_IMPLICIT);
				}
			}
		});

        TextView next = (TextView)findViewById(R.id.next);
    	Typeface tf2 = Typeface.createFromAsset(getAssets(), "Gotham-Bold.ttf");
        next.setTypeface(tf2);
        
        final ImagecodeCreateFragmentAdapter adapter = new ImagecodeCreateFragmentAdapter(getSupportFragmentManager());

    	final ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        
        PageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
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
        actionBar.setCustomView(R.layout.actionbar_code_create);
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

        TextView action_item_text_01 = (TextView)actionBar.getCustomView().findViewById(R.id.action_item_text_01);
    	Typeface tf = Typeface.createFromAsset(getAssets(), "Gotham-Bold.ttf");
    	action_item_text_01.setTypeface(tf);

        ImageView action_item_btn_02 = (ImageView)actionBar.getCustomView().findViewById(R.id.action_item_btn_02);
        final LinearLayout action_item_btn_02_layout = (LinearLayout)actionBar.getCustomView().findViewById(R.id.action_item_btn_02_layout);
        action_item_btn_02_layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
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
	}

	public void onCodeSelect(View view) {
		int tag = Integer.parseInt(view.getTag()+"");
		ImageView imagecode_big = (ImageView)findViewById(R.id.imagecode_big);
		View rootView = findViewById(R.id.colorLayout);
		
		if (!codeList[tag]) { // INVISIBLE
			for (int i=0; i<21; i++) {
				if (codeList[i]) {
					ImagecodeCreateFragment.getInstance().getView().findViewWithTag((i+21)+"").setVisibility(View.INVISIBLE);
					codeList[i] = false;
				}
			}
			codeList[tag] = true;
			ImagecodeCreateFragment.getInstance().getView().findViewWithTag((tag+21)+"").setVisibility(View.VISIBLE);
			codeCreateDTO.setSelectedCodeNum(tag);
			
			if (tag == 11) {
				imagecode_big.setImageResource(R.drawable.code_create_imagecode_big_01);
			} else if (tag == 18) {
				imagecode_big.setImageResource(R.drawable.code_create_imagecode_big_02);
			} else if (tag == 19) {
				imagecode_big.setImageResource(R.drawable.code_create_imagecode_big_03);
			}

			imagecode_big.setVisibility(View.VISIBLE);
			
			for (int i=0; i<9; i++) {
				rootView.findViewWithTag((i+9)+"").setVisibility(View.INVISIBLE);
				colorList[i] = false;
			}
		}
	}
	
	public void onColorSelect(View view) {
		int tag = Integer.parseInt(view.getTag()+"");
		ImageView imagecode_big = (ImageView)findViewById(R.id.imagecode_big);
		View rootView = findViewById(R.id.colorLayout);
		
		if (!colorList[tag]) { // INVISIBLE
			for (int i=0; i<9; i++) {
				if (colorList[i]) {
					rootView.findViewWithTag((i+9)+"").setVisibility(View.INVISIBLE);
					colorList[i] = false;
				}
			}
			colorList[tag] = true;
			rootView.findViewWithTag((tag+9)+"").setVisibility(View.VISIBLE);
			codeCreateDTO.setSelectedCodeColor(tag);
			
			if (tag == 4) {
				if (codeList[11]) {
					imagecode_big.setImageResource(R.drawable.code_create_imagecode_big_04);
					codeCreateDTO.setSelectedCodeUrl(25);
				}
			} else if (tag == 8) {
				if (codeList[18]) {
					imagecode_big.setImageResource(R.drawable.code_create_imagecode_big_05);
					codeCreateDTO.setSelectedCodeUrl(64);
				}
			} else if (tag == 2) {
				if (codeList[19]) {
					imagecode_big.setImageResource(R.drawable.code_create_imagecode_big_06);
					codeCreateDTO.setSelectedCodeUrl(0);
				}
			}
		}
	}
	public void onNext (View view) {
		EditText text = (EditText)findViewById(R.id.text2);
		if (!text.getText().toString().equals("")) {
			codeCreateDTO.setCodeName(text.getText().toString());
		}
		Intent progressView = new Intent(getApplicationContext(), ProgressActivity.class);
		progressView.putExtra("codeCreateDTO", codeCreateDTO);
		startActivityForResult(progressView, 1032);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void onSlidingClick(View view) {
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == 1032) {
			if (intent != null) {
				setResult(1031, intent);
				finish();
			}
		}
	}
}