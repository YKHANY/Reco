package org.recoapp.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.recoapp.activity.R;
import org.recoapp.util.FileDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.RecoHttpClient;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class ImagecodeListFragment extends Fragment {
    private static ImagecodeListFragment[] imagecodeListFragment;
    private int content;
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
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
	public static synchronized ImagecodeListFragment getInstance(int content) {
		if (imagecodeListFragment == null)
			imagecodeListFragment = new ImagecodeListFragment[3];
		if (content == 0) { // ALL CODES
			if (imagecodeListFragment[0] == null) {
				imagecodeListFragment[0] = new ImagecodeListFragment();
				imagecodeListFragment[0].content = 0;
			}
			return imagecodeListFragment[0];
		} else if (content == 1) { // FAVORITES
			if (imagecodeListFragment[1] == null) {
				imagecodeListFragment[1] = new ImagecodeListFragment();
				imagecodeListFragment[1].content = 1;
			}
			return imagecodeListFragment[1];
		} else { // SHARED CODES
			if (imagecodeListFragment[2] == null) {
				imagecodeListFragment[2] = new ImagecodeListFragment();
				imagecodeListFragment[2].content = 2;
			}
			return imagecodeListFragment[2];
		}
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mInflater = inflater;
    	mContainer = container;
    	ScrollView scrollView = new ScrollView(getActivity());
    	scrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	scrollView.setBackgroundColor(Color.WHITE);

        LinearLayout mainLayout = new LinearLayout(getActivity());
        mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setBackgroundColor(Color.WHITE);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setId(1029);;

        ArrayList<ImagecodeDTO> imagecodeDTOList = (ArrayList<ImagecodeDTO>)getActivity().getIntent().getSerializableExtra("imagecodeDTOList");
        ArrayList<ImagecodeDTO> imagecodeDTOTagList = (ArrayList<ImagecodeDTO>)getActivity().getIntent().getSerializableExtra("imagecodeDTOTagList");
        ArrayList<ImagecodeDTO> favoriteDTOList = (ArrayList<ImagecodeDTO>)getActivity().getIntent().getSerializableExtra("favoriteDTOList");
        
        if (content == 0) {
        	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
			int size = imagecodeDTOList.size()/3;
			int size2 = imagecodeDTOList.size()%3;
			if (size2 > 0) {
				size++;
			}
        	for (int i=0; i<size; i++) {
            	View rootView = inflater.inflate(R.layout.imagecode_list_3, container, false);
        		for (int j=0; j<3; j++) {
        			if ((j + i*3) > imagecodeDTOList.size()-1)
        				break;
        			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
        			ImagecodeDTO temp = imagecodeDTOList.get(j + i*3);
                	if (j%3 == 0) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode1);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]); // �ε��� ����

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout1);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName1);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType1);
                		boolean flag = false;
            			for (int k=0; k<favoriteDTOList.size(); k++) {
            				if (temp.getImagecode_code().equals(favoriteDTOList.get(k).getImagecode_code())) {
            					flag = true;
            				}
            			}
            			if (flag) { // FAVORITES
                    		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                    		imagecodeType.setVisibility(View.VISIBLE);
            			} else {
            				if (temp.getMember_code().equals("member2")) { // MY CODE
                        		imagecodeType.setVisibility(View.INVISIBLE);
                    		} else { // SHARED CODES
                        		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                        		imagecodeType.setVisibility(View.VISIBLE);
                			}
            			}
                	} else if (j%3 == 1) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode2);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]); // �ε��� ����

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout2);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test2", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) { // ����
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName2);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType2);
                		boolean flag = false;
            			for (int k=0; k<favoriteDTOList.size(); k++) {
            				if (temp.getImagecode_code().equals(favoriteDTOList.get(k).getImagecode_code())) {
            					flag = true;
            				}
            			}
            			if (flag) { // FAVORITES
                    		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                    		imagecodeType.setVisibility(View.VISIBLE);
            			} else {
            				if (temp.getMember_code().equals("member2")) { // MY CODE
                        		imagecodeType.setVisibility(View.INVISIBLE);
                    		} else { // SHARED CODES
                        		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                        		imagecodeType.setVisibility(View.VISIBLE);
                			}
            			}
                	} else {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode3);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout3);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test3", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName3);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType3);
                		boolean flag = false;
            			for (int k=0; k<favoriteDTOList.size(); k++) {
            				if (temp.getImagecode_code().equals(favoriteDTOList.get(k).getImagecode_code())) {
            					flag = true;
            				}
            			}
            			if (flag) { // FAVORITES
                    		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                    		imagecodeType.setVisibility(View.VISIBLE);
            			} else {
            				if (temp.getMember_code().equals("member2")) { // MY CODE
                        		imagecodeType.setVisibility(View.INVISIBLE);
                    		} else { // SHARED CODES
                        		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                        		imagecodeType.setVisibility(View.VISIBLE);
                			}
            			}
                	}
        		}
                mainLayout.addView(rootView);
        	}
    	} else if (content == 1) {
    		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
			int size = favoriteDTOList.size()/3;
			int size2 = favoriteDTOList.size()%3;
			if (size2 > 0) {
				size++;
			}

        	for (int i=0; i<size; i++) {
            	View rootView = inflater.inflate(R.layout.imagecode_list_3, container, false);
        		for (int j=0; j<3; j++) {
        			if ((j + i*3) > favoriteDTOList.size()-1)
        				break;
        			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
        			ImagecodeDTO temp = favoriteDTOList.get(j + i*3);
                	if (j%3 == 0) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode1);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout1);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test5", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName1);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType1);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                        imagecodeType.setVisibility(View.VISIBLE);
                	} else if (j%3 == 1) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode2);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout2);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test6", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName2);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType2);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                        imagecodeType.setVisibility(View.VISIBLE);
                	} else {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode3);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout3);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test7", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName3);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType3);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                        imagecodeType.setVisibility(View.VISIBLE);
                	}
        		}
                mainLayout.addView(rootView);
        	}
    		/*
        	View rootView = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	View rootView2 = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView2.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView2.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	View rootView3 = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView3.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView3.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	View rootView4 = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView4.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView4.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	View rootView5 = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView5.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView5.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	View rootView6 = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView6.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView6.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	View rootView7 = inflater.inflate(R.layout.imagecode_list_5, container, false);
        	((ImageView)(rootView7.findViewById(R.id.imageCodeType1))).setVisibility(View.INVISIBLE);
        	((ImageView)(rootView7.findViewById(R.id.imageCodeType4))).setVisibility(View.INVISIBLE);
        	
            mainLayout.addView(rootView);
            mainLayout.addView(rootView2);
            mainLayout.addView(rootView3);
            mainLayout.addView(rootView4);
            mainLayout.addView(rootView5);
            mainLayout.addView(rootView6);
            mainLayout.addView(rootView7);*/
    	} else {
    		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
			int size = imagecodeDTOTagList.size()/3;
			int size2 = imagecodeDTOTagList.size()%3;
			if (size2 > 0) {
				size++;
			}
        	for (int i=0; i<size; i++) {
            	View rootView = inflater.inflate(R.layout.imagecode_list_3, container, false);
        		for (int j=0; j<3; j++) {
        			if ((j + i*3) > imagecodeDTOTagList.size()-1)
        				break;
        			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
        			ImagecodeDTO temp = imagecodeDTOTagList.get(j + i*3);
                	if (j%3 == 0) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode1);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout1);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "plain text"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName1);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType1);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                    	imagecodeType.setVisibility(View.VISIBLE);
                	} else if (j%3 == 1) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode2);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]); // �ε��� ����

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout2);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName2);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType2);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                    	imagecodeType.setVisibility(View.VISIBLE);
                	} else {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode3);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout3);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName3);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType3);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                    	imagecodeType.setVisibility(View.VISIBLE);
                	}
        		}
                mainLayout.addView(rootView);
        	}
    		/*
        	View rootView = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView2 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView3 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView4 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView5 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView6 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView7 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView8 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView9 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	View rootView10 = inflater.inflate(R.layout.imagecode_list_7, container, false);
        	
            mainLayout.addView(rootView);
            mainLayout.addView(rootView2);
            mainLayout.addView(rootView3);
            mainLayout.addView(rootView4);
            mainLayout.addView(rootView5);
            mainLayout.addView(rootView6);
            mainLayout.addView(rootView7);
            mainLayout.addView(rootView8);
            mainLayout.addView(rootView9);
            mainLayout.addView(rootView10);*/
    	}
    	
        scrollView.addView(mainLayout);
        return scrollView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    
    private void imagecode_delete(ImagecodeDTO imagecodeDTO) {
    	final Intent intent = new Intent();
    	RequestParams params = new RequestParams();
		params.add("imagecode_code", imagecodeDTO.getImagecode_code());
		RecoHttpClient.getInstance().post(getActivity(), "imagecode/delete", params, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, Header[] arg1, byte[] response) {
				try {
					imagecode_search(intent);
				} catch (Exception e) {
					Log.d("imagecode", e.getMessage());
					e.printStackTrace();
				}
			}
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
	}
    
    private void imagecode_search (final Intent intent) {
		final SharedPreferences prefs = getActivity().getSharedPreferences("imagecode", Context.MODE_PRIVATE);
		final SharedPreferences.Editor ed = prefs.edit();
		final HashSet<String> values = (HashSet<String>) prefs.getStringSet("favoriteList", new HashSet<String>());
		
		RequestParams params2 = new RequestParams();
		RecoHttpClient.getInstance().post(getActivity(), "imagecode/search_all", params2, new AsyncHttpResponseHandler() {
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
					RecoHttpClient.getInstance().post(getActivity(), "imagecode/search_tag", params2, new AsyncHttpResponseHandler() {
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
								
								ImagecodeListFragment.getInstance(0).refreshList(intent);
								ImagecodeListFragment.getInstance(1).refreshList(intent);
							} catch (Exception e) {
								Log.d("imagecode", e.getMessage());
								e.printStackTrace();
							}
						}
						public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
							Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
						}
					});
				} catch (Exception e) {
					Log.d("imagecode", e.getMessage());
					e.printStackTrace();
				}
			}
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
			}
		});
	}
    
    public void refreshList(Intent intent) {
    	LinearLayout mainLayout = (LinearLayout)getView().findViewById(1029);
    	mainLayout.removeAllViews();

        ArrayList<ImagecodeDTO> imagecodeDTOList = (ArrayList<ImagecodeDTO>)intent.getSerializableExtra("imagecodeDTOList");
        ArrayList<ImagecodeDTO> imagecodeDTOTagList = (ArrayList<ImagecodeDTO>)intent.getSerializableExtra("imagecodeDTOTagList");
        ArrayList<ImagecodeDTO> favoriteDTOList = (ArrayList<ImagecodeDTO>)intent.getSerializableExtra("favoriteDTOList");
        for (int i=0; i<favoriteDTOList.size(); i++) {
        	Log.d("imagecode", favoriteDTOList.get(i).toString());
        }
        
        if (content == 0) {
        	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
			int size = imagecodeDTOList.size()/3;
			int size2 = imagecodeDTOList.size()%3;
			if (size2 > 0) {
				size++;
			}
        	for (int i=0; i<size; i++) {
            	View rootView = mInflater.inflate(R.layout.imagecode_list_3, mContainer, false);
        		for (int j=0; j<3; j++) {
        			if ((j + i*3) > imagecodeDTOList.size()-1)
        				break;
        			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
        			ImagecodeDTO temp = imagecodeDTOList.get(j + i*3);
                	if (j%3 == 0) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode1);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout1);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName1);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType1);
                		boolean flag = false;
            			for (int k=0; k<favoriteDTOList.size(); k++) {
            				if (temp.getImagecode_code().equals(favoriteDTOList.get(k).getImagecode_code())) {
            					flag = true;
            					break;
            				}
            			}
            			if (flag) { // FAVORITES
                    		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                    		imagecodeType.setVisibility(View.VISIBLE);
            			} else {
            				if (temp.getMember_code().equals("member2")) { // MY CODE
                        		imagecodeType.setVisibility(View.INVISIBLE);
                    		} else { // SHARED CODES
                        		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                        		imagecodeType.setVisibility(View.VISIBLE);
                			}
            			}
                	} else if (j%3 == 1) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode2);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout2);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName2);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType2);
                		boolean flag = false;
            			for (int k=0; k<favoriteDTOList.size(); k++) {
            				if (temp.getImagecode_code().equals(favoriteDTOList.get(k).getImagecode_code())) {
            					flag = true;
            					break;
            				}
            			}
            			if (flag) { // FAVORITES
                    		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                    		imagecodeType.setVisibility(View.VISIBLE);
            			} else {
            				if (temp.getMember_code().equals("member2")) { // MY CODE
                        		imagecodeType.setVisibility(View.INVISIBLE);
                    		} else { // SHARED CODES
                        		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                        		imagecodeType.setVisibility(View.VISIBLE);
                			}
            			}
                	} else {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode3);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout3);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName3);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType3);
                		boolean flag = false;
            			for (int k=0; k<favoriteDTOList.size(); k++) {
            				if (temp.getImagecode_code().equals(favoriteDTOList.get(k).getImagecode_code())) {
            					flag = true;
            					break;
            				}
            			}
            			if (flag) { // FAVORITES
                    		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                    		imagecodeType.setVisibility(View.VISIBLE);
            			} else {
            				if (temp.getMember_code().equals("member2")) { // MY CODE
                        		imagecodeType.setVisibility(View.INVISIBLE);
                    		} else { // SHARED CODES
                        		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                        		imagecodeType.setVisibility(View.VISIBLE);
                			}
            			}
                	}
        		}
                mainLayout.addView(rootView);
        	}
    	} else if (content == 1) {
    		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
			int size = favoriteDTOList.size()/3;
			int size2 = favoriteDTOList.size()%3;
			if (size2 > 0) {
				size++;
			}

        	for (int i=0; i<size; i++) {
            	View rootView = mInflater.inflate(R.layout.imagecode_list_3, mContainer, false);
        		for (int j=0; j<3; j++) {
        			if ((j + i*3) > favoriteDTOList.size()-1)
        				break;
        			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
        			ImagecodeDTO temp = favoriteDTOList.get(j + i*3);
                	if (j%3 == 0) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode1);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout1);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName1);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType1);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                        imagecodeType.setVisibility(View.VISIBLE);
                	} else if (j%3 == 1) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode2);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout2);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName2);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType2);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                        imagecodeType.setVisibility(View.VISIBLE);
                	} else {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode3);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout3);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName3);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType3);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_02);
                        imagecodeType.setVisibility(View.VISIBLE);
                	}
        		}
                mainLayout.addView(rootView);
        	}
    	} else {
    		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Gotham-Book.ttf");
			int size = imagecodeDTOTagList.size()/3;
			int size2 = imagecodeDTOTagList.size()%3;
			if (size2 > 0) {
				size++;
			}
        	for (int i=0; i<size; i++) {
            	View rootView = mInflater.inflate(R.layout.imagecode_list_3, mContainer, false);
        		for (int j=0; j<3; j++) {
        			if ((j + i*3) > imagecodeDTOTagList.size()-1)
        				break;
        			Log.d("imagecode", "size : " + size + " / index : " + (j+i*3));
        			ImagecodeDTO temp = imagecodeDTOTagList.get(j + i*3);
                	if (j%3 == 0) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode1);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout1);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName1);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType1);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                    	imagecodeType.setVisibility(View.VISIBLE);
                	} else if (j%3 == 1) {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode2);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout2);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName2);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType2);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                    	imagecodeType.setVisibility(View.VISIBLE);
                	} else {
                		ImageView imagecode = (ImageView)rootView.findViewById(R.id.imageCode3);
                		imagecode.setImageResource(imagecodeId[temp.getImagecode_url()]);

                		final LinearLayout tileLayout = (LinearLayout)rootView.findViewById(R.id.tileLayout3);
                		tileLayout.setVisibility(View.VISIBLE);
                		tileLayout.setClickable(true);
                		tileLayout.setTag(temp);
                		tileLayout.setOnLongClickListener(new OnLongClickListener() {
							public boolean onLongClick(View v) {
								final AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
								String[] items = new String[]{"test", "test"};
								dlg.setItems(items, new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (which == 0) {
											imagecode_delete((ImagecodeDTO)tileLayout.getTag());
											dialog.dismiss();
										} else {
											dialog.dismiss();
										}
									}
								});
								Dialog dialog = dlg.create();
								dialog.show();
								return false;
							}
						});
                		
                		TextView imagecode_name = (TextView)rootView.findViewById(R.id.imageCodeName3);
                		imagecode_name.setTypeface(tf);
                		imagecode_name.setText(temp.getImagecode_name());

                		ImageView imagecodeType = (ImageView)rootView.findViewById(R.id.imageCodeType3);
                		imagecodeType.setImageResource(R.drawable.imagecode_type_01);
                    	imagecodeType.setVisibility(View.VISIBLE);
                	}
        		}
                mainLayout.addView(rootView);
        	}
    	}
    }
}
