package org.recoapp.adapter;

import java.io.File;

import org.recoapp.util.GalleryImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryListAdapter extends BaseAdapter {
	Context context;
	private String path;
	String[] galleryID = { "thumb_gallery2_01.jpg", "thumb_gallery2_02.jpg", "thumb_gallery2_03.jpg", "thumb_gallery2_04.jpg", "thumb_gallery2_05.jpg" };
	
	public GalleryListAdapter (Context c, String path) {
		context = c;
		this.path = path;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return galleryID.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GalleryImageView imageView = new GalleryImageView(context);
		imageView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
		File picture = new File(path+galleryID[position]);
        if (picture.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), options);
            imageView.setImageBitmap(myBitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
		return imageView;
	}

}
