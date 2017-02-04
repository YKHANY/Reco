package com.ssu.reco2;

public class ContourPoints {
	public final static int MAX_CONTOUR = 5000;
	int num;
	int[] x;
	int[] y;
	
	public ContourPoints(){
		this.x = new int[MAX_CONTOUR];
		this.y = new int[MAX_CONTOUR];
		
	}
	
}
