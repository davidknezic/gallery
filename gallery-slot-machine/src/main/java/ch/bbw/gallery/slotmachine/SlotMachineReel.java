package ch.bbw.gallery.slotmachine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import ch.bbw.gallery.core.models.Image;

public class SlotMachineReel {
	/**
	 * The full reel texture containing all images and some duplicates
	 * prepended and others appended to the end  
	 */
	private BufferedImage reelTexture;
	
	/**
	 * The total height of the reelTexture
	 */
	private float totalHeight;
	
	/**
	 * Rotation velocity
	 */
	private float velocity = 0.01f;
	
	/**
	 * Rotation from 0.0f to 1.0f
	 */
	private float rotation = 0.0f;
	
	public SlotMachineReel(RunGameStateReelItem[] items, int width, int height, Random random) {
		// Set random starting position
		rotation = random.nextFloat();
		
		int padding = (height - 160) / 2;
		int cut = 160 - padding;
		
		this.totalHeight = items.length * 160.0f;
		
		int h = (items.length + 1) * 160 + 2 * padding;
		
		reelTexture = new BufferedImage(160, h, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = reelTexture.getGraphics();
		g.drawImage(items[items.length - 1].getImage(), 0, -cut, null);
		
		for (int i = 0; i < items.length; i++) {
			g.drawImage(items[i].getImage(), 0, i * 160 + padding, null);
		}

		g.drawImage(items[0].getImage(), 0, items.length * 160 + padding, null);
		g.drawImage(items[1].getImage(), 0, (items.length + 1) * 160 + padding, null);
	}
	
	/**
	 * Paints the reel in its current position to the Graphics
	 * 
	 * @param g Grapics to paint to
	 */
	public void paint(Graphics g) {
		int verticalPosition = (int) (totalHeight * rotation);
		
		g.drawImage(reelTexture, 0, -verticalPosition, null);
	}
	
	/**
	 * Calculate the reel position
	 */
	public void process() {
		rotation += velocity;
		
		if (rotation >= 1.0f) {
			rotation -= 1.0f;
		}
	}
	
	public void stop() {
		// TODO: Make it snap to an image
		this.velocity = 0.0f;
	}
	
	public Image current() {
		// TODO: Return the hit image
		// Would be better by notifying the parent object
		return null;
	}
}
