package com.ssu.bp;

public class BpRes {

	private int index;
	private float maxVal;
	
	public BpRes(){
		
	}
	public BpRes(int index, float maxVal){
		this.index = index;
		this.maxVal = maxVal;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public float getMaxVal() {
		return maxVal;
	}
	public void setMaxVal(float maxVal) {
		this.maxVal = maxVal;
	}

}
