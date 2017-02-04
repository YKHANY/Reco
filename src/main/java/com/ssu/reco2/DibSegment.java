package com.ssu.reco2;

import java.nio.ByteBuffer;

import android.util.Log;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class DibSegment {
	
	public ContourPoints contourTracing(IplImage src){
		
		int w = src.width();
		int h = src.height();
		int widthStep = src.widthStep();
		Log.i("w h widthStep","w : "+ w +" h : "+h+" widthStep : "+widthStep);
		char[][] ptr = new char[h][w];
		ByteBuffer buffer = src.getByteBuffer();
		
		
	//	init ptr array
		
		
		
		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				int index = i * src.widthStep() + j * src.nChannels();
				ptr[i][j]  = (char)buffer.get(index);
		//		Log.i("Dib",(byte)ptr[i][j]+"");
			}
		}
		
		int x,y,nx,ny;
		int dold, d, cnt;
		int[][] dir = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
		
		ContourPoints cp = new ContourPoints();
		Log.i("cp x, y","x : "+cp.x.length+ " y : "+cp.y.length);
		cp.num = 0;
		
		for(int j=0; j<h; j++)
		{
			for(int i=0; i<w; i++)
			{
				if((byte)ptr[j][i] == 0) // object 
				{
					x = i;
					y = j;

					dold = d = cnt = 0;

					while(true)
					{
						nx = x + dir[d][0];
						ny = y + dir[d][1];

						if(nx<0 || nx >= w || ny < 0 || ny>= h || ((byte)ptr[ny][nx])!= 0)
						{
							// out of index , or , not object
							// change direction 
							if(++d > 7) d = 0;
							cnt++;

							// all background 
							if(cnt >= 8)
							{
								cp.x[cp.num] = x;
								cp.y[cp.num] = y;
								cp.num++;
								break;
							}
						}
						else{
							// object
							cp.x[cp.num] = x;
							cp.y[cp.num] = y;
							cp.num++;
							
							if(cp.num >= ContourPoints.MAX_CONTOUR)
								break; // too much 

							// move 
							x = nx;
							y = ny;

							// init direction info
							cnt = 0;
							dold = d;
							d = (dold + 6)%8;  // d = dold-2
						}

						if(x == i && y == j && d ==0)
							break;
					}

					i = w; j = h; // for loop break;
				}
			}
		}
		
		
		return cp;
	}

}
