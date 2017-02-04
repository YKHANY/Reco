package org.recoapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.recoapp.util.FileDTO;
import org.recoapp.util.FileDownloadDAO;

import uk.co.senab.photoview.PhotoViewAttacher;

public class CodeDetailViewerActivity extends Activity {
	PhotoViewAttacher mAttacher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		setContentView(R.layout.activity_code_detail_viewer);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();

        FileDTO temp = (FileDTO)getIntent().getSerializableExtra("fileDTO");
        if (temp.getFile_type().equals("image")) {
			FileDownloadDAO fileDownloadDAO = new FileDownloadDAO();
			Bitmap file = fileDownloadDAO.imageDownload(getApplicationContext(),temp);
			if(file != null)
			{
				LinearLayout layout = (LinearLayout)findViewById(R.id.viewer);
				ImageView image = new ImageView(getApplicationContext());
				image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				image.setScaleType(ImageView.ScaleType.FIT_CENTER);
				image.setBackgroundColor(Color.BLACK);
				image.setImageBitmap(file);
				mAttacher = new PhotoViewAttacher(image);
				layout.addView(image);
			}
        }
	}
}
