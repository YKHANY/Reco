package org.recoapp.util;

import java.io.Serializable;

public class FileDTO implements Serializable{
	private String file_code;
	private String member_code;
	private String imagecode_code;
	private String file_latest_modifydate;
	private String file_type;
	private String file_name;
	private String file_url;
	private String file_thumb_url;
	
	public FileDTO(String file_code, String member_code, String imagecode_code,
			String file_latest_modifydate, String file_type, String file_name,
			String file_url, String file_thumb_url) {
		super();
		this.file_code = file_code;
		this.member_code = member_code;
		this.imagecode_code = imagecode_code;
		this.file_latest_modifydate = file_latest_modifydate;
		this.file_type = file_type;
		this.file_name = file_name;
		this.file_url = file_url;
		this.file_thumb_url = file_thumb_url;
	}
	public String getFile_code() {
		return file_code;
	}
	public void setFile_code(String file_code) {
		this.file_code = file_code;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public String getImagecode_code() {
		return imagecode_code;
	}
	public void setImagecode_code(String imagecode_code) {
		this.imagecode_code = imagecode_code;
	}
	public String getFile_latest_modifydate() {
		return file_latest_modifydate;
	}
	public void setFile_latest_modifydate(String file_latest_modifydate) {
		this.file_latest_modifydate = file_latest_modifydate;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_thumb_url() {
		return file_thumb_url;
	}
	public void setFile_thumb_url(String file_thumb_url) {
		this.file_thumb_url = file_thumb_url;
	}
	@Override
	public String toString() {
		return "FileDTO [file_code=" + file_code + ", member_code="
				+ member_code + ", imagecode_code=" + imagecode_code
				+ ", file_latest_modifydate=" + file_latest_modifydate
				+ ", file_type=" + file_type + ", file_name=" + file_name
				+ ", file_url=" + file_url + ", file_thumb_url="
				+ file_thumb_url + "]";
	}
}
