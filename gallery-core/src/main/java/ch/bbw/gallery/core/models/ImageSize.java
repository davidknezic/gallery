package ch.bbw.gallery.core.models;

public class ImageSize {
	private int width;
	private int height;
	
	public ImageSize() {
		this(0, 0);
	}
	
	public ImageSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
}
