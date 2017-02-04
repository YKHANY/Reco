package com.ssu.reco2;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.recoapp.activity.CodeDetailActivity;
import org.recoapp.activity.R;
import org.recoapp.util.FileDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.RecoHttpClient;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ScanMainActivity extends Activity {

	private static final int CAMERA_REQUEST = 1888;
	private static final String TAG = "HelloCamera";
	private static final int IN_SAMPLE_SIZE = 8;
	private Preview mPreView;
	private Button captureBtn;
	private ImageView imageView;
	private TextView textView;
	LayoutInflater controlInflater = null;
	public static String cam="cam1_";
	public static int cam1Num;
	public static int cam2Num;
	public static int cam3Num;
	public static int cam4Num;
	public static int cam5Num;
	public static int cam6Num;
	public static int cam7Num;
	public static int cam8Num;
	public static int cam9Num;
	public static boolean bin;
	public static boolean extData;
	public static boolean backP;
	static{
		cam1Num = 1;
		cam2Num = 1;
		cam3Num = 1;
		cam4Num = 1;
		cam5Num = 1;
		cam6Num = 1;
		cam7Num = 1;
		cam8Num = 1;
		cam9Num = 1;
		bin = false;
		extData = false;
		backP = true;
	}
/*	AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			// TODO Auto-generated method stub
			if (success) {
				mCamera.cancelAutoFocus();
			}

		}
	};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		Log.i("Reco", "onCreate");
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		mPreView = new Preview(this,displayMetrics);
		setContentView(mPreView);

		// camera UI
		controlInflater = LayoutInflater.from(getBaseContext());
		View viewControl = controlInflater.inflate(R.layout.control, null);
		LayoutParams layoutParamsControl = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.addContentView(viewControl, layoutParamsControl);
		textView = (TextView)findViewById(R.id.textView);
		mPreView.setTextView(textView);
		ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView1);
		mPreView.setScrollView(scrollView);
		
		// SurfaceView surface = (SurfaceView) findViewById(R.id.surface_view);

		// SurfaceHolder holder = surface.getHolder();

		// holder.addCallback(mSurfaceListener);

		// holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		imageView = (ImageView) findViewById(R.id.outineView);
 		captureBtn = (Button) findViewById(R.id.takepicture);
 		mPreView.setCapture(imageView, captureBtn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if(id == R.id.cam1){
			cam = "cam1_";
			Toast.makeText(getApplicationContext(),"cam1_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam2){
			cam = "cam2_";
			Toast.makeText(getApplicationContext(),"cam2_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam3){
			cam = "cam3_";
			Toast.makeText(getApplicationContext(),"cam3_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam4){
			cam = "cam4_";
			Toast.makeText(getApplicationContext(),"cam4_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam5){
			cam = "cam5_";
			Toast.makeText(getApplicationContext(),"cam5_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam6){
			cam = "cam6_";
			Toast.makeText(getApplicationContext(),"cam6_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam7){
			cam = "cam7_";
			Toast.makeText(getApplicationContext(),"cam7_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam8){
			cam = "cam8_";
			Toast.makeText(getApplicationContext(),"cam8_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.cam9){
			cam = "cam9_";
			Toast.makeText(getApplicationContext(),"cam9_" ,Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.bin){
			bin = true;
			backP = false;
			extData = false;
			Toast.makeText(getApplicationContext(), "bin = true; backP = false;	extData = false;", Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.bp){
			backP = true;
			bin = false;
			extData = false;
			Toast.makeText(getApplicationContext(), "backP = true; bin = false; extData = false;", Toast.LENGTH_SHORT).show();
			return true;
		}
		if(id == R.id.ext){
			extData = true;
			backP = false;
			bin = false;
			Toast.makeText(getApplicationContext(), "extData = true; backP = false; bin = false;", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		cam1Num = 1;
		cam2Num = 1;
		cam3Num = 1;
		cam4Num = 1;
		cam5Num = 1;
		cam6Num = 1;
		cam7Num = 1;
		cam8Num = 1;
		cam9Num = 1;
	}
}
