package org.recoapp.util;

import java.io.Serializable;
import java.util.ArrayList;

public class CodeCreateDTO implements Serializable{
	private int selectedCodeNum;
	private int selectedCodeColor;
	private int selectedCodeUrl;
	private String codeName;
	private ArrayList<String> filePath;

	public CodeCreateDTO() {
		this.selectedCodeUrl = 25;
		this.codeName = "noname";
		this.filePath = new ArrayList<String>();
	}
	
	public int getSelectedCodeNum() {
		return selectedCodeNum;
	}

	public void setSelectedCodeNum(int selectedCodeNum) {
		this.selectedCodeNum = selectedCodeNum;
	}

	public int getSelectedCodeColor() {
		return selectedCodeColor;
	}

	public void setSelectedCodeColor(int selectedCodeColor) {
		this.selectedCodeColor = selectedCodeColor;
	}

	public int getSelectedCodeUrl() {
		return selectedCodeUrl;
	}

	public void setSelectedCodeUrl(int selectedCodeUrl) {
		this.selectedCodeUrl = selectedCodeUrl;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public ArrayList<String> getFilePath() {
		return filePath;
	}

	public void setFilePath(ArrayList<String> filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "CodeCreateDTO [selectedCodeNum=" + selectedCodeNum
				+ ", selectedCodeColor=" + selectedCodeColor
				+ ", selectedCodeUrl=" + selectedCodeUrl + ", codeName="
				+ codeName + ", filePath=" + filePath + "]";
	}
	
}
