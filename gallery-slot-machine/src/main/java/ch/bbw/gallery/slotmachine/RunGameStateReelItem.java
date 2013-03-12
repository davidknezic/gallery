package ch.bbw.gallery.slotmachine;

import java.awt.image.BufferedImage;

import ch.bbw.gallery.core.models.Image;

public class RunGameStateReelItem {
	private BufferedImage image;
	private Image info;
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public Image getInfo() {
		return info;
	}
	
	public void setInfo(Image info) {
		this.info = info;
	}
}
