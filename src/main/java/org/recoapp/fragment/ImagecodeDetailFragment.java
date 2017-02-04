package org.recoapp.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.http.Header;
import org.json.JSONArray;
import org.recoapp.activity.R;
import org.recoapp.util.FileDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.RecoHttpClient;
import org.recoapp.util.SquareImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ImagecodeDetailFragment extends Fragment implements SurfaceHolder.Callback {
	SurfaceView mPreview;
	SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	String videoPath;
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_imagecode_detail, container, false);
		
        final ImagecodeDTO imagecodeDTO = (ImagecodeDTO)getActivity().getIntent().getSerializableExtra("imagecodeDTO");
        ArrayList<FileDTO> fileDTOVideoList = (ArrayList<FileDTO>)getActivity().getIntent().getSerializableExtra("fileDTOVideoList");
        ArrayList<FileDTO> fileDTOImageList = (ArrayList<FileDTO>)getActivity().getIntent().getSerializableExtra("fileDTOImageList");
		
        ImageView imageCode = (ImageView)rootView.findViewById(R.id.imageCode);
        imageCode.setImageResource(imagecodeId[imagecodeDTO.getImagecode_url()]);
        imageCode.setVisibility(View.VISIBLE);
        
        TextView imageCodeName = (TextView)rootView.findViewById(R.id.imageCodeName);
    	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Bold.ttf");
    	imageCodeName.setTypeface(tf);
    	imageCodeName.setText(imagecodeDTO.getImagecode_name());

        TextView imageCodeInfo = (TextView)rootView.findViewById(R.id.imageCodeInfo);
    	Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
        imageCodeInfo.setTypeface(tf2);
        StringBuilder infoText = new StringBuilder();
        infoText.append(imagecodeDTO.getImagecode_latest_modifydate().substring(0, 4));
        infoText.append(".");
        infoText.append(imagecodeDTO.getImagecode_latest_modifydate().substring(5, 7));
        infoText.append(".");
        infoText.append(imagecodeDTO.getImagecode_latest_modifydate().substring(8, 10));
        infoText.append(" / ");
        infoText.append((fileDTOVideoList.size()+fileDTOImageList.size())+"");
        infoText.append(" Images");
        imageCodeInfo.setText(infoText);

		final SharedPreferences prefs = rootView.getContext().getSharedPreferences("imagecode", Context.MODE_PRIVATE);
		final SharedPreferences.Editor ed = prefs.edit();

		ed.putBoolean("taggedOnOff", false);
		ed.commit();

		final HashSet<String> values = (HashSet<String>) prefs.getStringSet("favoriteList", new HashSet<String>());
        final ImageView favorite = (ImageView)rootView.findViewById(R.id.favorite);
        Iterator<String> iter = values.iterator();
		while (iter.hasNext()) {
			String temp = iter.next();
			if (imagecodeDTO.getImagecode_code().equals(temp)) {
				ed.putBoolean("favoriteOnOff", true);
				favorite.setImageResource(R.drawable.code_details_favorite_02);
				break;
			} else {
				ed.putBoolean("favoriteOnOff", false);
				favorite.setImageResource(R.drawable.code_details_favorite_01);
			}
			ed.commit();
		}
        
        favorite.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (prefs.getBoolean("favoriteOnOff", false)) {
					ed.putBoolean("favoriteOnOff", false);
					favorite.setImageResource(R.drawable.code_details_favorite_01);
					values.remove(imagecodeDTO.getImagecode_code());
				} else {
					ed.putBoolean("favoriteOnOff", true);
					favorite.setImageResource(R.drawable.code_details_favorite_02);
					values.add(imagecodeDTO.getImagecode_code());
				}
				ed.putStringSet("favoriteList", values);
				ed.commit();
			}
		});
        
        final ImageView tagged = (ImageView)rootView.findViewById(R.id.tagged);
        final ImageView taggedList = (ImageView)rootView.findViewById(R.id.taggedList);
        tagged.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (prefs.getBoolean("taggedOnOff", false)) {
					ed.putBoolean("taggedOnOff", false);
					tagged.setImageResource(R.drawable.code_details_tagged_01);
					taggedList.setVisibility(View.INVISIBLE);
				} else {
					ed.putBoolean("taggedOnOff", true);
					tagged.setImageResource(R.drawable.code_details_tagged_02);
					taggedList.setVisibility(View.VISIBLE);
				}
				ed.commit();
			}
		});
        

        ScrollView mediaListScroll = (ScrollView)rootView.findViewById(R.id.mediaListScroll);

        LinearLayout mainLayout = new LinearLayout(getActivity());
        mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setBackgroundColor(Color.WHITE);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        

        if(fileDTOVideoList.size()>0) {
        	videoPath = fileDTOVideoList.get(0).getFile_name();
        	View videoListView = inflater.inflate(R.layout.media_list_1, container, false);
        	videoListView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
		        	Intent i = new Intent(Intent.ACTION_VIEW);
		            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Movies/" + videoPath);
		            i.setDataAndType(uri, "video/*");
		            startActivity(i);
				}
			});
        	mPreview = (SurfaceView)videoListView.findViewById(R.id.media1);
        	mHolder = mPreview.getHolder();
        	mHolder.addCallback(this);
        	mainLayout.addView(videoListView);
        }
    	

		int size = fileDTOImageList.size()/3;
		int size2 = fileDTOImageList.size()%3;
		if (size2 > 0) {
			size++;
		}
    	for (int i=0; i<size; i++) {
        	final View imageListView = inflater.inflate(R.layout.media_list_3, container, false);
    		for (int j=0; j<3; j++) {
    			if ((j + i*3) > fileDTOImageList.size()-1)
    				break;
    			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
    			final FileDTO temp = fileDTOImageList.get(j + i*3);
            	if (j%3 == 0) {
            		RequestParams params = new RequestParams();
            		params.add("file_code", temp.getFile_code());
            		RecoHttpClient.getInstance().post(getActivity(), "file/download_thumb", params, new AsyncHttpResponseHandler() {
            			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
            				try {
        						Bitmap thumb = BitmapFactory.decodeByteArray(response, 0, response.length);
        						ImageView media = (ImageView)imageListView.findViewById(R.id.media1);
        						media.setImageBitmap(thumb);
        						media.setTag(temp);
            				} catch (Exception e) {
            					Log.d("imagecode", e.getMessage());
            					e.printStackTrace();
            				}
            			}
            			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            				Toast.makeText(getActivity(), "���� ����", Toast.LENGTH_SHORT).show();
            			}
            		});
            	} else if (j%3 == 1) {
            		RequestParams params = new RequestParams();
            		params.add("file_code", temp.getFile_code());
            		RecoHttpClient.getInstance().post(getActivity(), "file/download_thumb", params, new AsyncHttpResponseHandler() {
            			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
            				try {
        						Bitmap thumb = BitmapFactory.decodeByteArray(response, 0, response.length);
        						ImageView media = (ImageView)imageListView.findViewById(R.id.media2);
        						media.setImageBitmap(thumb);
        						media.setTag(temp);
            				} catch (Exception e) {
            					Log.d("imagecode", e.getMessage());
            					e.printStackTrace();
            				}
            			}
            			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            				Toast.makeText(getActivity(), "���� ����", Toast.LENGTH_SHORT).show();
            			}
            		});
            	} else {
            		RequestParams params = new RequestParams();
            		params.add("file_code", temp.getFile_code());
            		RecoHttpClient.getInstance().post(getActivity(), "file/download_thumb", params, new AsyncHttpResponseHandler() {
            			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
            				try {
        						Bitmap thumb = BitmapFactory.decodeByteArray(response, 0, response.length);
        						ImageView media = (ImageView)imageListView.findViewById(R.id.media3);
        						media.setImageBitmap(thumb);
        						media.setTag(temp);
            				} catch (Exception e) {
            					Log.d("imagecode", e.getMessage());
            					e.printStackTrace();
            				}
            			}
            			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            				Toast.makeText(getActivity(), "���� ����", Toast.LENGTH_SHORT).show();
            			}
            		});
        		}
        	}
            mainLayout.addView(imageListView);
        }
    	
        /*View mediaListView2 = inflater.inflate(R.layout.media_list_3, container, false);
        View mediaListView3 = inflater.inflate(R.layout.media_list_3, container, false);
        ((SquareImageView)(mediaListView3.findViewById(R.id.media1))).setImageResource(R.drawable.thumb_media_05);
        ((SquareImageView)(mediaListView3.findViewById(R.id.media2))).setImageResource(R.drawable.thumb_media_06);
        ((SquareImageView)(mediaListView3.findViewById(R.id.media3))).setImageResource(R.drawable.thumb_media_07);
        
        mainLayout.addView(mediaListView);
        mainLayout.addView(mediaListView2);
        mainLayout.addView(mediaListView3);*/

        mediaListScroll.addView(mainLayout);
        
		return rootView;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			if (mPlayer == null) {
				mPlayer = new MediaPlayer();
			} else {
				mPlayer.reset();
			}
			String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			mPlayer.setDataSource(sd + "/Movies/" + videoPath);
			mPlayer.setDisplay(holder);
			mPlayer.prepare();
			mPlayer.start();
		} catch (Exception e) {
			Toast.makeText(getActivity().getApplicationContext(), "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

}
