package org.recoapp.activity;

import java.io.File;
import java.util.ArrayList;

import org.recoapp.fragment.ImagecodeListFragment;
import org.recoapp.util.CodeCreateDTO;
import org.recoapp.util.GalleryImageView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PhotoSelectActivity extends FragmentActivity {

   DrawerLayout dlDrawer;
   ActionBarDrawerToggle dtToggle;

   ImageView lvDrawerList;
   String path;
   CodeCreateDTO codeCreateDTO;
   ImageView imageViewArr[] = new ImageView[56];
   float layoutX[] = {200, 400, 600, 800, 1000, 1200, 1400};

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
      
      setContentView(R.layout.activity_photo_select);
      codeCreateDTO = new CodeCreateDTO();
      
      // Navigation drawer : menu lists
        lvDrawerList = (ImageView) findViewById(R.id.lv_activity_main);

        // Navigation drawer : ActionBar Toggle
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, 0, R.drawable.action_item_01, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);

        actionBarSettings();

		Typeface tf = Typeface.createFromAsset(getAssets(), "Gotham-Book.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(), "Gotham-Bold.ttf");
		TextView text = (TextView) findViewById(R.id.galleryListText);
		TextView text2 = (TextView) findViewById(R.id.galleryImageText);
		TextView text3 = (TextView) findViewById(R.id.create);
		text.setTypeface(tf);
		text2.setTypeface(tf);
		text3.setTypeface(tf2);

      path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gallery/";
        HorizontalScrollView galleryListScroll = (HorizontalScrollView)findViewById(R.id.galleryList);
        
      LinearLayout listLayout = new LinearLayout(getApplicationContext());
      listLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      listLayout.setGravity(Gravity.CENTER);
      listLayout.setBackgroundColor(Color.WHITE);
      listLayout.setOrientation(LinearLayout.HORIZONTAL);
        
        final LayoutInflater li = getLayoutInflater();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        for (int i=0; i<5; i++) {
           File picture = new File(path + "gallery2/thumb_gallery2_" + (i+1) + ".jpg");
           Log.d("imagecode", picture.getAbsolutePath());
            if (picture.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), options);
                GalleryImageView imageView = new GalleryImageView(getApplicationContext());
              imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(myBitmap);
                listLayout.addView(imageView);
            }
        }
        galleryListScroll.addView(listLayout);

        ScrollView galleryImageScroll = (ScrollView)findViewById(R.id.galleryImageScroll);
        
        LinearLayout mainLayout = new LinearLayout(getApplicationContext());
        mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setBackgroundColor(Color.WHITE);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        options.inSampleSize = 2;
        File parentThumb = new File(path + "thumb/");
        File[] thumbList = parentThumb.listFiles();
        Log.d("imagecode", "thumbList : " + thumbList.length);

        final ArrayList<File> selectedFileList = new ArrayList<File>();
        final ScrollView selectImageListScroll = (ScrollView) findViewById(R.id.selectImageListScroll);
      final LinearLayout selectImageLayout = new LinearLayout(getApplicationContext());
      selectImageLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      selectImageLayout.setGravity(Gravity.CENTER);
      selectImageLayout.setOrientation(LinearLayout.VERTICAL);
      //
        for (int j=0; j<8; j++) {
        	final int tempIndex = j;
            View galleryImageList= li.inflate(R.layout.gallery_list_7, null);
            galleryImageList.setOnTouchListener(new OnTouchListener() {
				int startIndex = 0;
				int endIndex = 0;
				public boolean onTouch(View v, MotionEvent event) {
					float startX=0, endX=0, widthX=0;
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						startX = event.getX();
						if (startX >=0 && startX <layoutX[0]) {
							startIndex = 0 + tempIndex*7;
						} else if (startX >=layoutX[0] && startX <layoutX[1]) {
							startIndex = 1 + tempIndex*7;
						} else if (startX >=layoutX[1] && startX <layoutX[2]) {
							startIndex = 2 + tempIndex*7;
						} else if (startX >=layoutX[2] && startX <layoutX[3]) {
							startIndex = 3 + tempIndex*7;
						} else if (startX >=layoutX[3] && startX <layoutX[4]) {
							startIndex = 4 + tempIndex*7;
						} else if (startX >=layoutX[4] && startX <layoutX[5]) {
							startIndex = 5 + tempIndex*7;
						} else if (startX >=layoutX[5] && startX <layoutX[6]) {
							startIndex = 6 + tempIndex*7;
						}
						Log.d("imagecode", "down startIndex" + startIndex);
						
					} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					} else if (event.getAction() == MotionEvent.ACTION_UP){
						endX = event.getX();
						if (endX >=0 && endX <layoutX[0]) {
							endIndex = 0 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "0. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) {
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                              
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						} else if (endX >=layoutX[0] && endX <layoutX[1]) {
							endIndex = 1 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "1. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) {
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}	
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                              
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						} else if (endX >=layoutX[1] && endX <layoutX[2]) {
							endIndex = 2 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "2. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) {
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                              
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						} else if (endX >=layoutX[2] && endX <layoutX[3]) {
							endIndex = 3 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "3. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) { // ��ϵ�
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                              
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						} else if (endX >=layoutX[3] && endX <layoutX[4]) {
							endIndex = 4 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "4. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) {
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                              
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						} else if (endX >=layoutX[4] && endX <layoutX[5]) {
							endIndex = 5 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "5. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) {
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						} else if (endX >=layoutX[5] && endX <layoutX[6]) {
							endIndex = 6 + tempIndex*7;
							for (int k=startIndex; k<=endIndex; k++) {
								Log.d("imagecode", "6. startIndex : " + k + " / endIndex : " + (startIndex+endIndex));
								if (codeCreateDTO.getFilePath().contains((String)imageViewArr[k].getTag())) {
									codeCreateDTO.getFilePath().remove((String)imageViewArr[k].getTag());
			                        imageViewArr[k].setAlpha(255);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.remove(new File(tempStr));
								} else { // �ȵ�
									codeCreateDTO.getFilePath().add((String)imageViewArr[k].getTag());
									imageViewArr[k].setAlpha(127);
			                        File picture = new File((String)imageViewArr[k].getTag());
			                        String tempStr = picture.getName();
			                        tempStr = tempStr.substring(0, tempStr.length()-3);
			                        tempStr = picture.getParent() + "/thumb/thumb_" + tempStr + "jpg";
			                        selectedFileList.add(new File(tempStr));
								}
							}
							int listSize = selectedFileList.size();
	                        if(listSize > 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                           
	                           View selectImageList = null;
	                           
	                           for(int i=0; i<listSize; i++){
	                              if(i%4 == 0){
	                                 selectImageList = li.inflate(R.layout.select_gallery_list_4, null);
	                              }
	                              File picture = selectedFileList.get(i);
	                              Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(),options);
	                              if(i % 4 == 0){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery1))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout1))).setVisibility(View.VISIBLE);
	                                 selectImageLayout.addView(selectImageList);
	                              }
	                              if(i % 4 == 1){
	                                 
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery2))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout2))).setVisibility(View.VISIBLE);
	                                 
	                              }
	                              if(i% 4 == 2){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery3))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout3))).setVisibility(View.VISIBLE);
	                              }
	                              if(i%4 ==3){
	                                 ((ImageView) (selectImageList.findViewById(R.id.selectGallery4))).setImageBitmap(myBitmap);
	                                 ((RelativeLayout) (selectImageList.findViewById(R.id.selectGalleryLayout4))).setVisibility(View.VISIBLE);
	                              }
	                              
	                           }
	                           
	                           selectImageListScroll.addView(selectImageLayout);
	                        }else if(listSize == 0){
	                           selectImageListScroll.removeAllViews();
	                           selectImageLayout.removeAllViews();
	                           TextView selectImageNum = (TextView)findViewById(R.id.selectImageNum);
	                           selectImageNum.setText(listSize+" Images");
	                        }
						}
						Log.d("imagecode", selectedFileList.toString());
					}
					return true;
				}
			});
           for (int i=0; i<7; i++) {
        	   final int index = i+(j*7);
        	   File picture = null; // ��
        	   File tagPicture = null;
        	   if (index <= 32) {
        		   picture = new File(path + "thumb/thumb_healing_" + index + ".jpg");
        		   tagPicture = new File(path + "healing_" + index + ".jpg");
        	   } else if (index > 32) {
        		   picture = new File(path + "thumb/thumb_flower_" + (index-33) + ".jpg");
        		   tagPicture = new File(path + "flower_" + (index-33) + ".jpg");
        	   }
               if ((i+(j*7)) == 0) {
                    ImageView imageViewType = (ImageView)galleryImageList.findViewById(R.id.gallery1Type);
                    imageViewType.setVisibility(View.VISIBLE);
					ImageView imageView = (ImageView) galleryImageList.findViewById(R.id.gallery1);
					String imagePath = tagPicture.getAbsolutePath();
					imagePath = imagePath.substring(0, imagePath.length() - 3);
					imageView.setTag(imagePath + "mp4");
				} else if ((i + (j * 7)) == 33) {
                    ImageView imageViewType = (ImageView)galleryImageList.findViewById(R.id.gallery6Type);
                    imageViewType.setVisibility(View.VISIBLE);
					ImageView imageView = (ImageView) galleryImageList.findViewById(R.id.gallery6);
					String imagePath = tagPicture.getAbsolutePath();
					imagePath = imagePath.substring(0, imagePath.length() - 3);
					imageView.setTag(imagePath + "mp4");
                } else {
					ImageView imageView = (ImageView) galleryImageList.findViewById(R.id.gallery1 + (i * 2));
					imageView.setTag(tagPicture.getAbsolutePath());
                }
               final ImageView imageView2 = (ImageView)galleryImageList.findViewById(R.id.gallery1 + (i*2));
               imageViewArr[index] = imageView2;
                if (picture.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), options);
                    final ImageView imageView = (ImageView)galleryImageList.findViewById(R.id.gallery1 + (i*2));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(myBitmap);
                    

                }
            }
           mainLayout.addView(galleryImageList);
        }
        galleryImageScroll.addView(mainLayout);
   }

   @Override
   protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      dtToggle.syncState();
   }

   @Override
   public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      dtToggle.onConfigurationChanged(newConfig);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (dtToggle.onOptionsItemSelected(item)) {
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   public void actionBarSettings() {
      ActionBar actionBar = getActionBar();
      actionBar.setBackgroundDrawable(new ColorDrawable(Color
            .parseColor("#FFFFFF")));
      actionBar.setDisplayShowCustomEnabled(true);
      actionBar.setCustomView(R.layout.actionbar_code_create);
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setDisplayUseLogoEnabled(false);
      actionBar.setDisplayShowHomeEnabled(false);
      actionBar.setDisplayHomeAsUpEnabled(false);


      ImageView action_item_btn_01 = (ImageView) actionBar.getCustomView()
            .findViewById(R.id.action_item_btn_01);
      final LinearLayout action_item_btn_01_layout = (LinearLayout) actionBar
            .getCustomView().findViewById(R.id.action_item_btn_01_layout);
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
               action_item_btn_01_layout
                     .setBackgroundDrawable(new ColorDrawable(Color
                           .parseColor("#eaeaea")));
            if (event.getAction() == MotionEvent.ACTION_UP)
               action_item_btn_01_layout
                     .setBackgroundDrawable(new ColorDrawable(Color
                           .parseColor("#ffffff")));
            return false;
         }
      });

      //
      TextView action_item_text_01 = (TextView) actionBar.getCustomView()
            .findViewById(R.id.action_item_text_01);
      action_item_text_01.setVisibility(View.INVISIBLE);

      //
      ImageView action_item_btn_02 = (ImageView) actionBar.getCustomView()
            .findViewById(R.id.action_item_btn_02);
      final LinearLayout action_item_btn_02_layout = (LinearLayout) actionBar
            .getCustomView().findViewById(R.id.action_item_btn_02_layout);
      action_item_btn_02_layout.setOnClickListener(new OnClickListener() {
         public void onClick(View v) {
            finish();
         }
      });
      action_item_btn_02_layout.setOnTouchListener(new OnTouchListener() {
         public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
               action_item_btn_02_layout
                     .setBackgroundDrawable(new ColorDrawable(Color
                           .parseColor("#eaeaea")));
            if (event.getAction() == MotionEvent.ACTION_UP)
               action_item_btn_02_layout
                     .setBackgroundDrawable(new ColorDrawable(Color
                           .parseColor("#ffffff")));
            return false;
         }
      });
   }

   public void onCodeCreate(View view) {
	   Log.d("imagecode", "finally : " + codeCreateDTO.toString());
      Intent codeCreateView = new Intent(getApplicationContext(),
            CodeCreateActivity.class);
      codeCreateView.putExtra("codeCreateDTO", codeCreateDTO);
      startActivityForResult(codeCreateView, 1031);
   }

   public void onSlidingClick(View view) {
   }

   protected void onActivityResult(int requestCode, int resultCode,
         Intent intent) {
      super.onActivityResult(requestCode, resultCode, intent);
      if (resultCode == 1031) {
         if (intent != null) {
            setResult(1030, intent);
            finish();
         }
      }
   }
}