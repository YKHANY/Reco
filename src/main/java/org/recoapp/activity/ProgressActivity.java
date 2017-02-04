package org.recoapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.recoapp.util.CodeCreateDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.RecoHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ProgressActivity extends Activity {
	private LinearLayout linear;
	private int[] image = {R.drawable.progress_01_01, R.drawable.progress_01_02, R.drawable.progress_01_03, R.drawable.progress_01_04};
	private int num;
	private CodeCreateDTO codeCreateDTO;
	private Thread imageThread;
    private Handler imageThreadHandler;
	private boolean imageThreadOnOff;
	
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		codeCreateDTO = (CodeCreateDTO)getIntent().getSerializableExtra("codeCreateDTO");
		Log.d("imagecode", codeCreateDTO.toString());
		
		setContentView(R.layout.activity_progress);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
        TextView progress = (TextView)findViewById(R.id.progress);
        TextView progressText = (TextView)findViewById(R.id.progressText);
    	Typeface tf = Typeface.createFromAsset(getAssets(), "Gotham-Bold.ttf");
    	Typeface tf2 = Typeface.createFromAsset(getAssets(), "Gotham-Book.ttf");
    	progress.setTypeface(tf);
    	progressText.setTypeface(tf2);

    	num = 0;
    	linear = (LinearLayout)findViewById(R.id.progressImage);
    	imageThreadHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				linear.setBackgroundResource(image[num++]);
				Log.d("imagecode", num + "");
				if (num > 3) {
					num = 0;
				}
			}
        };
        
        imageThreadOnOff = true;
        imageThread = new Thread(new Runnable() {
			public void run() {
				while(imageThreadOnOff) {
					imageThreadHandler.sendEmptyMessage(0);
	            	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
        
        imageThread.start();
        imagecode_insert();
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		this.imageThreadOnOff = false;
	}

	private void imagecode_insert() {
    	RequestParams params = new RequestParams();
		params.add("imagecode_name", codeCreateDTO.getCodeName());
		params.add("imagecode_url", codeCreateDTO.getSelectedCodeUrl()+"");
        RecoHttpClient.getInstance().post(getApplicationContext(), "imagecode/insert", params, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
				RequestParams params2 = new RequestParams();
				RecoHttpClient.getInstance().post(getApplicationContext(), "imagecode/search_member", params2, new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, Header[] arg1, byte[] response) {
						try {
							String input = new String(response, 0, response.length, "UTF-8");
							JSONArray arr = new JSONArray(input);
							final ArrayList<ImagecodeDTO> imagecodeDTOList = new ArrayList<ImagecodeDTO>();
							JSONObject obj = arr.getJSONObject(0);
							file_upload(obj.getString("imagecode_code"));
						} catch (Exception e) {
							Log.d("imagecode", e.getMessage());
							e.printStackTrace();
						}
					}
					public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
						Toast.makeText(getApplicationContext(), "�ڵ� ��ȣ ��ȸ ���� ����", Toast.LENGTH_SHORT).show();
					}
				});
			}
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getApplicationContext(), "�ڵ� ���� ���� ����", Toast.LENGTH_SHORT).show();
			}
        });
    }
    
    private void file_upload(String imagecode) {
		ArrayList<String> filePath = codeCreateDTO.getFilePath();
		ArrayList<File> listFile = new ArrayList<File>();
		for (int i=0; i<filePath.size(); i++) {
			listFile.add(new File(filePath.get(i)));
		}
		RequestParams params = new RequestParams();
		try {
			params.put("file1", listFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		params.put("imagecode_code", imagecode);
        RecoHttpClient.getInstance().post(getApplicationContext(), "file/upload", params, new AsyncHttpResponseHandler() {
        	public void onProgress(int bytesWritten, int totalSize) {
				int per = (bytesWritten*100)/totalSize;
				TextView text = (TextView)findViewById(R.id.progress);
				text.setText(per+"%");
			}
			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
				try {
					Log.d("imagecode", "���� ���ε� �Ϸ�");
					Toast.makeText(getApplicationContext(), "��� �Ϸ�", Toast.LENGTH_SHORT).show();
					imageThreadOnOff = false;
					imagecode_search(new Intent());
				} catch (Exception e) {
					Log.d("imagecode", e.getMessage());
					e.printStackTrace();
				}
			}
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getApplicationContext(), "���� ���ε� ���� ����", Toast.LENGTH_SHORT).show();
			}
        });
    }
    
    private void imagecode_search (final Intent intent) {
		final SharedPreferences prefs = getSharedPreferences("imagecode", Context.MODE_PRIVATE);
		final SharedPreferences.Editor ed = prefs.edit();
		final HashSet<String> values = (HashSet<String>) prefs.getStringSet("favoriteList", new HashSet<String>());
		Iterator<String> iter = values.iterator();
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
					Log.d("imagecode", "imagecodeDTOList : " + imagecodeDTOList.size());
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
								Log.d("imagecode", "imagecodeDTOTagList : " + imagecodeDTOTagList.size());
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
								Log.d("imagecode", "favoriteDTOList : " + favoriteDTOList.size());
								intent.putExtra("favoriteDTOList", favoriteDTOList);
								setResult(1032, intent);
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
