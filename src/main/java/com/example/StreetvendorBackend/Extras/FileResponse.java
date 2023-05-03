package com.example.StreetvendorBackend.Extras;

public class FileResponse {
	private String Filename;
	private String message;
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public FileResponse(String filename, String message) {
		super();
		Filename = filename;
		this.message = message;
	}
	public FileResponse() {
		super();
		
	}
	
}