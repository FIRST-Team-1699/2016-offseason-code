package org.usfirst.frc.team1699.utils.autonomous;

public class AutoPath {
	
	private String path;
	private int[] fileAsString;
	private int width;
	
	public AutoPath(String path, int width){
		this.path = path;
		this.width = width;
		fileAsString = AutoUtils.loadFileAsArray(path, width);
	}
	
	
	
	public void generateDirections(){
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int[] getFileAsString() {
		return fileAsString;
	}

	public int getWidth() {
		return width;
	}
}