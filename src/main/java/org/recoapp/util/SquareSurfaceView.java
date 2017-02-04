package org.recoapp.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class SquareSurfaceView extends SurfaceView {
	public SquareSurfaceView(Context context) {
        super(context);
    }
 
    public SquareSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public SquareSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int width = getMeasuredWidth();
    int height = (width/3)*2;
    setMeasuredDimension(width, height);
  }
}
