package org.recoapp.util;

import java.io.Serializable;
import java.util.Date;

public class ImagecodeDTO implements Serializable{
	private String imagecode_code;
	private String member_code;
	private String imagecode_latest_modifydate;
	private String imagecode_name;
	private int imagecode_url;
	
	public ImagecodeDTO(String imagecode_code, String member_code, String imagecode_latest_modifydate, String imagecode_name, String imagecode_url) {
		this.imagecode_code = imagecode_code;
		this.member_code = member_code;
		this.imagecode_latest_modifydate = imagecode_latest_modifydate;
		this.imagecode_name = imagecode_name;
		this.imagecode_url = Integer.parseInt(imagecode_url);
	}
	public String getImagecode_code() {
		return imagecode_code;
	}
	public void setImagecode_code(String imagecode_code) {
		this.imagecode_code = imagecode_code;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public String getImagecode_latest_modifydate() {
		return imagecode_latest_modifydate;
	}
	public void setImagecode_latest_modifydate(
			String imagecode_latest_modifydate) {
		this.imagecode_latest_modifydate = imagecode_latest_modifydate;
	}
	public String getImagecode_name() {
		return imagecode_name;
	}
	public void setImagecode_name(String imagecode_name) {
		this.imagecode_name = imagecode_name;
	}
	public int getImagecode_url() {
		return imagecode_url;
	}
	public void setImagecode_url(int imagecode_url) {
		this.imagecode_url = imagecode_url;
	}
	@Override
	public String toString() {
		return "ImagecodeDTO [imagecode_code=" + imagecode_code
				+ ", member_code=" + member_code
				+ ", imagecode_latest_modifydate="
				+ imagecode_latest_modifydate + ", imagecode_name="
				+ imagecode_name + ", imagecode_url=" + imagecode_url + "]";
	}
}
