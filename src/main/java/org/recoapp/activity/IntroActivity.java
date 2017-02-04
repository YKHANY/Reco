package org.recoapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.RecoHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class IntroActivity extends Activity {
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		final SharedPreferences prefs = getSharedPreferences("imagecode", Context.MODE_PRIVATE);
		final SharedPreferences.Editor ed = prefs.edit();
		if (!(prefs.getBoolean("firstlaunch", false))) {
			HashSet<String> values = new HashSet<String>();
			values.add("imagecode28");
			values.add("imagecode26");
			values.add("imagecode20");
			values.add("imagecode19");
			values.add("imagecode18");
			values.add("imagecode17");
			values.add("imagecode16");
			values.add("imagecode15");
			values.add("imagecode14");
			values.add("imagecode13");
			values.add("imagecode12");
			values.add("imagecode11");
			ed.putStringSet("favoriteList", values);
			ed.putBoolean("firstlaunch", true);
			ed.commit();


			File file = getApplicationContext().getExternalFilesDir(null);
			String path = file.getAbsolutePath() + "/neural.dat";
			
			AssetManager assetManager = this.getResources().getAssets();
			File outfile = new File(path);
			 
			InputStream is = null;
			FileOutputStream fo = null;
			long filesize = 0;
			try {
				is = assetManager.open("neural.dat", AssetManager.ACCESS_BUFFER);
				filesize = is.available(); 
				if (outfile.length() <= 0) {
					byte[] tempdata = new byte[(int) filesize];
					is.read(tempdata);
					is.close();
					outfile.createNewFile();
					fo = new FileOutputStream(outfile);
					fo.write(tempdata);
					fo.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		beforeIntro();

		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		Log.d("start","introactivity");

	}

	private void beforeIntro() {
		getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				RequestParams params = new RequestParams();
				params.put("member_id", "uer@naver.com");
				params.put("member_password", "password");
				RecoHttpClient.getInstance().post(getApplicationContext(), "member/login", params, new AsyncHttpResponseHandler() {
					
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] response) {
						try {
							String input = new String(response, 0, response.length, "UTF-8");
							JSONObject obj = new JSONObject(input);
							if (obj.getBoolean("isSuccess")) {
								//Toast.makeText(getApplicationContext(), "�α��� ����", Toast.LENGTH_SHORT).show();
								imagecode_search(intent);
							} else {
								Toast.makeText(getApplicationContext(), "�α��� ����", Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
						Toast.makeText(getApplicationContext(), "���� ����", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}, 2000);
	}
	private void imagecode_search (final Intent intent) {
		final SharedPreferences prefs = getSharedPreferences("imagecode", Context.MODE_PRIVATE);
		final SharedPreferences.Editor ed = prefs.edit();
		final HashSet<String> values = (HashSet<String>) prefs.getStringSet("favoriteList", new HashSet<String>());
		
		RequestParams params2 = new RequestParams();
		RecoHttpClient.getInstance().post(getApplicationContext(), "imagecode/search_all", params2, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
				try {
					String input = new String(response, 0, response.length, "UTF-8");
					JSONArray arr = new JSONArray(input);
					final ArrayList<ImagecodeDTO> imagecodeDTOList = new ArrayList<ImagecodeDTO>();
					for (int i=0; i<arr.length(); i++) {
						JSONObject obj = arr.getJSONObject(i);
						imagecodeDTOList.add(new ImagecodeDTO(obj.getString("imagecode_code"), obj.getString("member_code"),
								obj.getString("imagecode_latest_modifydate"), obj.getString("imagecode_name"), obj.getString("imagecode_url")));
					}
					Log.d("imagecode", imagecodeDTOList.size()+"");
					intent.putExtra("imagecodeDTOList", imagecodeDTOList);
					
					RequestParams params2 = new RequestParams();
					RecoHttpClient.getInstance().post(getApplicationContext(), "imagecode/search_tag", params2, new AsyncHttpResponseHandler() {
						public void onSuccess(int arg0, Header[] arg1, byte[] response) {
							try {
								String input = new String(response, 0, response.length, "UTF-8");
								JSONArray arr = new JSONArray(input);
								ArrayList<ImagecodeDTO> imagecodeDTOTagList = new ArrayList<ImagecodeDTO>();
								for (int i=0; i<arr.length(); i++) {
									JSONObject obj = arr.getJSONObject(i);
									imagecodeDTOTagList.add(new ImagecodeDTO(obj.getString("imagecode_code"), obj.getString("member_code"),
											obj.getString("imagecode_latest_modifydate"), obj.getString("imagecode_name"), obj.getString("imagecode_url")));
								}
								Log.d("imagecode", imagecodeDTOTagList.size()+"");
								intent.putExtra("imagecodeDTOTagList", imagecodeDTOTagList);
								
								ArrayList<ImagecodeDTO> favoriteDTOList = new ArrayList<ImagecodeDTO>();
								Iterator<String> iter = values.iterator();
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

								startActivity(intent);
								overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
								finish();
							} catch (Exception e) {
								Log.d("imagecode", e.getMessage());
								e.printStackTrace();
							}
						}
						public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
							Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
						}
					});
				} catch (Exception e) {
					Log.d("imagecode", e.getMessage());
					e.printStackTrace();
				}
			}
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
