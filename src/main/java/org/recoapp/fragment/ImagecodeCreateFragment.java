package org.recoapp.fragment;

import org.recoapp.activity.CodeCreateActivity;
import org.recoapp.activity.R;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ImagecodeCreateFragment extends Fragment {
    private static ImagecodeCreateFragment imagecodeCreateFragment;
    
    public static synchronized ImagecodeCreateFragment getInstance() {
    	if(imagecodeCreateFragment == null){
    		imagecodeCreateFragment = new ImagecodeCreateFragment();
    	}
        return imagecodeCreateFragment;
    }
    
    public static ImagecodeCreateFragment newInstance() {
    	ImagecodeCreateFragment fragment = new ImagecodeCreateFragment();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mainLayout = inflater.inflate(R.layout.imagecode_create_list_7, container, false);
        return mainLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}