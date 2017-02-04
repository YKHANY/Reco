package com.ssu.reco2;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvGet2D;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RGB2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_THRESH_BINARY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvThreshold;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.recoapp.activity.CodeDetailActivity;
import org.recoapp.activity.R;
import org.recoapp.util.FileDTO;
import org.recoapp.util.ImagecodeDTO;
import org.recoapp.util.RecoHttpClient;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ssu.bp.BpModule;
import com.ssu.bp.BpRes;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "HelloCamera";
	SurfaceView mSurfaceView;
	SurfaceHolder mHolder;
	Camera mCamera;
	List<Size> mSupportedPreviewSizes;
	DrawingView drawingView;
	boolean drawingViewSet = false;
	boolean listenerSet = true;
	ImageView imageView;
	TextView textView;
	Button captureBtn;
	ScrollView scrollView;
	ScanMainActivity context;
	DisplayMetrics mainDisplayMetrics;
	BpModule bp;

	@SuppressWarnings("deprecation")
	Preview(ScanMainActivity context, DisplayMetrics displayMetrics) {
		super(context);

		this.context = context;
		mainDisplayMetrics = displayMetrics;
		// drawingView = (DrawingView) findViewById(R.id.id_view);
		if (drawingView != null) {
			drawingViewSet = true;
		}
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
		// addView(mSurfaceView);
		//Log.e(context.getString(R.string.app_name), "add sucessfully");
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.

		mHolder = getHolder();
		mHolder.addCallback(this);
		// mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// backpropagation init
		bp = new BpModule(context);
		bp.init();
		
		File file = context.getExternalFilesDir(null);
		String path = file.getAbsolutePath() + "/neural.dat";
		
		Log.i("BpModule", "Load start");
		bp.Load(path);
		Log.i("BpModule", "Load finished");

	}

	void setCapture(ImageView imageView, Button captureBtn) {
		// take picture
		this.imageView = imageView;
		this.captureBtn = captureBtn;
		captureBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mCamera.takePicture(shutterCallback, rawCallback,
						mPictureListener);
			}
		});
	}

	void setTextView(TextView textView) {
		this.textView = textView;
	}

	void setScrollView(ScrollView scrollView) {
		this.scrollView = scrollView;
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};
	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	// JPEG
	private Camera.PictureCallback mPictureListener = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i(TAG, "Picture Taken");
			if (data != null) {
				Log.i(TAG, "JPEG Picture Taken");
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length); // bitmap ������ ������ ����

				// Matrix matrix = new Matrix();
				// rotate the Bitmap
				// matrix.postRotate(90);
				// recreate the new Bitmap
				// Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0,
				// 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				// ���ǽ��並 ���ְ� �̹����並 �ҷ��ͼ� ������ ������
				// mSurfaceView.setVisibility(View.GONE);

				if (bitmap != null) {

					Matrix matrix = new Matrix(); // rotate the Bitmap
					matrix.postRotate(90); // recreate the new Bitmap
					Bitmap rotateBitmap = Bitmap
							.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, true);
					Log.e("width, height", "width : " + rotateBitmap.getWidth()
							+ " height : " + rotateBitmap.getHeight());
					Bitmap croppedBitmap = Bitmap.createBitmap(rotateBitmap,
							365, 725, 400, 400);
					Bitmap scaledImage = Bitmap.createScaledBitmap(
							croppedBitmap, 100, 100, true);
					// saveBitmaptoJpeg(scaledImage, "cam1");

					// saveBitmaptoJpeg(rotateBitmap, "test");
					if (ScanMainActivity.extData || ScanMainActivity.backP) {
						IplImage srcIpl = IplImage.create(
								scaledImage.getWidth(),
								scaledImage.getHeight(), IPL_DEPTH_8U, 4);
						Log.i("cropped size ",
								"width :" + scaledImage.getWidth()
										+ "height : " + scaledImage.getHeight());
						IplImage desIpl = IplImage.create(
								scaledImage.getWidth(),
								scaledImage.getHeight(), IPL_DEPTH_8U, 1);
						scaledImage.copyPixelsToBuffer(srcIpl.getByteBuffer());
						cvCvtColor(srcIpl, desIpl, CV_RGB2GRAY);
						cvThreshold(desIpl, desIpl, 140, 255, CV_THRESH_BINARY);
						ControlThread ctrThread = new ControlThread(context,
								mHandler, scaledImage.getWidth(),
								scaledImage.getHeight(), desIpl);
						ctrThread.start();
					}
					if (ScanMainActivity.bin) {
						IplImage srcIpl = IplImage.create(
								croppedBitmap.getWidth(),
								croppedBitmap.getHeight(), IPL_DEPTH_8U, 4);
						Log.i("cropped size ",
								"width :" + croppedBitmap.getWidth()
										+ "height : "
										+ croppedBitmap.getHeight());
						IplImage desIpl = IplImage.create(
								croppedBitmap.getWidth(),
								croppedBitmap.getHeight(), IPL_DEPTH_8U, 1);
						croppedBitmap
								.copyPixelsToBuffer(srcIpl.getByteBuffer());
						cvCvtColor(srcIpl, desIpl, CV_RGB2GRAY);
						cvThreshold(desIpl, desIpl, 140, 255, CV_THRESH_BINARY);

						Bitmap res = IplImageToBitmap(desIpl);
						imageView.setImageBitmap(res);
					}
					// Bitmap res =
					// Bitmap.createBitmap(desIpl.width(),desIpl.height(),Bitmap.Config.ARGB_8888);
					// //

					// Bitmap bmap = imageView.getDrawingCache();
					// Bitmap res = IplImageToBitmap(desIpl);
					// save(res,"test");

					// imageView.setVisibility(View.GONE);
					// captureBtn.setVisibility(View.GONE);

					// scrollView.setVisibility(View.VISIBLE);

					// imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				} else {
					Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT)
							.show();
				}
				// surfaceView.setVisibility(View.GONE);
				// Picturetmp.setVisibility(View.VISIBLE);
				// Picturetmp.setImageBitmap(rotateBitmap);
				// Picturetmp.setScaleType(ImageView.ScaleType.FIT_XY);
			}
		}
	};

	public static Bitmap IplImageToBitmap(IplImage src) {
		int width = src.width();
		int height = src.height();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int gray = (int) Math.floor(cvGet2D(src, r, c).getVal(0));
				bitmap.setPixel(c, r, Color.argb(255, gray, gray, gray));
			}
		}
		Log.i("IplImageToBitmap", "end job");
		return bitmap;
	}

	public void saveBitmaptoJpeg(Bitmap bitmap, String name) {

		// String ex_storage =
		// Environment.getExternalStorageDirectory().getAbsolutePath();
		// String folder_name = "/"+folder + "/";
		String file_name = name + ".jpg";
		// String string_path = ex_storage + folder_name;

		File file = context.getExternalFilesDir(null);
		String path = file.getAbsolutePath() + "/" + file_name;
		File saveFile = new File(path);

		FileOutputStream out = null;
		try {
			saveFile.createNewFile();
			out = new FileOutputStream(saveFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		} catch (FileNotFoundException fnfe) {
			Log.e("FileNotFoundException", fnfe.getMessage());
		} catch (IOException ioe) {
			Log.e("IOException", ioe.getMessage());
		}
	}

	public void save(Bitmap bitmap, String name) {
		File file;
		String path = Environment.getExternalStorageDirectory()
				+ "/Android/data/com.ssm.reco2/files";
		file = new File(path);
		try {
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path + "/test.jpg");
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera = Camera.open();

		Camera.Parameters params = mCamera.getParameters();
		List<Size> size = params.getSupportedPictureSizes();
		int width = mainDisplayMetrics.widthPixels;
		int height = mainDisplayMetrics.heightPixels;
		Log.i("main size  ", "width : " + width + "height : " + height);
		for (Size supporedSize : size) {
			if (supporedSize.height * supporedSize.width <= width * height) {
				width = supporedSize.width;
				height = supporedSize.height;
				break;
			}
		}
		params.setPictureSize(width, height);
		mCamera.setParameters(params);
		Log.i(TAG, "Camera opened");
		if (Build.VERSION.SDK_INT >= 8)
			mCamera.setDisplayOrientation(90);
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		// Camera.Parameters parameters = mCamera.getParameters();
		// parameters.setPreviewSize(width, height);
		// mCamera.setParameters(parameters);
		mCamera.startPreview();
		Log.i(TAG, "Camera preview started");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera.release();
		mCamera = null;
		Log.i(TAG, "Camera released");

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (!listenerSet) {
			return false;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();

			Rect touchRect = new Rect((int) (x - 100), (int) (y - 100),
					(int) (x + 100), (int) (y + 100));

			final Rect targetFocusRect = new Rect(touchRect.left * 2000
					/ this.getWidth() - 1000, touchRect.top * 2000
					/ this.getHeight() - 1000, touchRect.right * 2000
					/ this.getWidth() - 1000, touchRect.bottom * 2000
					/ this.getHeight() - 1000);

			this.doTouchFocus(targetFocusRect);
			if (drawingViewSet) {
				drawingView.setHaveTouch(true, touchRect);
				drawingView.invalidate();

				// Remove the square indicator after 1000 msec
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						drawingView.setHaveTouch(false, new Rect(0, 0, 0, 0));
						drawingView.invalidate();
					}
				}, 1000);
			}

		}

		return false;
	}

	AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback() {

		@Override
		public void onAutoFocus(boolean arg0, Camera arg1) {
			if (arg0) {
				mCamera.cancelAutoFocus();
			}
		}
	};

	/**
	 * Called from PreviewSurfaceView to set touch focus.
	 * 
	 * @param - Rect - new area for auto focus
	 */
	public void doTouchFocus(final Rect tfocusRect) {
		try {
			List<Camera.Area> focusList = new ArrayList<Camera.Area>();
			Camera.Area focusArea = new Camera.Area(tfocusRect, 1000);
			focusList.add(focusArea);

			Camera.Parameters param = mCamera.getParameters();
			param.setFocusAreas(focusList);
			param.setMeteringAreas(focusList);
			mCamera.setParameters(param);

			mCamera.autoFocus(myAutoFocusCallback);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "Unable to autofocus");
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String tmp = (String) msg.obj + '\n';
				// Log.i("Contour",tmp);
				textView.append(tmp);
				//Toast.makeText(context, "good", Toast.LENGTH_SHORT).show();
				if (ScanMainActivity.backP) {
					BpThread bpThread = new BpThread(context, mHandler, bp);
					bpThread.start();
				}
				break;
			case 4:
				Toast.makeText(context, "bad", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				BpRes res = (BpRes) msg.obj;

				if (res.getMaxVal() < 0.0) {
					Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();
					/*Toast.makeText(
							context,
							"Character recognized. But Maybe not correct: "
									+ res.getIndex() + "  : "
									+ Float.toString(res.getMaxVal()),
							Toast.LENGTH_SHORT).show();*/
				} else {
					if (res.getIndex() == 0) {
						res.setIndex(64); // ��
					} else if (res.getIndex() == 1) {
						res.setIndex(25); // Ǯ
					}
					RequestParams params = new RequestParams();
					params.add("imagecode_url", res.getIndex()+"");
					RecoHttpClient.getInstance().post(context, "imagecode/search_url", params, new AsyncHttpResponseHandler() {
						public void onSuccess(int arg0, Header[] arg1, byte[] response) {
							try {
								String input = new String(response, 0, response.length, "UTF-8");
								JSONArray arr = new JSONArray(input);
								ImagecodeDTO imagecodeDTO = null;
								for (int i=0; i<arr.length(); i++) {
									JSONObject obj = arr.getJSONObject(i);
									imagecodeDTO = new ImagecodeDTO(obj.getString("imagecode_code"), obj.getString("member_code"),
											obj.getString("imagecode_latest_modifydate"), obj.getString("imagecode_name"), obj.getString("imagecode_url"));
								}
								Log.d("imagecode", "recognize : " + imagecodeDTO.toString());
								
								final Intent codeDetailView = new Intent(context, CodeDetailActivity.class);
								codeDetailView.putExtra("imagecodeDTO", imagecodeDTO);
								
								RequestParams params = new RequestParams();
								params.add("imagecode_code", imagecodeDTO.getImagecode_code());
								RecoHttpClient.getInstance().post(context, "file/search_imagecode", params, new AsyncHttpResponseHandler() {
									public void onSuccess(int arg0, Header[] arg1, byte[] response) {
										try {
											String input = new String(response, 0, response.length, "UTF-8");
											JSONArray arr = new JSONArray(input);
											ArrayList<FileDTO> fileDTOImageList = new ArrayList<FileDTO>();
											ArrayList<FileDTO> fileDTOVideoList = new ArrayList<FileDTO>();
											for (int i=0; i<arr.length(); i++) {
												JSONObject obj = arr.getJSONObject(i);
												if (obj.getString("file_type").equals("image")) {
													fileDTOImageList.add(new FileDTO(obj.getString("file_code"), obj.getString("member_code"), obj.getString("imagecode_code"), 
															obj.getString("file_latest_modifydate"), obj.getString("file_type"), obj.getString("file_name"), 
															obj.getString("file_url"), obj.getString("file_thumb_url")));
												} else {
													fileDTOVideoList.add(new FileDTO(obj.getString("file_code"), obj.getString("member_code"), obj.getString("imagecode_code"), 
															obj.getString("file_latest_modifydate"), obj.getString("file_type"), obj.getString("file_name"), 
															obj.getString("file_url"), obj.getString("file_thumb_url")));
												}
											}
											Log.d("imagecode", "fileDTOImageList " + fileDTOImageList.size()+"");
											Log.d("imagecode", "fileDTOVideoList " + fileDTOVideoList.size()+"");
											codeDetailView.putExtra("fileDTOImageList", fileDTOImageList);
											codeDetailView.putExtra("fileDTOVideoList", fileDTOVideoList);
											
											context.startActivity(codeDetailView);
											context.finish();
										} catch (Exception e) {
											Log.d("imagecode", e.getMessage());
											e.printStackTrace();
										}
									}
									public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
										Toast.makeText(context, "���� ����", Toast.LENGTH_SHORT).show();
									}
								});
								
							} catch (Exception e) {
								Log.d("imagecode", e.getMessage());
								e.printStackTrace();
							}
						}
						public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
							Toast.makeText(context, "���� ����", Toast.LENGTH_SHORT).show();
						}
					});
					
					
					
					/*Toast.makeText(
							context,
							"Character recognized: " + res.getIndex() + "  : "
									+ Float.toString(res.getMaxVal()),
							Toast.LENGTH_SHORT).show();*/
				}
				break;
			case 6: // save num
				Toast.makeText(context, "good", Toast.LENGTH_SHORT).show();
				int num = 0;
				if (ScanMainActivity.cam.equals("cam1_")) {
					num = ScanMainActivity.cam1Num;
				}
				if (ScanMainActivity.cam.equals("cam2_")) {
					num = ScanMainActivity.cam2Num;
				}
				if (ScanMainActivity.cam.equals("cam3_")) {
					num = ScanMainActivity.cam3Num;
				}
				if (ScanMainActivity.cam.equals("cam4_")) {
					num = ScanMainActivity.cam4Num;
				}
				if (ScanMainActivity.cam.equals("cam5_")) {
					num = ScanMainActivity.cam5Num;
				}
				if (ScanMainActivity.cam.equals("cam6_")) {
					num = ScanMainActivity.cam6Num;
				}
				if (ScanMainActivity.cam.equals("cam7_")) {
					num = ScanMainActivity.cam7Num;
				}
				if (ScanMainActivity.cam.equals("cam8_")) {
					num = ScanMainActivity.cam8Num;
				}
				if (ScanMainActivity.cam.equals("cam9_")) {
					num = ScanMainActivity.cam9Num;
				}
				num--;
				Toast.makeText(context, ScanMainActivity.cam + num,
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}

class BpThread extends Thread {
	Handler mHandler;
	BpModule bp;
	Context context;

	public BpThread(Context context, Handler handler, BpModule bp) {
		this.context = context;
		this.mHandler = handler;
		this.bp = bp;
	}

	public void run() {
		File file = context.getExternalFilesDir(null);
		String path = file.getAbsolutePath() + "/input.txt";
		BpRes res = bp.doRunButton(path);
		Message msg = Message.obtain();
		msg.what = 5;
		msg.obj = res;
		mHandler.sendMessage(msg);
	}
}

class ControlThread extends Thread {
	Handler mHandler;
	int w, h;
	IplImage image;
	Context context;

	public ControlThread(Context context, Handler handler, int w, int h,
			IplImage image) {
		this.context = context;
		this.mHandler = handler;
		this.w = w;
		this.h = h;
		this.image = image;
	}

	public void run() {
		DibSegment dib = new DibSegment();
		ContourPoints cp = dib.contourTracing(image);

		if (ScanMainActivity.extData) {
			if (cp.num > 30) {
				/*
				 * char[][] ptr2 = new char[h][w]; for (int i = 0; i < h; i++) {
				 * for (int j = 0; j < w; j++) { ptr2[i][j] = 0; } }
				 * 
				 * for (int i = 0; i < cp.num; i++) { ptr2[cp.y[i]][cp.x[i]] =
				 * 1; }
				 * 
				 * Log.i("Contour", "cp.num" + cp.num);
				 * 
				 * Log.i("Contour", "contour data setting finish"); String res =
				 * ""; String tmp = "";
				 * 
				 * for (int i = 0; i < h; i++) { for (int j = 0; j < w; j++) {
				 * tmp += (int) ptr2[i][j]; } res += tmp+'\n'; tmp = ""; }
				 */
				// training file extract
				// SimpleDateFormat mSimpleDateFormat = new
				// SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
				// Date currentTime = new Date();
				// String mTime = mSimpleDateFormat.format(currentTime);

				char[][] ptr = new char[h][w];
				ByteBuffer buffer = image.getByteBuffer();
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						int index = i * image.widthStep() + j
								* image.nChannels();
						ptr[i][j] = (char) buffer.get(index);
						// Log.i("Dib",(byte)ptr[i][j]+"");
					}
				}

				String res = "";
				String tmp = "";
				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						if ((int) ptr[i][j] != 0) {
							tmp += "0";
						} else {
							tmp += "1";
						}
						// tmp += (int) ptr[i][j];
					}
					res += tmp + '\n';
					tmp = "";
				}

				int num = 0;
				if (ScanMainActivity.cam.equals("cam1_")) {
					num = ScanMainActivity.cam1Num++;
				}
				if (ScanMainActivity.cam.equals("cam2_")) {
					num = ScanMainActivity.cam2Num++;
				}
				if (ScanMainActivity.cam.equals("cam3_")) {
					num = ScanMainActivity.cam3Num++;
				}
				if (ScanMainActivity.cam.equals("cam4_")) {
					num = ScanMainActivity.cam4Num++;
				}
				if (ScanMainActivity.cam.equals("cam5_")) {
					num = ScanMainActivity.cam5Num++;
				}
				if (ScanMainActivity.cam.equals("cam6_")) {
					num = ScanMainActivity.cam6Num++;
				}
				if (ScanMainActivity.cam.equals("cam7_")) {
					num = ScanMainActivity.cam7Num++;
				}
				if (ScanMainActivity.cam.equals("cam8_")) {
					num = ScanMainActivity.cam8Num++;
				}
				if (ScanMainActivity.cam.equals("cam9_")) {
					num = ScanMainActivity.cam9Num++;
				}

				String path = null;
				// training data extract

				String tmpPath = ScanMainActivity.cam + num;
				File file = context.getExternalFilesDir(null);
				path = file.getAbsolutePath() + "/" + tmpPath + ".txt";
				File saveFile = new File(path);
				BufferedWriter bfw = null;
				try {
					saveFile.createNewFile();
					bfw = new BufferedWriter(new FileWriter(saveFile));
					bfw.write(res);
					bfw.flush();
					bfw.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(6);
			} else {
				mHandler.sendEmptyMessage(4);
			}
		}
		if (ScanMainActivity.backP) {
			char[][] ptr = new char[h][w];
			ByteBuffer buffer = image.getByteBuffer();
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					int index = i * image.widthStep() + j * image.nChannels();
					ptr[i][j] = (char) buffer.get(index);
					// Log.i("Dib",(byte)ptr[i][j]+"");
				}
			}

			String res = "";
			String tmp = "";
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if ((int) ptr[i][j] != 0) {
						tmp += "0";
					} else {
						tmp += "1";
					}
					// tmp += (int) ptr[i][j];
				}
				res += tmp + '\n';
				tmp = "";
			}

			String path = null;
			File file = context.getExternalFilesDir(null);
			path = file.getAbsolutePath() + "/input.txt";

			File saveFile = new File(path);
			BufferedWriter bfw = null;
			try {
				saveFile.createNewFile();
				bfw = new BufferedWriter(new FileWriter(saveFile));
				bfw.write(res);
				bfw.flush();
				bfw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (ScanMainActivity.backP) {
				Message msg = Message.obtain();
				msg.what = 0;
				msg.obj = res;
				mHandler.sendMessage(msg);
			}

			Log.i("Contour", "contour data appending finish");
			Log.i("Contour", "thread h : " + h + " w :" + w);
		}
	}
}
